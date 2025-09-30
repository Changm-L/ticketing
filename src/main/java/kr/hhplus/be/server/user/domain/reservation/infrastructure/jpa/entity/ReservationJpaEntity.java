package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.Getter;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.user.entity.User;

@Entity
@Getter
@Table(name = "reservation")
public class ReservationJpaEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_inventory_id", nullable = false)
    private SeatInventory seatInventory;

    @Column(nullable = false)
    private BigDecimal price;

    public ReservationJpaEntity() {
    }

    private ReservationJpaEntity(
            Concert concert,
            SeatInventory seatInventory,
            User user,
            BigDecimal price
    ) {
        this.concert = concert;
        this.seatInventory = seatInventory;
        this.user = user;
        this.price = price;
    }

    public static ReservationJpaEntity createWith(
            Concert concert,
            SeatInventory seatInventory,
            User user,
            BigDecimal price
    ) {
        return new ReservationJpaEntity(concert, seatInventory, user, price);
    }
}
