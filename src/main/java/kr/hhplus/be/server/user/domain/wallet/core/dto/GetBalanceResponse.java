package kr.hhplus.be.server.user.domain.wallet.core.dto;

import java.math.BigDecimal;

public record GetBalanceResponse(
        BigDecimal balance
) {
}
