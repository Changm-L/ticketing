package kr.hhplus.be.server.user.domain.reservation.application;

import java.util.List;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.user.domain.reservation.core.port.in.ReservationUseCase;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.adapter.ReservationPersistenceAdapter;
import kr.hhplus.be.server.user.domain.reservation.port.out.ReservationRepository;
import kr.hhplus.be.server.user.domain.reservation.presentation.dto.response.PlaceReservationResponse;

@Service
public class ReservationService implements ReservationUseCase {

    private final ReservationRepository repository;

    public ReservationService(ReservationPersistenceAdapter repository) {
        this.repository = repository;
    }

    @Override
    public PlaceReservationResponse placeReservation() {
        return null;
    }

    @Override
    public List<FindAllReservationResponse> findAll() {
        return this.repository.findAll();
    }
}
