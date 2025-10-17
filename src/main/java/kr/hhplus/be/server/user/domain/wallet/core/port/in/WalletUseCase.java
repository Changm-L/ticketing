package kr.hhplus.be.server.user.domain.wallet.core.port.in;

import java.util.List;

import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.presentation.dto.WalletChargeRequest;

public interface WalletUseCase {
    WalletChargeResponse charge(long userId, WalletChargeRequest request);

    List<GetWalletHistoryResponse> getWalletHistory(long userId);
}
