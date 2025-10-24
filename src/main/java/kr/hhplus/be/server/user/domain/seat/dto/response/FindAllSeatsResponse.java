package kr.hhplus.be.server.user.domain.seat.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;

public record FindAllSeatsResponse(
        long concertId,
        LocalDate concertDate,
        long seatId,
        long seatNo,
        SeatStatus status
) {

    public static FindAllSeatsResponse of(
            ConcertJpaEntity concertJpaEntity,
            SeatMasterJpaEntity seatMasterJpaEntity
    ) {
        return new FindAllSeatsResponse(
                concertJpaEntity.getId(),
                concertJpaEntity.getStartsAt(),
                seatMasterJpaEntity.getId(),
                seatMasterJpaEntity.getSeatNo(),
                seatMasterJpaEntity.getSeatInventoryJpaEntity().getSeatStatus()
        );
    }
}
