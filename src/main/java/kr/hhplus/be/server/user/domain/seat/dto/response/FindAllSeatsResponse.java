package kr.hhplus.be.server.user.domain.seat.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;

public record FindAllSeatsResponse(
        long concertId,
        LocalDate concertDate,
        long seatId,
        long seatNo
) {

    public static FindAllSeatsResponse of(
            Concert concert,
            SeatMaster seatMaster
    ) {
        return new FindAllSeatsResponse(
                concert.getId(),
                concert.getStartsAt(),
                seatMaster.getId(),
                seatMaster.getSeatNo()
        );
    }
}
