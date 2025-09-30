package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity.PaymentJpaEntity;

public interface PaymentJpaRepository extends JpaRepository<PaymentJpaEntity, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse(
                    p.id,
                    p.price,
                    r.concert.title,
                    r.concert.startsAt
                )
                FROM PaymentJpaEntity p
                INNER JOIN FETCH ReservationJpaEntity r on p.reservationJpaEntity.id = r.id
                WHERE p.user.id =:userId
                ORDER BY p.id DESC
            """)
    List<FindAllPaymentResponse> findAllByUserId(long userId);
}
