package kr.hhplus.be.server.user.domain.wallet.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.dto.request.WalletChargeRequest;
import kr.hhplus.be.server.user.domain.wallet.dto.response.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.dto.response.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.dto.response.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.entity.Wallet;
import kr.hhplus.be.server.user.domain.wallet.repository.WalletRepository;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;

    @Transactional(readOnly = true)
    public GetBalanceResponse getBalanceBy(long userId) {
        return walletRepository.getWalletBalanceBy(userId);
    }

    @Transactional
    public WalletChargeResponse charge(
            long userId,
            WalletChargeRequest request
    ) {
        Wallet wallet = walletRepository.getWalletByUserId(userId);
        wallet.charge(request.amount());
        walletRepository.save(wallet);

        return WalletChargeResponse.of(TransactionType.CHARGE, wallet.getBalance());
    }

    @Transactional(readOnly = true)
    public List<GetWalletHistoryResponse> getWalletHistory(long userId) {
        return walletRepository.getWalletHistoryByUserId(userId);
    }
}
