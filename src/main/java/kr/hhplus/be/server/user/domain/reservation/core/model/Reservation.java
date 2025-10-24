package kr.hhplus.be.server.user.domain.reservation.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

public class Reservation {
    private long          id;
    private long          userId;
    private long          concertId;
    private long          seatInventoryId;
    private BigDecimal    price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public long getConcertId() {
        return concertId;
    }

    public long getSeatInventoryId() {
        return seatInventoryId;
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

    private Reservation(
            long id,
            long userId,
            long concertId,
            long seatInventoryId,
            BigDecimal price,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.concertId = concertId;
        this.seatInventoryId = seatInventoryId;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static Reservation createWith(
            long id,
            UserJpaEntity userJpaEntity,
            ConcertJpaEntity concertJpaEntity,
            SeatInventoryJpaEntity seatInventoryJpaEntity,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            BigDecimal price
    ) {
        return new Reservation(
                id,
                userJpaEntity.getId(),
                concertJpaEntity.getId(),
                seatInventoryJpaEntity.getId(),
                price,
                createdAt,
                updatedAt
        );
    }
}
