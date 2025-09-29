package kr.hhplus.be.server.user.domain.reservation.core.port.out;

import java.util.List;

import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;

public interface ReservationPort {

    Reservation getById(long id);

    Reservation place(long userId, long concertId, long seatInventoryId);

    List<FindAllReservationResponse> findAllByUserId(long userId);
}
