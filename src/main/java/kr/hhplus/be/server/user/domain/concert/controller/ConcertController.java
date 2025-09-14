package kr.hhplus.be.server.user.domain.concert.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.service.ConcertService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/concerts")
public class ConcertController {

    private final ConcertService concertService;

    @GetMapping
    public ResponseEntity<ListResult<ConcertListResponse>> findAllConcert() {
        List<ConcertListResponse> result = concertService.findAllConcerts();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResult<ConcertDetailResponse>> getConcertById(
            @PathVariable long id
    ) {
        return ApiResponse.ok(concertService.getConcertById(id));
    }
}
