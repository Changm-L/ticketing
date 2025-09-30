package kr.hhplus.be.server.user.domain.reservation.core.port.in;

import java.util.List;

import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.dto.PlaceReservationResponse;

public interface ReservationUseCase {
    PlaceReservationResponse placeReservation(
            long userId,
            long concertId,
            long seatInventoryId
    );

    List<FindAllReservationResponse> findAll(long userId);
}
