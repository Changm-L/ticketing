package kr.hhplus.be.server.admin.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;

public record AdminConcertListResponse(
        long concertId,
        String title,
        String address,
        int availableSeatCount,
        LocalDate date,
        ConcertStatus status
) {

    public static AdminConcertListResponse of(Concert concert) {
        return new AdminConcertListResponse(
                concert.getId(),
                concert.getTitle(),
                concert.getAddress(),
                50, //TODO: seat 설계 후 변경
                concert.getStartsAt(),
                concert.getStatus()
        );
    }
}
