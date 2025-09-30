package kr.hhplus.be.server.user.domain.payment.presentation.controller;

import java.util.List;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.ListResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.port.in.PaymentUseCase;
import kr.hhplus.be.server.user.domain.payment.presentation.dto.PaymentRequest;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    public PaymentController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    @PostMapping
    public ResponseEntity<SingleResult<PaymentResponse>> payment(
            @RequestAttribute("userId") long userId,
            @Valid @RequestBody PaymentRequest request
    ) {
        PaymentResponse result = paymentUseCase.payment(userId, request.reservationId());
        return ApiResponse.ok(result);
    }

    @GetMapping
    public ResponseEntity<ListResult<FindAllPaymentResponse>> findAll(
            @RequestAttribute("userId") long userId
    ) {
        List<FindAllPaymentResponse> result = paymentUseCase.findAll(userId);
        return ApiResponse.ok(result);
    }

}
