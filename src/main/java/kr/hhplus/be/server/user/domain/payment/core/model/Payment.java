package kr.hhplus.be.server.user.domain.payment.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

public class Payment {
    private long          id;
    private long          userId;
    private long          reservationId;
    private BigDecimal    price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Payment(
            long id,
            long userId,
            long reservationId,
            BigDecimal price,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.reservationId = reservationId;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Payment createWith(
            long id,
            UserJpaEntity userJpaEntity,
            Reservation reservation,
            BigDecimal price,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new Payment(
                id,
                userJpaEntity.getId(),
                reservation.getId(),
                price,
                createdAt,
                updatedAt
        );
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getReservationId() {
        return reservationId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
