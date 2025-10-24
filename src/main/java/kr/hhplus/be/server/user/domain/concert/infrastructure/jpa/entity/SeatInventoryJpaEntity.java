package kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.core.exception.CannotUpdateSeatStatusException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "seat_inventory")
public class SeatInventoryJpaEntity extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_master_id", nullable = false)
    SeatMasterJpaEntity seatMasterJpaEntity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    @Version
    int version;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;

    public ConcertJpaEntity getConcert() {
        return this.seatMasterJpaEntity.getConcertJpaEntity();
    }

    private SeatInventoryJpaEntity(
            SeatMasterJpaEntity seatMasterJpaEntity,
            BigDecimal price,
            SeatStatus status
    ) {
        this.seatMasterJpaEntity = seatMasterJpaEntity;
        this.seatStatus = status;
        this.price = price;
    }

    public static SeatInventoryJpaEntity of(
            SeatMasterJpaEntity seatMasterJpaEntity,
            BigDecimal price
    ) {
        return new SeatInventoryJpaEntity(
                seatMasterJpaEntity,
                price,
                SeatStatus.AVAILABLE
        );
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
