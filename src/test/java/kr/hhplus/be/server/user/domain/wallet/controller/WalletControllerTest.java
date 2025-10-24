package kr.hhplus.be.server.user.domain.wallet.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.core.utils.ControllerTest;
import kr.hhplus.be.server.user.domain.wallet.application.service.WalletService;
import kr.hhplus.be.server.user.domain.wallet.core.constant.TransactionType;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetBalanceResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.GetWalletHistoryResponse;
import kr.hhplus.be.server.user.domain.wallet.core.dto.WalletChargeResponse;
import kr.hhplus.be.server.user.domain.wallet.presentation.controller.WalletController;
import kr.hhplus.be.server.user.domain.wallet.presentation.dto.WalletChargeRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WalletService walletService;

    @Test
    void getBalance() throws Exception {
        //given
        long userId = 1L;
        GetBalanceResponse expected = new GetBalanceResponse(
                BigDecimal.valueOf(10000L)
        );
        when(walletService.getBalanceBy(userId)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/wallet")
                               .contentType(MediaType.APPLICATION_JSON)
                               .requestAttr("userId", userId)
               )
               .andExpect(status().isOk())
               .andExpect(
                       jsonPath("$.data.balance").value(expected.balance())
               );

    }

    @Test
    void charge() throws Exception {
        //given
        long userId = 1L;
        WalletChargeRequest request = new WalletChargeRequest(BigDecimal.valueOf(10000L));
        WalletChargeResponse expected = new WalletChargeResponse(
                TransactionType.CHARGE,
                BigDecimal.valueOf(20000L)
        );
        when(walletService.charge(userId, request)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       patch("/v1/wallet/charge")
                               .requestAttr("userId", userId)
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.type").value(expected.type().name()))
               .andExpect(jsonPath("$.data.balanceAfter").value(expected.balanceAfter()));
    }

    @Test
    void getHistory() throws Exception {
        //given
        long userId = 1L;
        GetWalletHistoryResponse history1 = new GetWalletHistoryResponse(
                1L,
                BigDecimal.valueOf(1000L),
                BigDecimal.valueOf(10000L),
                TransactionType.CHARGE,
                LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)
        );
        List<GetWalletHistoryResponse> expected = List.of(history1);
        when(walletService.getWalletHistory(userId)).thenReturn(expected);

        //when && then
        mockMvc.perform(
                       get("/v1/wallet/transactions")
                               .requestAttr("userId", userId)
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].id").value(history1.id()))
               .andExpect(jsonPath("$.data[0].amount").value(history1.amount()))
               .andExpect(jsonPath("$.data[0].balanceAfter").value(history1.balanceAfter()))
               .andExpect(jsonPath("$.data[0].type").value(history1.type().name()))
               .andExpect(jsonPath("$.data[0].updatedAt").value(history1.updatedAt().toString()));

    }
}