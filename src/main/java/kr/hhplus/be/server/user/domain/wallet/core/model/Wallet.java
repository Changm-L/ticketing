package kr.hhplus.be.server.user.domain.wallet.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.exception.InvalidChargeAmountException;

public class Wallet {
    private final long               id;
    private final long               userId;
    private       BigDecimal         balance;
    private final List<WalletLedger> walletLedgers  = new ArrayList<>();
    private final List<WalletLedger> pendingLedgers = new ArrayList<>();
    private       LocalDateTime      createdAt;
    private       LocalDateTime      updatedAt;

    private Wallet(
            long id,
            long userId,
            BigDecimal balance,
            LocalDateTime createAt,
            LocalDateTime updateAt,
            List<WalletLedger> walletLedgers
    ) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.createdAt = createAt;
        this.updatedAt = updateAt;

        if (walletLedgers != null) {
            this.walletLedgers.addAll(walletLedgers);
        }
    }

    public static Wallet createWith(
            long id,
            long userId,
            BigDecimal balance,
            List<WalletLedger> walletLedgers,
            LocalDateTime createAt,
            LocalDateTime updateAt
    ) {
        return new Wallet(
                id,
                userId,
                balance,
                createAt,
                updateAt,
                walletLedgers
        );
    }

    public void charge(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidChargeAmountException("충전 금액은 0보다 작을 수 없습니다.");
        }

        balance = balance.add(amount);
        walletLedgers.add(WalletLedger.of(id, TransactionType.CHARGE, amount, balance));
        pendingLedgers.add(WalletLedger.of(id, TransactionType.CHARGE, amount, balance));
    }

    public void use(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
        balance = balance.subtract(amount);
        walletLedgers.add(WalletLedger.of(id, TransactionType.USE, amount, balance));
        pendingLedgers.add(WalletLedger.of(id, TransactionType.USE, amount, balance));
    }

    public List<WalletLedger> drainingWalletLedgers() {
        if (pendingLedgers.isEmpty()) {
            return List.of();
        }

        List<WalletLedger> copy = new ArrayList<>(pendingLedgers);
        pendingLedgers.clear();
        return copy;
    }

    public long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public List<WalletLedger> getWalletLedgers() {
        return walletLedgers;
    }

    public List<WalletLedger> getPendingLedgers() {
        return List.copyOf(pendingLedgers);
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
