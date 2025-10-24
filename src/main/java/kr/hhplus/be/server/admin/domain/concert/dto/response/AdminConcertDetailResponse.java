package kr.hhplus.be.server.admin.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.core.constant.ConcertStatus;

public record AdminConcertDetailResponse(
        long concertId,
        String title,
        String address,
        LocalDate startsAt,
        LocalDate endsAt,
        int availableSeatCount,
        ConcertStatus status
) {
}
