package kr.hhplus.be.server.user.domain.payment.presentation.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull long reservationId
) {
}
