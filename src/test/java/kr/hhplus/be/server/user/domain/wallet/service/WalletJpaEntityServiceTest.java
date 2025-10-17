package kr.hhplus.be.server.user.domain.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.user.domain.wallet.application.service.WalletService;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.core.model.Wallet;
import kr.hhplus.be.server.user.domain.wallet.core.model.WalletLedger;
import kr.hhplus.be.server.user.domain.wallet.core.port.out.WalletPort;
import kr.hhplus.be.server.user.domain.wallet.presentation.dto.WalletChargeRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WalletServiceTest {

    @Mock
    private WalletPort walletPort;

    @InjectMocks
    private WalletService walletService;

    @Test
    void getBalanceBy() {
        //given
        long userId = 1L;
        GetBalanceResponse expected = new GetBalanceResponse(
                BigDecimal.valueOf(10000L)
        );
        when(walletPort.getWalletBalanceBy(userId)).thenReturn(expected);

        //when
        GetBalanceResponse result = walletService.getBalanceBy(userId);

        //then
        assertEquals(expected, result);
        assertThat(result.balance()).isEqualTo(expected.balance());
        assertThat(result.balance()).isPositive();
        verify(walletPort).getWalletBalanceBy(userId);
    }

    @Test
    void charge() {
        //given
        long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10000L);

        WalletLedger walletLedger = WalletLedger.of(
                1L,
                TransactionType.USE,
                BigDecimal.ZERO,
                amount
        );
        List<WalletLedger> walletLedgers = List.of(walletLedger);
        Wallet wallet = Wallet.createWith(
                1L,
                userId,
                BigDecimal.ZERO,
                walletLedgers,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        WalletChargeRequest request = new WalletChargeRequest(amount);
        WalletChargeResponse expected = new WalletChargeResponse(TransactionType.CHARGE, amount);

        when(walletPort.getWalletByUserId(userId)).thenReturn(wallet);

        //when
        WalletChargeResponse result = walletService.charge(userId, request);

        //then
        assertEquals(expected, result);
        assertThat(wallet.getBalance()).isEqualTo(amount);
        assertThat(wallet.getWalletLedgers().get(0).getBalanceAfter()).isEqualTo(amount);
    }

    @Test
    void getWalletHistory() {
        //given
        long userId = 1L;
        List<GetWalletHistoryResponse> expected = List.of(
                new GetWalletHistoryResponse(
                        userId,
                        BigDecimal.valueOf(10000L),
                        BigDecimal.valueOf(20000L),
                        TransactionType.CHARGE,
                        LocalDateTime.now()
                )
        );
        when(walletPort.getWalletHistoryByUserId(userId)).thenReturn(expected);

        //when
        List<GetWalletHistoryResponse> result = walletService.getWalletHistory(userId);

        //then
        assertEquals(expected, result);
        assertThat(result.isEmpty()).isFalse();
        verify(walletPort).getWalletHistoryByUserId(userId);
    }

}