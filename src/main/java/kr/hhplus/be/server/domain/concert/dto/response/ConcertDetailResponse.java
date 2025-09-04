package kr.hhplus.be.server.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.domain.concert.entity.Concert;

public record ConcertDetailResponse(
        long concertId,
        String title,
        String address,
        LocalDate startsAt,
        LocalDate endsAt,
        int availableSeatCount
) {

    public static ConcertDetailResponse of(
            Concert concert
    ) {
        return new ConcertDetailResponse(
                concert.getId(),
                concert.getTitle(),
                concert.getAddress(),
                concert.getStartsAt(),
                concert.getEndsAt(),
                50 // TODO: 추후 seat 설계 후 변경
        );
    }
}
