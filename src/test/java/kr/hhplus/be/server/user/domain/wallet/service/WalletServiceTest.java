package kr.hhplus.be.server.user.domain.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.wallet.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.dto.request.WalletChargeRequest;
import kr.hhplus.be.server.user.domain.wallet.dto.response.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.dto.response.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.dto.response.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.entity.Wallet;
import kr.hhplus.be.server.user.domain.wallet.repository.WalletRepository;
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
    private WalletRepository walletRepository;

    @InjectMocks
    private WalletService walletService;

    @Test
    void getBalanceBy() {
        //given
        long userId = 1L;
        GetBalanceResponse expected = new GetBalanceResponse(
                BigDecimal.valueOf(10000L)
        );
        when(walletRepository.getWalletBalanceBy(userId)).thenReturn(expected);

        //when
        GetBalanceResponse result = walletService.getBalanceBy(userId);

        //then
        assertEquals(expected, result);
        assertThat(result.balance()).isEqualTo(expected.balance());
        assertThat(result.balance()).isPositive();
        verify(walletRepository).getWalletBalanceBy(userId);
    }

    @Test
    void charge() {
        //given
        long userId = 1L;
        BigDecimal amount = BigDecimal.valueOf(10000L);

        User user = AuthFixture.user();
        Wallet wallet = Wallet.of(user);
        WalletChargeRequest request = new WalletChargeRequest(amount);
        WalletChargeResponse expected = new WalletChargeResponse(TransactionType.CHARGE, amount);

        when(walletRepository.getWalletByUserId(userId)).thenReturn(wallet);

        //when
        WalletChargeResponse result = walletService.charge(userId, request);

        //then
        assertEquals(expected, result);
        assertThat(wallet.getBalance()).isEqualTo(amount);
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
        when(walletRepository.getWalletHistoryByUserId(userId)).thenReturn(expected);

        //when
        List<GetWalletHistoryResponse> result = walletService.getWalletHistory(userId);

        //then
        assertEquals(expected, result);
        assertThat(result.isEmpty()).isFalse();
        verify(walletRepository).getWalletHistoryByUserId(userId);
    }

}