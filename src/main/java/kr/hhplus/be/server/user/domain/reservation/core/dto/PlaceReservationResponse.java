package kr.hhplus.be.server.user.domain.reservation.core.dto;

import java.math.BigDecimal;

import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;

public record PlaceReservationResponse(
        long concertId,
        long seatInventoryId,
        BigDecimal price
) {

    public static PlaceReservationResponse of(
            Reservation reservation
    ) {
        return new PlaceReservationResponse(
                reservation.getConcertId(),
                reservation.getSeatInventoryId(),
                reservation.getPrice()
        );
    }
}
