package kr.hhplus.be.server.user.domain.payment.core.dto;

import java.math.BigDecimal;

public record PaymentResponse(
        long reservationId,
        BigDecimal price
) {
    public static PaymentResponse of(long reservationId, BigDecimal price) {
        return new PaymentResponse(reservationId, price);
    }
}
