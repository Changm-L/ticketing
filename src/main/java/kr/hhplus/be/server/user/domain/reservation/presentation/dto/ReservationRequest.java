package kr.hhplus.be.server.user.domain.reservation.presentation.dto;

public record ReservationRequest(
        long concertId,
        long seatInventoryId
) {
}
