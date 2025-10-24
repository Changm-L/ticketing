package kr.hhplus.be.server.admin.domain.concert.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.application.SeatGenerator;
import kr.hhplus.be.server.user.domain.concert.core.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.ConcertRepository;

@Service
@RequiredArgsConstructor
public class AdminConcertService {

    private final ConcertRepository concertRepository;
    private final SeatGenerator     seatGenerator;

    @Transactional(readOnly = true)
    public List<AdminConcertListResponse> findAllConcerts() {
        return concertRepository.findAllAdminConcerts();
    }

    @Transactional(readOnly = true)
    public AdminConcertDetailResponse getById(long id) {
        AdminConcertDetailResponse response = concertRepository.getAdminConcertDetailById(id);
        if (ObjectUtils.isEmpty(response)) {
            throw new ConcertNotFoundException();
        }

        return response;
    }

    @Transactional
    public long create(CreateConcertRequest request) {
        ConcertJpaEntity concertJpaEntity = new ConcertJpaEntity(request);
        List<SeatMasterJpaEntity> seatMasterJpaEntityList = seatGenerator.generateSeatMasterAndInventory(
                concertJpaEntity,
                50
        );
        concertJpaEntity.resetSeatsWith(seatMasterJpaEntityList);
        concertRepository.save(concertJpaEntity);

        return concertJpaEntity.getId();
    }

    @Transactional
    public long update(long id, UpdateConcertRequest request) {
        ConcertJpaEntity concertJpaEntity = concertRepository.getById(id);
        concertJpaEntity.update(request);
        concertRepository.save(concertJpaEntity);

        return concertJpaEntity.getId();
    }
}
