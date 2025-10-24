package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.repository;

import java.util.List;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.exception.WalletNotFoundException;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;

@Repository
public interface WalletJpaRepository extends JpaRepository<WalletJpaEntity, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse(
                        w.balance
                    )
                FROM WalletJpaEntity w
                WHERE  w.userJpaEntity.id = :userId
            """)
    GetBalanceResponse getWalletBalanceBy(long userId);

    default WalletJpaEntity getWalletByUserId(long userId) {
        return findWalletByUserJpaEntityId(userId).orElseThrow(WalletNotFoundException::new);
    }

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse(
                    wl.id,
                    wl.amount,
                    wl.balanceAfter,
                    wl.transactionType,
                    wl.updatedAt
                )
                FROM WalletLedgerJpaEntity wl
                LEFT JOIN FETCH WalletJpaEntity w on wl.walletJpaEntity.id = w.id
                LEFT JOIN FETCH UserJpaEntity u on w.userJpaEntity.id = :userId
                ORDER BY wl.createdAt DESC
            """)
    List<GetWalletHistoryResponse> getWalletHistoryByUserId(long userId);

    Optional<WalletJpaEntity> findWalletByUserJpaEntityId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select w from WalletJpaEntity w join fetch w.userJpaEntity where w.userJpaEntity.id = :userId")
    Optional<WalletJpaEntity> findWalletByUserIdForUpdate(long userId);
}
