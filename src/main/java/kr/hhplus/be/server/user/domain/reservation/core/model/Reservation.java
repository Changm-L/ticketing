package kr.hhplus.be.server.user.domain.reservation.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.user.entity.User;

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
            User user,
            Concert concert,
            SeatInventory seatInventory,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            BigDecimal price
    ) {
        return new Reservation(
                id,
                user.getId(),
                concert.getId(),
                seatInventory.getId(),
                price,
                createdAt,
                updatedAt
        );
    }
}
