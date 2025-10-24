package kr.hhplus.be.server.user.domain.concert.core.dto;

import java.time.LocalDate;

public record ConcertListResponse(
        long concertId,
        String title,
        String address,
        int availableSeatCount,
        LocalDate startsAt
) {
}
