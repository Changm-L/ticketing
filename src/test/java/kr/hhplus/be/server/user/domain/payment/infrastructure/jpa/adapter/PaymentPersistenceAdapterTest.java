package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.adapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import jakarta.persistence.EntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity.PaymentJpaEntity;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.repository.PaymentJpaRepository;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.adapter.WalletPersistenceAdapter;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentPersistenceAdapterTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private PaymentJpaRepository paymentJpaRepository;

    @Mock
    private WalletPersistenceAdapter walletPersistenceAdapter;

    @InjectMocks
    private PaymentPersistenceAdapter paymentPersistenceAdapter;

    @Test
    void findAllByUserId() {
        //given
        long userId = 1L;
        BigDecimal price = BigDecimal.valueOf(10000L);
        FindAllPaymentResponse response = new FindAllPaymentResponse(
                1L,
                price,
                "title",
                LocalDate.now()
        );
        List<FindAllPaymentResponse> expected = List.of(response);

        when(paymentJpaRepository.findAllByUserId(userId)).thenReturn(expected);

        //when
        List<FindAllPaymentResponse> result = paymentPersistenceAdapter.findAllByUserId(userId);

        //then
        assertEquals(expected.size(), result.size());
        verify(paymentJpaRepository).findAllByUserId(userId);
    }

    @Test
    void pay() {
        long reservationId = 1L;
        UserJpaEntity userJpaEntity = spy(AuthFixture.user());
        Wallet wallet = mock(Wallet.class);
        BigDecimal price = BigDecimal.valueOf(10000L);
        ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
        SeatInventoryJpaEntity seatInventoryJpaEntity = ConcertFixture
                .seatMasterList()
                .get(0)
                .getSeatInventoryJpaEntity();

        ReflectionTestUtils.setField(seatInventoryJpaEntity, "id", 1L);
        ReflectionTestUtils.setField(seatInventoryJpaEntity, "seatStatus", SeatStatus.HELD);
        wallet.charge(price);

        Reservation reservation = Reservation.createWith(
                reservationId, userJpaEntity, concertJpaEntity, seatInventoryJpaEntity,
                LocalDateTime.now(), LocalDateTime.now(), price
        );
        ReservationJpaEntity reservationJpaEntity = ReservationJpaEntity.createWith(
                concertJpaEntity,
                seatInventoryJpaEntity,
                userJpaEntity,
                price
        );
        PaymentJpaEntity paymentJpaEntity = PaymentJpaEntity.createWith(userJpaEntity, reservationJpaEntity, price);

        when(entityManager.getReference(UserJpaEntity.class, 1L)).thenReturn(userJpaEntity);
        when(entityManager.getReference(ReservationJpaEntity.class, reservationId)).thenReturn(reservationJpaEntity);
        when(entityManager.getReference(SeatInventoryJpaEntity.class, 1L)).thenReturn(seatInventoryJpaEntity);

        //when
        Payment result = paymentPersistenceAdapter.pay(reservation, wallet);

        //then
        assertEquals(result.getPrice(), paymentJpaEntity.getPrice());
        assertEquals(SeatStatus.HELD, seatInventoryJpaEntity.getSeatStatus());
    }
}