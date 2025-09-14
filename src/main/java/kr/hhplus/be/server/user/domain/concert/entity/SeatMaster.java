package kr.hhplus.be.server.user.domain.concert.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.IdentifiableEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "seat_master",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_concert_seat_master",
                columnNames = {"concert_id", "row_no", "seat_no"}
        )
)

public class SeatMaster extends IdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @Column(nullable = false)
    private int rowNo;

    @Column(nullable = false)
    private int seatNo;

    @OneToOne(
            mappedBy = "seatMaster",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private SeatInventory seatInventory;

    private SeatMaster(
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
            BigDecimal price,
            int rowNo,
            int seatNo
    ) {
        SeatMaster seatMaster = new SeatMaster(
                concert,
                rowNo,
                seatNo
        );
        seatMaster.seatInventory = SeatInventory.of(seatMaster, price);

        return seatMaster;
    }

}
