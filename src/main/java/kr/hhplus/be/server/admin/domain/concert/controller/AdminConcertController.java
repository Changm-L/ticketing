package kr.hhplus.be.server.admin.domain.concert.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server._core.dto.response.CreateRes;
import kr.hhplus.be.server._core.dto.response.UpdateRes;
import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.admin.domain.concert.service.AdminConcertService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/concerts")
public class AdminConcertController {

    private final AdminConcertService adminConcertService;

    @GetMapping
    public ResponseEntity<ListResult<AdminConcertListResponse>> findAll() {
        List<AdminConcertListResponse> result = adminConcertService.findAllConcerts();
        return ApiResponse.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SingleResult<AdminConcertDetailResponse>> getById(
            @PathVariable long id
    ) {
        AdminConcertDetailResponse result = adminConcertService.getById(id);
        return ApiResponse.ok(result);
    }

    @PostMapping
    public ResponseEntity<SingleResult<CreateRes>> create(
            @Valid @RequestBody CreateConcertRequest request
    ) {
        long result = adminConcertService.create(request);
        return ApiResponse.create(result);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SingleResult<UpdateRes>> update(
            @PathVariable long id,
            @RequestBody UpdateConcertRequest request
    ) {
        long result = adminConcertService.update(id, request);
        return ApiResponse.update(result);
    }
}
