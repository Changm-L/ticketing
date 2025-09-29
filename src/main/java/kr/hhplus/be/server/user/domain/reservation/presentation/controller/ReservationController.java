package kr.hhplus.be.server.user.domain.reservation.presentation.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.dto.PlaceReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.port.in.ReservationUseCase;
import kr.hhplus.be.server.user.domain.reservation.presentation.dto.ReservationRequest;

@RestController
@RequestMapping("/v1/reservations")
public class ReservationController {
    private final ReservationUseCase reservationUseCase;

    public ReservationController(ReservationUseCase reservationUseCase) {
        this.reservationUseCase = reservationUseCase;
    }

    @GetMapping
    public ResponseEntity<ListResult<FindAllReservationResponse>> findAllReservations(
            @RequestAttribute("userId") long userId
    ) {
        List<FindAllReservationResponse> result = reservationUseCase.findAll(userId);
        return ApiResponse.ok(result);
    }

    @PostMapping
    public ResponseEntity<SingleResult<PlaceReservationResponse>> reservation(
            @RequestAttribute("userId") long userId,
            @Valid @RequestBody ReservationRequest request
    ) {
        PlaceReservationResponse result = reservationUseCase.placeReservation(
                userId,
                request.concertId(),
                request.seatInventoryId()
        );
        return ApiResponse.ok(result);
    }

}
