package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Getter;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Entity
@Getter
@Table(name = "reservation")
public class ReservationJpaEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private ConcertJpaEntity concertJpaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_inventory_id", nullable = false)
    private SeatInventoryJpaEntity seatInventoryJpaEntity;

    @Column(nullable = false)
    private BigDecimal price;

    public ReservationJpaEntity() {
    }

    private ReservationJpaEntity(
            ConcertJpaEntity concertJpaEntity,
            SeatInventoryJpaEntity seatInventoryJpaEntity,
            UserJpaEntity userJpaEntity,
            BigDecimal price
    ) {
        this.concertJpaEntity = concertJpaEntity;
        this.seatInventoryJpaEntity = seatInventoryJpaEntity;
        this.userJpaEntity = userJpaEntity;
        this.price = price;
    }

    public static ReservationJpaEntity createWith(
            ConcertJpaEntity concertJpaEntity,
            SeatInventoryJpaEntity seatInventoryJpaEntity,
            UserJpaEntity userJpaEntity,
            BigDecimal price
    ) {
        return new ReservationJpaEntity(concertJpaEntity, seatInventoryJpaEntity, userJpaEntity, price);
    }
}
