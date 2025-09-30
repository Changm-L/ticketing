package kr.hhplus.be.server.user.domain.concert.dto.response;

import java.time.LocalDate;

public record ConcertListResponse(
        long concertId,
        String title,
        String address,
        int availableSeatCount,
        LocalDate startsAt
) {
}
