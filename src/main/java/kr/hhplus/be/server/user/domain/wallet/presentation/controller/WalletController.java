package kr.hhplus.be.server.user.domain.wallet.presentation.controller;

import java.util.List;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server.user.domain.wallet.presentation.dto.WalletChargeRequest;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.application.service.WalletService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/wallet")
public class WalletController {

    private final WalletService walletService;

    @GetMapping
    public ResponseEntity<SingleResult<GetBalanceResponse>> getBalance(
            @RequestAttribute("userId") long userId
    ) {
        GetBalanceResponse result = walletService.getBalanceBy(userId);
        return ApiResponse.ok(result);
    }

    @PatchMapping("/charge")
    public ResponseEntity<SingleResult<WalletChargeResponse>> charge(
            @RequestAttribute("userId") long userId,
            @Valid @RequestBody WalletChargeRequest request
    ) {
        WalletChargeResponse result = walletService.charge(userId, request);
        return ApiResponse.ok(result);
    }

    @GetMapping("/transactions")
    public ResponseEntity<ListResult<GetWalletHistoryResponse>> getHistory(
            @RequestAttribute("userId") long userId
    ) {
        List<GetWalletHistoryResponse> result = walletService.getWalletHistory(userId);
        return ApiResponse.ok(result);
    }
}
