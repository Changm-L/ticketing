package kr.hhplus.be.server.user.domain.concert.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;
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
        ConcertDetailResponse concertDetailResponse = repository.getConcertDetailById(concertId);
        if (ObjectUtils.isEmpty(concertDetailResponse)) {
            throw new ConcertNotFoundException();
        }

        return concertDetailResponse;
    }
}
