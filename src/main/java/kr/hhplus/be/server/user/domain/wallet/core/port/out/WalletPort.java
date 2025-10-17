package kr.hhplus.be.server.user.domain.wallet.core.port.out;

import java.util.List;

import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;

public interface WalletPort {
    Wallet getWalletByUserId(long userId);

    List<GetWalletHistoryResponse> getWalletHistoryByUserId(long userId);

    Wallet save(Wallet wallet);

    GetBalanceResponse getWalletBalanceBy(long userId);
}
