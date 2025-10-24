package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper;

import java.math.BigDecimal;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Component
public class ReservationMapper {

    public Reservation toDomain(
            ReservationJpaEntity entity,
            BigDecimal price
    ) {
        return Reservation.createWith(
                entity.getId(),
                entity.getUserJpaEntity(),
                entity.getConcertJpaEntity(),
                entity.getSeatInventoryJpaEntity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                price
        );
    }

    public ReservationJpaEntity toJpaEntity(
            ConcertJpaEntity concertJpaEntity,
            UserJpaEntity userJpaEntity,
            SeatInventoryJpaEntity seatInventoryJpaEntity,
            BigDecimal price
    ) {
        return ReservationJpaEntity.createWith(
                concertJpaEntity,
                seatInventoryJpaEntity,
                userJpaEntity,
                price
        );
    }
}
