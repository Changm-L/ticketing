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
import kr.hhplus.be.server.user.domain.concert.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity.PaymentJpaEntity;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.repository.PaymentJpaRepository;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.repository.WalletJpaRepository;
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
    private WalletJpaRepository walletJpaRepository;

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
        User user = spy(AuthFixture.user());
        WalletJpaEntity walletJpaEntity = mock(WalletJpaEntity.class);
        BigDecimal price = BigDecimal.valueOf(10000L);
        Concert concert = ConcertFixture.concert();
        SeatInventory seatInventory = ConcertFixture.seatMasterList().get(0).getSeatInventory();

        ReflectionTestUtils.setField(seatInventory, "id", 1L);
        ReflectionTestUtils.setField(seatInventory, "seatStatus", SeatStatus.HELD);
        walletJpaEntity.charge(price);

        Reservation reservation = Reservation.createWith(
                reservationId, user, concert, seatInventory,
                LocalDateTime.now(), LocalDateTime.now(), price
        );
        ReservationJpaEntity reservationJpaEntity = ReservationJpaEntity.createWith(
                concert,
                seatInventory,
                user,
                price
        );
        PaymentJpaEntity paymentJpaEntity = PaymentJpaEntity.createWith(user, reservationJpaEntity, price);

        when(entityManager.getReference(User.class, 1L)).thenReturn(user);
        when(entityManager.getReference(ReservationJpaEntity.class, reservationId)).thenReturn(reservationJpaEntity);
        when(entityManager.getReference(SeatInventory.class, 1L)).thenReturn(seatInventory);

        //when
        Payment result = paymentPersistenceAdapter.pay(reservation, walletJpaEntity);

        //then
        assertEquals(result.getPrice(), paymentJpaEntity.getPrice());
        assertEquals(SeatStatus.RESERVED, seatInventory.getSeatStatus());
        verify(walletJpaEntity).use(price);
    }
}