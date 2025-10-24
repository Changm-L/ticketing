package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Entity
@Table(name = "payment")
public class PaymentJpaEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationJpaEntity reservationJpaEntity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    public PaymentJpaEntity() {
    }

    private PaymentJpaEntity(
            UserJpaEntity userJpaEntity,
            ReservationJpaEntity reservationJpaEntity,
            BigDecimal price
    ) {
        this.userJpaEntity = userJpaEntity;
        this.reservationJpaEntity = reservationJpaEntity;
        this.price = price;
    }

    public static PaymentJpaEntity createWith(
            UserJpaEntity userJpaEntity,
            ReservationJpaEntity reservationJpaEntity,
            BigDecimal price
    ) {
        return new PaymentJpaEntity(userJpaEntity, reservationJpaEntity, price);
    }

    public UserJpaEntity getUser() {
        return userJpaEntity;
    }

    public ReservationJpaEntity getReservationJpaEntity() {
        return reservationJpaEntity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
