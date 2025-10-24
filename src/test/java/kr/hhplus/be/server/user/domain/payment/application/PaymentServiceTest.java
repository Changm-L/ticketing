package kr.hhplus.be.server.user.domain.payment.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.model.WalletLedger;
import kr.hhplus.be.server.user.domain.wallet.core.port.out.WalletPort;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentPort paymentPort;

    @Mock
    private ReservationPort reservationPort;

    @Mock
    private WalletPort walletPort;

    @Mock
    private SeatInventoryReadRepository seatInventoryReadRepository;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void findAll() {
        //given
        long userId = 1L;
        BigDecimal price = BigDecimal.valueOf(10000L);
        FindAllPaymentResponse response = new FindAllPaymentResponse(1L, price, "title", LocalDate.now());
        List<FindAllPaymentResponse> expected = List.of(response);
        when(paymentPort.findAllByUserId(userId)).thenReturn(expected);

        //when
        List<FindAllPaymentResponse> result = paymentService.findAll(userId);

        //then
        assertEquals(expected.size(), result.size());
        verify(paymentPort).findAllByUserId(userId);
    }

    @Nested
    class payment {
        @Test
        void success() {
            //given
            long userId = 1L;
            long reservationId = 1L;
            long seatInventoryId = 1L;
            BigDecimal price = BigDecimal.valueOf(10000L);
            UserJpaEntity userJpaEntity = spy(AuthFixture.user());
            WalletLedger walletLedger = WalletLedger.of(
                    1L,
                    TransactionType.CHARGE,
                    BigDecimal.valueOf(10000L),
                    price
            );
            List<WalletLedger> walletLedgers = List.of(walletLedger);
            Wallet wallet = Wallet.createWith(
                    1L,
                    userId,
                    BigDecimal.valueOf(10000L),
                    walletLedgers,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
            SeatInventoryJpaEntity seatInventoryJpaEntity = ConcertFixture
                    .seatMasterList()
                    .get(0)
                    .getSeatInventoryJpaEntity();
            ReflectionTestUtils.setField(seatInventoryJpaEntity, "id", seatInventoryId);
            ReflectionTestUtils.setField(seatInventoryJpaEntity, "seatStatus", SeatStatus.HELD);
            Reservation reservation = Reservation.createWith(
                    reservationId,
                    userJpaEntity,
                    concertJpaEntity,
                    seatInventoryJpaEntity,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    price
            );
            Payment payment = Payment.createWith(
                    1L,
                    userJpaEntity,
                    reservation,
                    price,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            when(reservationPort.getById(reservationId)).thenReturn(reservation);
            when(walletPort.getWalletByUserId(userId)).thenReturn(wallet);
            when(seatInventoryReadRepository.getById(seatInventoryId)).thenReturn(seatInventoryJpaEntity);
            when(paymentPort.pay(reservation, wallet)).thenReturn(payment);

            //when
            PaymentResponse result = paymentService.payment(userId, reservationId);

            //then
            verify(reservationPort).getById(reservationId);
            verify(walletPort).getWalletByUserId(userId);
            verify(seatInventoryReadRepository).getById(seatInventoryId);
            verify(paymentPort).pay(reservation, wallet);
            assertEquals(payment.getPrice(), result.price());
        }

        @Test
        void 잔액이_부족한_경우_InsufficientBalanceException을_던진다() {
            //given
            long userId = 1L;
            long reservationId = 1L;
            long seatInventoryId = 1L;
            BigDecimal price = BigDecimal.valueOf(10000L);
            UserJpaEntity userJpaEntity = spy(AuthFixture.user());
            WalletLedger walletLedger = WalletLedger.of(
                    1L,
                    TransactionType.CHARGE,
                    BigDecimal.valueOf(10000L),
                    price
            );
            List<WalletLedger> walletLedgers = List.of(walletLedger);
            Wallet wallet = Wallet.createWith(
                    1L,
                    userId,
                    BigDecimal.ZERO,
                    walletLedgers,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );

            ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
            SeatInventoryJpaEntity seatInventoryJpaEntity = ConcertFixture
                    .seatMasterList()
                    .get(0)
                    .getSeatInventoryJpaEntity();
            ReflectionTestUtils.setField(seatInventoryJpaEntity, "id", seatInventoryId);
            ReflectionTestUtils.setField(seatInventoryJpaEntity, "seatStatus", SeatStatus.HELD);
            Reservation reservation = Reservation.createWith(
                    reservationId,
                    userJpaEntity,
                    concertJpaEntity,
                    seatInventoryJpaEntity,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    price
            );
            Payment payment = Payment.createWith(
                    1L,
                    userJpaEntity,
                    reservation,
                    price,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            when(reservationPort.getById(reservationId)).thenReturn(reservation);
            when(walletPort.getWalletByUserId(userId)).thenReturn(wallet);

            //when & then
            assertThrows(
                    InsufficientBalanceException.class,
                    () -> paymentService.payment(userId, reservationId)
            );
        }
    }

}