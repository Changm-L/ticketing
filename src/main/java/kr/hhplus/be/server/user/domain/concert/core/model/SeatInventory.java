package kr.hhplus.be.server.user.domain.concert.core.model;

import java.math.BigDecimal;

import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.core.exception.CannotUpdateSeatStatusException;

public class SeatInventory {
    private long       id;
    private SeatStatus seatStatus;
    private int        version;
    private BigDecimal price;

    private SeatInventory(
            long id,
            SeatStatus seatStatus,
            BigDecimal price
    ) {
        this.id = id;
        this.seatStatus = seatStatus;
        this.price = price;
    }

    private SeatInventory(SeatStatus status, BigDecimal price) {
        this.seatStatus = status;
        this.price = price;
    }

    public static SeatInventory available(
            BigDecimal price
    ) {
        return new SeatInventory(SeatStatus.AVAILABLE, price);
    }

    public void held() {
        if (this.seatStatus.equals(SeatStatus.AVAILABLE)) {
            this.seatStatus = SeatStatus.HELD;
        } else {
            throw new CannotUpdateSeatStatusException();
        }
    }

    public void reserve() {
        if (this.seatStatus.equals(SeatStatus.HELD)) {
            this.seatStatus = SeatStatus.RESERVED;
        } else {
            throw new CannotUpdateSeatStatusException();
        }
    }
}
