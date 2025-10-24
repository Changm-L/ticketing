package kr.hhplus.be.server.user.domain.concert.core.port.out;

import java.util.List;

import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertListResponse;

public interface ConcertPort {
    List<ConcertListResponse> findAllConcertWithAvailableSeatCount();

    ConcertDetailResponse getConcertDetailById(long concertId);

}
