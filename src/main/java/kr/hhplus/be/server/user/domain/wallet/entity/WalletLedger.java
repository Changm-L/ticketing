package kr.hhplus.be.server.user.domain.wallet.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletLedger extends BaseTimeEntity {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal balanceAfter;

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id", nullable = false)
    private Wallet wallet;

    private WalletLedger(
            Wallet wallet,
            TransactionType transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter
    ) {
        this.wallet = wallet;
        this.transactionType = transactionType;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public static WalletLedger of(
            Wallet wallet,
            TransactionType transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter
    ) {
        return new WalletLedger(
                wallet,
                transactionType,
                amount,
                balanceAfter
        );
    }
}
