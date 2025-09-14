package kr.hhplus.be.server.user.domain.concert.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.concert.constant.SeatStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat_inventory")
public class SeatInventory extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_master_id", nullable = false)
    SeatMaster seatMaster;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Version
    int version;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    public Concert getConcert() {
        return this.seatMaster.getConcert();
    }

    private SeatInventory(
            SeatMaster seatMaster,
            BigDecimal price,
            SeatStatus status
    ) {
        this.seatMaster = seatMaster;
        this.seatStatus = status;
        this.price = price;
    }

    public static SeatInventory of(
            SeatMaster seatMaster,
            BigDecimal price
    ) {
        return new SeatInventory(
                seatMaster,
                price,
                SeatStatus.AVAILABLE
        );
    }
}
