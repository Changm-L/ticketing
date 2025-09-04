package kr.hhplus.be.server.domain.concert.dto.response;

import java.time.LocalDate;

import kr.hhplus.be.server.domain.concert.entity.Concert;

public record ConcertListResponse(
        long concertId,
        String title,
        String address,
        int availableSeatCount,
        LocalDate startsAt
) {

    public static ConcertListResponse of(
            Concert concert
    ) {
        return new ConcertListResponse(
                concert.getId(),
                concert.getTitle(),
                concert.getAddress(),
                50, // TODO: 좌석 Entity 생성 후 변경
                concert.getStartsAt()
        );
    }
}
