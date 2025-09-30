package kr.hhplus.be.server.user.domain.wallet.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.wallet.dto.response.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.dto.response.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.entity.Wallet;
import kr.hhplus.be.server.user.domain.wallet.exception.WalletNotFoundException;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.wallet.dto.response.GetBalanceResponse(
                        w.balance
                    )
                FROM Wallet w
                WHERE  w.user.id = :userId
            """)
    GetBalanceResponse getWalletBalanceBy(long userId);

    default Wallet getWalletByUserId(long userId) {
        return findWalletByUserId(userId).orElseThrow(WalletNotFoundException::new);
    }

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.wallet.dto.response.GetWalletHistoryResponse(
                    wl.id,
                    wl.amount,
                    wl.balanceAfter,
                    wl.transactionType,
                    wl.updatedAt
                )
                FROM WalletLedger wl
                LEFT JOIN FETCH Wallet w on wl.wallet.id = w.id
                LEFT JOIN FETCH User u on w.user.id = :userId
                ORDER BY wl.createdAt DESC
            """)
    List<GetWalletHistoryResponse> getWalletHistoryByUserId(long userId);

    Optional<Wallet> findWalletByUserId(long userId);
}
