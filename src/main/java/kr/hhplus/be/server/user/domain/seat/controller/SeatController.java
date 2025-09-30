package kr.hhplus.be.server.user.domain.seat.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;
import kr.hhplus.be.server.user.domain.seat.service.SeatService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concerts")
public class SeatController {

    private final SeatService seatService;

    @GetMapping("/{concertId}/seats")
    public ResponseEntity<ListResult<FindAllSeatsResponse>> findAllAvailableSeats(
            @PathVariable long concertId
    ) {
        List<FindAllSeatsResponse> result = seatService.findAllAvailableSeats(concertId);
        return ApiResponse.ok(result);
    }
}
