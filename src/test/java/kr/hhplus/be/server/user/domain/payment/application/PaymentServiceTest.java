package kr.hhplus.be.server.user.domain.payment.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.user.repository.UserRepository;
import kr.hhplus.be.server.user.domain.wallet.entity.Wallet;
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
    private UserRepository userRepository;

    @Mock
    private ReservationPort reservationPort;

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
        verify(reservationPort).findAllByUserId(userId);
    }

    @Nested
    class payment {
        @Test
        void success() {
            //given
            long userId = 1L;
            long reservationId = 1L;
            BigDecimal price = BigDecimal.valueOf(10000L);
            User user = spy(AuthFixture.user());
            Wallet wallet = mock(Wallet.class);

            Concert concert = ConcertFixture.concert();
            SeatInventory seatInventory = ConcertFixture.seatMasterList().get(0).getSeatInventory();
            Reservation reservation = Reservation.createWith(
                    reservationId,
                    user,
                    concert,
                    seatInventory,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    price
            );
            Payment payment = Payment.createWith(
                    1L,
                    user,
                    reservation,
                    price,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            when(reservationPort.getById(reservationId)).thenReturn(reservation);
            when(userRepository.getById(userId)).thenReturn(user);
            when(paymentPort.pay(reservation, wallet)).thenReturn(payment);
            when(user.getWallet()).thenReturn(wallet);
            when(wallet.getBalance()).thenReturn(BigDecimal.valueOf(10000L));

            //when
            PaymentResponse result = paymentService.payment(userId, reservationId);

            //then
            verify(reservationPort).getById(reservationId);
            verify(userRepository).getById(userId);
            verify(paymentPort).pay(reservation, wallet);
            assertEquals(payment.getPrice(), result.price());
        }

        @Test
        void 잔액이_부족한_경우_InsufficientBalanceException을_던진다() {
            //given
            long userId = 1L;
            long reservationId = 1L;
            BigDecimal price = BigDecimal.valueOf(10000L);
            User user = spy(AuthFixture.user());
            Wallet wallet = mock(Wallet.class);

            Concert concert = ConcertFixture.concert();
            SeatInventory seatInventory = ConcertFixture.seatMasterList().get(0).getSeatInventory();
            Reservation reservation = Reservation.createWith(
                    reservationId,
                    user,
                    concert,
                    seatInventory,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    price
            );
            Payment payment = Payment.createWith(
                    1L,
                    user,
                    reservation,
                    price,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            when(reservationPort.getById(reservationId)).thenReturn(reservation);
            when(userRepository.getById(userId)).thenReturn(user);
            when(user.getWallet()).thenReturn(wallet);
            when(wallet.getBalance()).thenReturn(BigDecimal.valueOf(5000L));

            //when & then
            assertThrows(
                    InsufficientBalanceException.class,
                    () -> paymentService.payment(userId, reservationId)
            );
        }
    }

}