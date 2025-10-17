package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wallet_ledger")
public class WalletLedgerJpaEntity extends BaseTimeEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal balanceAfter;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private WalletJpaEntity walletJpaEntity;

    private WalletLedgerJpaEntity(
            WalletJpaEntity walletJpaEntity,
            TransactionType transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter
    ) {
        this.walletJpaEntity = walletJpaEntity;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public static WalletLedgerJpaEntity of(
            WalletJpaEntity walletJpaEntity,
            TransactionType transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter
    ) {
        return new WalletLedgerJpaEntity(
                walletJpaEntity,
                transactionType,
                amount,
                balanceAfter
        );
    }
}
