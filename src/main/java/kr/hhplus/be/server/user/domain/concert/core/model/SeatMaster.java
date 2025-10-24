package kr.hhplus.be.server.user.domain.concert.core.model;

import java.time.LocalDateTime;

public class SeatMaster {
    private long          id;
    private int           rowNo;
    private int           seatNo;
    private SeatInventory seatInventory;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private SeatMaster(
            int rowNo,
            int seatNo,
            SeatInventory seatInventory
    ) {
        this.rowNo = rowNo;
        this.seatNo = seatNo;
        this.seatInventory = seatInventory;
    }

    private SeatMaster(
            long id,
            int rowNo,
            int seatNo,
            SeatInventory seatInventory,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.rowNo = rowNo;
        this.seatNo = seatNo;
        this.seatInventory = seatInventory;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static SeatMaster create(int rowNo, int seatNo, SeatInventory seatInventory) {
        return new SeatMaster(
                rowNo,
                seatNo,
                seatInventory
        );
    }

    public long getId() {
        return id;
    }

    public int getRowNo() {
        return rowNo;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public SeatInventory getSeatInventory() {
        return seatInventory;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
