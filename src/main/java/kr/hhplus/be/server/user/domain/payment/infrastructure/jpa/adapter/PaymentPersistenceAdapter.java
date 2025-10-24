package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.adapter;

import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity.PaymentJpaEntity;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.repository.PaymentJpaRepository;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.adapter.WalletPersistenceAdapter;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPort {

    private final EntityManager            entityManager;
    private final PaymentJpaRepository     paymentJpaRepository;
    private final WalletPersistenceAdapter walletPersistenceAdapter;

    @Override
    public List<FindAllPaymentResponse> findAllByUserId(long userId) {
        return paymentJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Payment pay(
            Reservation reservation,
            Wallet wallet
    ) {
        UserJpaEntity userJpaEntityRef = entityManager.getReference(UserJpaEntity.class, reservation.getUserId());
        ReservationJpaEntity reservationJpaEntityRef = entityManager.getReference(
                ReservationJpaEntity.class,
                reservation.getId()
        );
        SeatInventoryJpaEntity seatInventoryJpaEntityRef = entityManager.getReference(
                SeatInventoryJpaEntity.class,
                reservation.getSeatInventoryId()
        );

        PaymentJpaEntity paymentJpaEntity = PaymentJpaEntity.createWith(
                userJpaEntityRef,
                reservationJpaEntityRef,
                reservation.getPrice()
        );
        paymentJpaRepository.save(paymentJpaEntity);

        walletPersistenceAdapter.save(wallet);

        return Payment.createWith(
                paymentJpaEntity.getId(),
                userJpaEntityRef,
                reservation,
                seatInventoryJpaEntityRef.getPrice(),
                paymentJpaEntity.getCreatedAt(),
                paymentJpaEntity.getUpdatedAt()
        );
    }
}
