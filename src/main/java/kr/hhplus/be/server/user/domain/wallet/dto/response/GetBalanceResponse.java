package kr.hhplus.be.server.user.domain.wallet.dto.response;

import java.math.BigDecimal;

public record GetBalanceResponse(
        BigDecimal balance
) {
}
