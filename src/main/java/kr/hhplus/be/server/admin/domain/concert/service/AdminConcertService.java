package kr.hhplus.be.server.admin.domain.concert.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.SeatGenerator;
import kr.hhplus.be.server.user.domain.concert.dto.response.SeatBatch;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;

@Service
@RequiredArgsConstructor
public class AdminConcertService {

    private final ConcertRepository concertRepository;
    private final SeatGenerator     seatGenerator;

    @Transactional(readOnly = true)
    public List<AdminConcertListResponse> findAllConcerts() {
        return concertRepository.findAll()
                                .stream()
                                .map(AdminConcertListResponse::of)
                                .toList();
    }

    @Transactional(readOnly = true)
    public AdminConcertDetailResponse getById(long id) {
        Concert concert = concertRepository.getById(id);
        return AdminConcertDetailResponse.of(concert);
    }

    @Transactional
    public long create(CreateConcertRequest request) {
        Concert concert = new Concert(request);
        SeatBatch seatBatch = seatGenerator.generateSeatMasterAndInventory(concert, 50);
        concert.resetSeatsWith(seatBatch);
        concertRepository.save(concert);

        return concert.getId();
    }

    @Transactional
    public long update(long id, UpdateConcertRequest request) {
        Concert concert = concertRepository.getById(id);
        concert.update(request);
        concertRepository.save(concert);

        return concert.getId();
    }
}
