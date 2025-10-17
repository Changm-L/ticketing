package kr.hhplus.be.server.user.domain.wallet.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.port.in.WalletUseCase;
import kr.hhplus.be.server.user.domain.wallet.core.port.out.WalletPort;
import kr.hhplus.be.server.user.domain.wallet.presentation.dto.WalletChargeRequest;

@Service
@RequiredArgsConstructor
public class WalletService implements WalletUseCase {

    private final WalletPort walletPort;

    @Transactional(readOnly = true)
    public GetBalanceResponse getBalanceBy(long userId) {
        return walletPort.getWalletBalanceBy(userId);
    }

    @Transactional
    public WalletChargeResponse charge(
            long userId,
            WalletChargeRequest request
    ) {
        Wallet wallet = walletPort.getWalletByUserId(userId);
        wallet.charge(request.amount());
        walletPort.save(wallet);

        return WalletChargeResponse.of(TransactionType.CHARGE, wallet.getBalance());
    }

    @Transactional(readOnly = true)
    public List<GetWalletHistoryResponse> getWalletHistory(long userId) {
        return walletPort.getWalletHistoryByUserId(userId);
    }
}
