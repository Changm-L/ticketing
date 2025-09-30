package kr.hhplus.be.server.user.domain.wallet.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.Positive;

public record WalletChargeRequest(
        @Positive BigDecimal amount
) {
}
