package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.entity.User;

@Component
public class ReservationMapper {

    public Reservation toDomain(
            ReservationJpaEntity entity,
            BigDecimal price
    ) {
        return Reservation.createWith(
                entity.getId(),
                entity.getUser(),
                entity.getConcert(),
                entity.getSeatInventory(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                price
        );
    }

    public ReservationJpaEntity toJpaEntity(
            Concert concert,
            User user,
            SeatInventory seatInventory,
            BigDecimal price
    ) {
        return ReservationJpaEntity.createWith(
                concert,
                seatInventory,
                user,
                price
        );
    }
}
