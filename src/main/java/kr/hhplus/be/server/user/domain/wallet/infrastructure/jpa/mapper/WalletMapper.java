package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.mapper;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.model.WalletLedger;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletLedgerJpaEntity;

@Component
public class WalletMapper {

    public Wallet toDomain(WalletJpaEntity walletJpaEntity) {
        List<WalletLedger> walletLedgers = new ArrayList<>();
        for (WalletLedgerJpaEntity walletLedgerJpaEntity : walletJpaEntity.getWalletLedgerJpaEntities()) {
            walletLedgers.add(
                    WalletLedger.of(
                            walletLedgerJpaEntity.getTransactionType(),
                            walletLedgerJpaEntity.getId(),
                            walletJpaEntity.getId(),
                            walletLedgerJpaEntity.getAmount(),
                            walletLedgerJpaEntity.getBalanceAfter(),
                            walletLedgerJpaEntity.getCreatedAt(),
                            walletLedgerJpaEntity.getUpdatedAt()
                    )
            );
        }

        return Wallet.createWith(
                walletJpaEntity.getId(),
                walletJpaEntity.getUserJpaEntity().getId(),
                walletJpaEntity.getBalance(),
                walletLedgers,
                walletJpaEntity.getCreatedAt(),
                walletJpaEntity.getUpdatedAt()
        );
    }
}
