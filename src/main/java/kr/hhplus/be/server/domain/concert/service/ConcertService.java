package kr.hhplus.be.server.domain.concert.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.domain.concert.entity.Concert;
import kr.hhplus.be.server.domain.concert.repository.ConcertRepository;

@Service
@RequiredArgsConstructor
public class ConcertService {

    private final ConcertRepository repository;

    public List<ConcertListResponse> findAllConcerts() {
        return repository.findAll().stream()
                         .map(ConcertListResponse::of)
                         .toList();
    }

    public ConcertDetailResponse getConcertById(long concertId) {
        Concert concert = repository.getById(concertId);
        return ConcertDetailResponse.of(concert);
    }
}
