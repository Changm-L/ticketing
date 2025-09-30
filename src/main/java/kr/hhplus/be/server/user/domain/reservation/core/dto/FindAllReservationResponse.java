package kr.hhplus.be.server.user.domain.reservation.core.dto;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.constant.SeatStatus;

public record FindAllReservationResponse(
        long id,
        long concertId,
        String title,
        SeatStatus status,
        LocalDate startsAt
) {
}
