package kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.adapter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ObjectUtils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.model.WalletLedger;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.entity.WalletJpaEntity;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.mapper.WalletMapper;
import kr.hhplus.be.server.user.domain.wallet.infrastructure.jpa.repository.WalletJpaRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletPersistenceAdapterTest {

    @Mock
    private WalletMapper walletMapper;

    @Mock
    private WalletJpaRepository walletJpaRepository;

    @InjectMocks
    private WalletPersistenceAdapter walletPersistenceAdapter;

    @Test
    void getWalletByUserId() {
        //given
        long userId = 1L;
        long walletId = 1L;
        User user = AuthFixture.user();
        WalletLedger walletLedger = WalletLedger.of(
                walletId,
                TransactionType.CHARGE,
                BigDecimal.valueOf(1000L),
                BigDecimal.valueOf(10000L)
        );
        Wallet expected = Wallet.createWith(
                walletId,
                userId,
                BigDecimal.valueOf(10000L),
                List.of(walletLedger),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
        WalletJpaEntity walletJpaEntity = WalletJpaEntity.of(user);
        when(walletJpaRepository.getWalletByUserId(userId)).thenReturn(walletJpaEntity);
        when(walletMapper.toDomain(walletJpaEntity)).thenReturn(expected);

        //when
        Wallet result = walletPersistenceAdapter.getWalletByUserId(userId);

        //then
        assertEquals(expected, result);
        assertEquals(expected.getBalance(), result.getBalance());
        assertEquals(expected.getUserId(), result.getUserId());
    }

    @Test
    void getWalletBalanceBy() {
        //given
        long userId = 1L;
        GetBalanceResponse expected = new GetBalanceResponse(BigDecimal.valueOf(1000L));
        when(walletJpaRepository.getWalletBalanceBy(userId)).thenReturn(expected);

        //when
        GetBalanceResponse result = walletPersistenceAdapter.getWalletBalanceBy(userId);

        //then
        assertEquals(expected, result);
        assertEquals(expected.balance(), result.balance());
    }

    @Nested
    class save {
        @Test
        void pending_ledgers가_없으면_balance만_업데이트_한다() {
            //given
            long userId = 1L;
            long walletId = 1L;
            User user = AuthFixture.user();
            WalletLedger walletLedger = WalletLedger.of(
                    userId,
                    TransactionType.CHARGE,
                    BigDecimal.valueOf(10000L),
                    BigDecimal.valueOf(10000L)
            );
            List<WalletLedger> walletLedgers = List.of(walletLedger);
            Wallet expected = Wallet.createWith(
                    walletId,
                    userId,
                    BigDecimal.valueOf(10000L),
                    walletLedgers,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            WalletJpaEntity walletJpaEntity = WalletJpaEntity.of(user);
            ReflectionTestUtils.setField(walletJpaEntity, "id", walletId);
            ReflectionTestUtils.setField(walletJpaEntity, "balance", BigDecimal.valueOf(1000L));
            when(walletJpaRepository.getReferenceById(walletId)).thenReturn(walletJpaEntity);
            when(walletMapper.toDomain(walletJpaEntity)).thenReturn(expected);

            //when
            Wallet result = walletPersistenceAdapter.save(expected);

            //then
            verify(walletJpaRepository).save(walletJpaEntity);
            verify(walletMapper).toDomain(walletJpaEntity);
            assertEquals(expected, result);
            assertEquals(expected.getId(), result.getId());
            assertEquals(expected.getBalance(), result.getBalance());
        }

        @Test
        void pending_leddgers가_있으면_wallet_ledgers도_업데이트한다() {
            //given
            long userId = 1L;
            long walletId = 1L;
            User user = AuthFixture.user();
            WalletLedger walletLedger = WalletLedger.of(
                    userId,
                    TransactionType.CHARGE,
                    BigDecimal.valueOf(10000L),
                    BigDecimal.valueOf(10000L)
            );
            WalletLedger walletLedger2 = WalletLedger.of(
                    userId,
                    TransactionType.CHARGE,
                    BigDecimal.valueOf(1000L),
                    BigDecimal.valueOf(11000L)
            );

            List<WalletLedger> walletLedgers = List.of(walletLedger, walletLedger2);
            Wallet expected = Wallet.createWith(
                    walletId,
                    userId,
                    BigDecimal.valueOf(10000L),
                    walletLedgers,
                    LocalDateTime.now(),
                    LocalDateTime.now()
            );
            ReflectionTestUtils.setField(expected, "pendingLedgers", new ArrayList<>(List.of(walletLedger2)));
            WalletJpaEntity walletJpaEntity = WalletJpaEntity.of(user);
            ReflectionTestUtils.setField(walletJpaEntity, "id", walletId);
            ReflectionTestUtils.setField(walletJpaEntity, "balance", BigDecimal.valueOf(1000L));
            when(walletJpaRepository.getReferenceById(walletId)).thenReturn(walletJpaEntity);
            when(walletMapper.toDomain(walletJpaEntity)).thenReturn(expected);

            //when
            Wallet result = walletPersistenceAdapter.save(expected);

            //then
            assertTrue(ObjectUtils.isEmpty(result.getPendingLedgers()));
            verify(walletJpaRepository).save(walletJpaEntity);
            assertEquals(result.getWalletLedgers().get(1).getBalanceAfter(), walletLedger2.getBalanceAfter());
        }
    }

    @Test
    void getWalletHistoryByUserId() {
        //given
        long userId = 1L;
        GetWalletHistoryResponse walletHistoryResponse = new GetWalletHistoryResponse(
                1L,
                BigDecimal.valueOf(1000L),
                BigDecimal.valueOf(1000L),
                TransactionType.CHARGE,
                LocalDateTime.now()
        );
        GetWalletHistoryResponse walletHistoryResponse2 = new GetWalletHistoryResponse(
                2L,
                BigDecimal.valueOf(1000L),
                BigDecimal.valueOf(2000L),
                TransactionType.CHARGE,
                LocalDateTime.now()
        );
        List<GetWalletHistoryResponse> expected = List.of(walletHistoryResponse, walletHistoryResponse2);
        when(walletJpaRepository.getWalletHistoryByUserId(userId)).thenReturn(expected);

        //when
        List<GetWalletHistoryResponse> result = walletPersistenceAdapter.getWalletHistoryByUserId(userId);

        //then
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(0).balanceAfter(), result.get(0).balanceAfter());

    }

}