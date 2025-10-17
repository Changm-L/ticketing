package kr.hhplus.be.server.user.domain.wallet.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;

public class WalletLedger {
    private Long            id;
    private long            walletId;
    private TransactionType transactionType;
    private BigDecimal      amount;
    private BigDecimal      balanceAfter;
    private LocalDateTime   createdAt;
    private LocalDateTime   updatedAt;

    private WalletLedger(
            Long id,
            long walletId,
            BigDecimal amount,
            BigDecimal balanceAfter,
            TransactionType type,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.walletId = walletId;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.transactionType = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static WalletLedger of(
            TransactionType transactionType,
            long id,
            long walletId,
            BigDecimal amount,
            BigDecimal balanceAfter,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new WalletLedger(
                id,
                walletId,
                amount,
                balanceAfter,
                transactionType,
                createdAt,
                updatedAt
        );
    }

    public static WalletLedger of(
            long walletId,
            TransactionType transactionType,
            BigDecimal amount,
            BigDecimal balanceAfter
    ) {
        return new WalletLedger(
                null,
                walletId,
                amount,
                balanceAfter,
                transactionType,
                null,
                null
        );
    }

    public long getId() {
        return id;
    }

    public long getWalletId() {
        return walletId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
