package kr.hhplus.be.server.user.domain.concert.dto.response;

import java.time.LocalDate;

public record ConcertDetailResponse(
        long concertId,
        String title,
        String address,
        LocalDate startsAt,
        LocalDate endsAt,
        int availableSeatCount
) {
}
