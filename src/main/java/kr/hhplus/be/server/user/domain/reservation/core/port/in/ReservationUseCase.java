package kr.hhplus.be.server.user.domain.reservation.core.port.in;

import java.util.List;

import kr.hhplus.be.server.user.domain.reservation.presentation.dto.response.PlaceReservationResponse;

public interface ReservationUseCase {
    public PlaceReservationResponse placeReservation();

    public List<FindAllReservationResponse> findAll();
}
