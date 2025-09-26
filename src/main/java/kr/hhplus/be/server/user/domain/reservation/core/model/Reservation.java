package kr.hhplus.be.server.user.domain.reservation.core.model;

import java.time.LocalDateTime;

public class Reservation {
    private long          id;
    private long          userId;
    private long          concertId;
    private long          seatMasterId;
    private long          price;
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

    public long getSeatMasterId() {
        return seatMasterId;
    }

    public long getPrice() {
        return price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
