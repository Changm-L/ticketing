package kr.hhplus.be.server.user.domain.payment.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.entity.User;

@Entity
@Table(name = "payment")
public class PaymentJpaEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationJpaEntity reservationJpaEntity;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    public PaymentJpaEntity() {
    }

    private PaymentJpaEntity(
            User user,
            ReservationJpaEntity reservationJpaEntity,
            BigDecimal price
    ) {
        this.user = user;
        this.reservationJpaEntity = reservationJpaEntity;
        this.price = price;
    }

    public static PaymentJpaEntity createWith(
            User user,
            ReservationJpaEntity reservationJpaEntity,
            BigDecimal price
    ) {
        return new PaymentJpaEntity(user, reservationJpaEntity, price);
    }

    public User getUser() {
        return user;
    }

    public ReservationJpaEntity getReservationJpaEntity() {
        return reservationJpaEntity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
