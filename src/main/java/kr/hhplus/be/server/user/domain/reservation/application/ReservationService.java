package kr.hhplus.be.server.user.domain.reservation.application;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.dto.PlaceReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.in.ReservationUseCase;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.seat.exception.SeatNotFoundException;

@Service
@RequiredArgsConstructor
public class ReservationService implements ReservationUseCase {

    private final ReservationPort             reservationPort;
    private final SeatInventoryReadRepository seatInventoryReadRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FindAllReservationResponse> findAll(long userId) {
        return reservationPort.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public PlaceReservationResponse placeReservation(
            long userId,
            long concertId,
            long seatInventoryId
    ) {
        Optional<SeatInventoryJpaEntity> si = seatInventoryReadRepository.findByConcertIdAndSeatInventoryId(
                concertId,
                seatInventoryId
        );
        if (si.isEmpty()) {
            throw new SeatNotFoundException();
        }

        SeatInventoryJpaEntity seatInventoryJpaEntity = si.get();
        seatInventoryJpaEntity.held();

        Reservation reservation = reservationPort.place(userId, concertId, seatInventoryId);

        return PlaceReservationResponse.of(reservation);
    }
}
