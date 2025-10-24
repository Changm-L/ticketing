package kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity;

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

public class SeatMasterJpaEntity extends IdentifiableEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private ConcertJpaEntity concertJpaEntity;

    @Column(nullable = false)
    private int rowNo;

    @Column(nullable = false)
    private int seatNo;

    @OneToOne(
            mappedBy = "seatMasterJpaEntity",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private SeatInventoryJpaEntity seatInventoryJpaEntity;

    private SeatMasterJpaEntity(
            ConcertJpaEntity concertJpaEntity,
            int rowNo,
            int seatNo
    ) {
        this.concertJpaEntity = concertJpaEntity;
        this.rowNo = rowNo;
        this.seatNo = seatNo;
    }

    public static SeatMasterJpaEntity of(
            ConcertJpaEntity concertJpaEntity,
            BigDecimal price,
            int rowNo,
            int seatNo
    ) {
        SeatMasterJpaEntity seatMasterJpaEntity = new SeatMasterJpaEntity(
                concertJpaEntity,
                rowNo,
                seatNo
        );
        seatMasterJpaEntity.seatInventoryJpaEntity = SeatInventoryJpaEntity.of(seatMasterJpaEntity, price);

        return seatMasterJpaEntity;
    }

}
