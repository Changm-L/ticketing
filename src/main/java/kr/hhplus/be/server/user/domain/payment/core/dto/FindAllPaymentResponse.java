package kr.hhplus.be.server.user.domain.payment.core.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record FindAllPaymentResponse(
        long id,
        BigDecimal price,
        String title,
        LocalDate startsAt
) {
}
