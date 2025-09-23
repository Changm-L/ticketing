package kr.hhplus.be.server.user.domain.wallet.dto.response;

import java.math.BigDecimal;

import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;

public record WalletChargeResponse(
        TransactionType type,
        BigDecimal balanceAfter
) {

    public static WalletChargeResponse of(
            TransactionType type,
            BigDecimal balanceAfter
    ) {
        return new WalletChargeResponse(type, balanceAfter);
    }
}
