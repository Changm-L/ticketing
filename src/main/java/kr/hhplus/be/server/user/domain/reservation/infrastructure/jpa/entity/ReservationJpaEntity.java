package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;
import kr.hhplus.be.server.user.domain.user.entity.User;

@Entity
public class ReservationJpaEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id", nullable = false)
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_master_id", nullable = false)
    private SeatMaster seatMaster;

    @Column(nullable = false)
    private BigDecimal price;

    public ReservationJpaEntity() {
    }

    private ReservationJpaEntity(
            Concert concert,
            SeatMaster seatMaster,
            User user
    ) {
        this.concert = concert;
        this.seatMaster = seatMaster;
        this.user = user;
        this.price = seatMaster.getSeatInventory().getPrice();
    }

    public Concert getConcert() {
        return concert;
    }

    public User getUser() {
        return user;
    }

    public SeatMaster getSeatMaster() {
        return seatMaster;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
