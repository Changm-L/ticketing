package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.adapter;

import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.model.Payment;
import kr.hhplus.be.server.user.domain.payment.core.port.out.PaymentPort;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity.PaymentJpaEntity;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.repository.PaymentJpaRepository;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.repository.WalletJpaRepository;

@Component
@RequiredArgsConstructor
public class PaymentPersistenceAdapter implements PaymentPort {

    private final EntityManager        entityManager;
    private final PaymentJpaRepository paymentJpaRepository;
    private final WalletJpaRepository  walletJpaRepository;

    @Override
    public List<FindAllPaymentResponse> findAllByUserId(long userId) {
        return paymentJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Payment pay(
            Reservation reservation,
            WalletJpaEntity walletJpaEntity
    ) {
        User userRef = entityManager.getReference(User.class, reservation.getUserId());
        ReservationJpaEntity reservationJpaEntityRef = entityManager.getReference(
                ReservationJpaEntity.class,
                reservation.getId()
        );
        SeatInventory seatInventoryRef = entityManager.getReference(
                SeatInventory.class,
                reservation.getSeatInventoryId()
        );

        PaymentJpaEntity paymentJpaEntity = PaymentJpaEntity.createWith(
                userRef,
                reservationJpaEntityRef,
                reservation.getPrice()
        );
        seatInventoryRef.reserve();
        paymentJpaRepository.save(paymentJpaEntity);

        walletJpaEntity.use(paymentJpaEntity.getPrice());
        walletJpaRepository.save(walletJpaEntity);

        return Payment.createWith(
                paymentJpaEntity.getId(),
                userRef,
                reservation,
                seatInventoryRef.getPrice(),
                paymentJpaEntity.getCreatedAt(),
                paymentJpaEntity.getUpdatedAt()
        );
    }
}
