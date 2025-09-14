package kr.hhplus.be.server.admin.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;

public record AdminConcertDetailResponse(
        long concertId,
        String title,
        String address,
        LocalDate startsAt,
        LocalDate endsAt,
        int availableSeatCount,
        ConcertStatus status
) {

    public static AdminConcertDetailResponse of(Concert concert) {
        return new AdminConcertDetailResponse(
                concert.getId(),
                concert.getTitle(),
                concert.getAddress(),
                concert.getStartsAt(),
                concert.getEndsAt(),
                50, // TODO: seat 설계 후 변경
                concert.getStatus()
        );
    }
}
