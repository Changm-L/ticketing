package kr.hhplus.be.server.user.domain.concert.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.core.dto.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.core.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.core.port.in.ConcertUseCase;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.ConcertRepository;

@Service
@RequiredArgsConstructor
public class ConcertService implements ConcertUseCase {

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
