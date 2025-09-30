package kr.hhplus.be.server.admin.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;

public record AdminConcertListResponse(
        long concertId,
        String title,
        String address,
        int availableSeatCount,
        LocalDate startsAt,
        ConcertStatus status
) {
}
