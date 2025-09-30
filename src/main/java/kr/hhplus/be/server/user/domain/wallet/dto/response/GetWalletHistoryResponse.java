package kr.hhplus.be.server.user.domain.wallet.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;

public record GetWalletHistoryResponse(
        long id,
        BigDecimal amount,
        BigDecimal balanceAfter,
        TransactionType type,
        LocalDateTime updatedAt
) {
}
