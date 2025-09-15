package kr.hhplus.be.server.user.domain.concert.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository repository;

    @Transactional(readOnly = true)
    public List<ConcertListResponse> findAllConcerts() {
        return repository.findAllConcertWithAvailableSeatsCount();
    }

    @Transactional(readOnly = true)
    public ConcertDetailResponse getConcertById(long concertId) {
        Concert concert = repository.getById(concertId);
        return ConcertDetailResponse.of(concert);
    }
}
