package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.user.domain.payment.core.exception.InsufficientBalanceException;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.exception.InvalidChargeAmountException;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;

/**
 * TODO: 예약 대기 금액 필드 생성 후
 *  예약 시 balance - 에약 금액
 *  예약 완료 시 에약 대기 금액 차감
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "wallet")
public class WalletJpaEntity extends BaseTimeEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserJpaEntity userJpaEntity;

    @Column(nullable = false, precision = 16, scale = 2)
    private BigDecimal balance;

    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true,
            mappedBy = "walletJpaEntity"
    )
    private final List<WalletLedgerJpaEntity> walletLedgerJpaEntities = new ArrayList<>();

    @Version
    int version;

    private WalletJpaEntity(
            UserJpaEntity userJpaEntity,
            BigDecimal balance
    ) {
        this.userJpaEntity = userJpaEntity;
        this.balance = balance;
    }

    public static WalletJpaEntity of(UserJpaEntity userJpaEntity) {
        return new WalletJpaEntity(userJpaEntity, BigDecimal.ZERO);
    }

    public static WalletJpaEntity createWith(
            UserJpaEntity userJpaEntity,
            Wallet wallet
    ) {
        return new WalletJpaEntity(
                userJpaEntity,
                wallet.getBalance()
        );
    }

    public void charge(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidChargeAmountException("충전 금액은 0보다 작을 수 없습니다.");
        }

        balance = balance.add(amount);
        WalletLedgerJpaEntity walletLedgerJpaEntity = WalletLedgerJpaEntity.of(
                this,
                TransactionType.CHARGE,
                amount,
                this.balance
        );

        walletLedgerJpaEntities.add(walletLedgerJpaEntity);
    }

    public BigDecimal use(BigDecimal amount) {
        if (this.balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }

        this.balance = this.balance.subtract(amount);

        WalletLedgerJpaEntity walletLedgerJpaEntity = WalletLedgerJpaEntity.of(
                this,
                TransactionType.USE,
                amount,
                this.balance
        );
        walletLedgerJpaEntities.add(walletLedgerJpaEntity);

        return this.balance;
    }

    public void addWalletLedger(WalletLedgerJpaEntity walletLedgerJpaEntity) {
        if (ObjectUtils.isEmpty(walletLedgerJpaEntity)) {
            throw new IllegalArgumentException("지갑 이력을 저장할 수 없습니다.");
        }

        walletLedgerJpaEntities.add(walletLedgerJpaEntity);
    }

    public void updateBalance(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("0보다 작은 값으로 변경할 수 없습니다.");
        }
        this.balance = amount;
    }
}
