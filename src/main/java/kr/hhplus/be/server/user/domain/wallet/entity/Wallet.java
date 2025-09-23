package kr.hhplus.be.server.user.domain.wallet.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.exception.InvalidChargeAmountException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wallet extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal balance;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "wallet"
    )
    private final List<WalletLedger> walletLedgers = new ArrayList<>();

    @Version
    int version;

    private Wallet(
            User user,
            BigDecimal balance
    ) {
        this.user = user;
        this.balance = balance;
    }

    public static Wallet of(User user) {
        return new Wallet(user, BigDecimal.ZERO);
    }

    public void charge(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidChargeAmountException("충전 금액은 음수일 수 없습니다.");
        }

        balance = balance.add(amount);
        WalletLedger walletLedger = WalletLedger.of(
                this,
                TransactionType.CHARGE,
                amount,
                this.balance
        );

        walletLedgers.add(walletLedger);
    }
}
