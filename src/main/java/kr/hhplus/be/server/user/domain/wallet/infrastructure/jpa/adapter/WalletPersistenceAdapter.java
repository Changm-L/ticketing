package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.adapter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.model.WalletLedger;
import kr.hhplus.be.server.user.domain.wallet.core.port.out.WalletPort;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletLedgerJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.mapper.WalletMapper;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.repository.WalletJpaRepository;

@Component
@RequiredArgsConstructor
public class WalletPersistenceAdapter implements WalletPort {

    private final WalletMapper        walletMapper;
    private final WalletJpaRepository walletJpaRepository;

    @Override
    public Wallet getWalletByUserId(long userId) {
        WalletJpaEntity walletJpaEntity = walletJpaRepository.getWalletByUserId(userId);
        return walletMapper.toDomain(walletJpaEntity);
    }

    @Override
    public GetBalanceResponse getWalletBalanceBy(long userId) {
        return walletJpaRepository.getWalletBalanceBy(userId);
    }

    @Override
    public Wallet save(Wallet wallet) {
        WalletJpaEntity walletJpaEntity = walletJpaRepository.getReferenceById(wallet.getId());
        //fixme: walletJpaEntity.create로 domain -> jpa entity 치환 작업을 했는데
        // persistence container 같은 엔티티로 감지를 못하는 문제 발생.
        // 새로운 지갑 생성쿼리 발생으로 인해 jpa entity조회 후 가격 업데이트로 변경했는데 나은 방법이 있는가.
        walletJpaEntity.updateBalance(wallet.getBalance());

        for (WalletLedger walletLedger : wallet.drainingWalletLedgers()) {
            WalletLedgerJpaEntity walletLedgerJpaEntity = WalletLedgerJpaEntity.of(
                    walletJpaEntity,
                    walletLedger.getTransactionType(),
                    walletLedger.getAmount(),
                    walletLedger.getBalanceAfter()
            );
            walletJpaEntity.addWalletLedger(walletLedgerJpaEntity);
        }

        walletJpaRepository.save(walletJpaEntity);

        return walletMapper.toDomain(walletJpaEntity);
    }

    @Override
    public List<GetWalletHistoryResponse> getWalletHistoryByUserId(long userId) {
        return walletJpaRepository.getWalletHistoryByUserId(userId);
    }
}
