package kr.hhplus.be.server.user.domain.concert.core.port.in;

import java.util.List;

import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertListResponse;

public interface ConcertUseCase {
    List<ConcertListResponse> findAllConcerts();

    ConcertDetailResponse getConcertById(long concertId);
}
