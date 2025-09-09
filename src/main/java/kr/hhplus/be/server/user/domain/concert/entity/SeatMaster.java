package kr.hhplus.be.server.user.domain.concert.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.IdentifiableEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SeatMaster extends IdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    Concert concert;

    @Column(nullable = false)
    int rowNo;

    @Column(nullable = false)
    int seatNo;

    public SeatMaster(
            Concert concert,
            int rowNo,
            int seatNo
    ) {
        this.concert = concert;
        this.rowNo = rowNo;
        this.seatNo = seatNo;
    }

    public static SeatMaster of(
            Concert concert,
            int rowNo,
            int seatNo
    ) {
        return new SeatMaster(concert, rowNo, seatNo);
    }
}
