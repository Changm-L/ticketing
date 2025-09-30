package kr.hhplus.be.server.user.domain.payment.presentation.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.core.utils.ControllerTest;
import kr.hhplus.be.server.user.domain.payment.core.dto.FindAllPaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.dto.PaymentResponse;
import kr.hhplus.be.server.user.domain.payment.core.port.in.PaymentUseCase;
import kr.hhplus.be.server.user.domain.payment.presentation.dto.PaymentRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = PaymentController.class)
class PaymentControllerTest {

    @MockitoBean
    private PaymentUseCase paymentUseCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void payment() throws Exception {
        //given
        long userId = 1L;
        long reservationId = 1L;
        BigDecimal price = BigDecimal.valueOf(10000L);
        PaymentRequest request = new PaymentRequest(reservationId);
        PaymentResponse expected = new PaymentResponse(reservationId, price);

        when(paymentUseCase.payment(userId, request.reservationId())).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       post("/v1/payment")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content(objectMapper.writeValueAsString(request))
                               .requestAttr("userId", userId)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.reservationId").value(reservationId))
               .andExpect(jsonPath("$.data.price").value(price.toString()));
    }

    @Test
    void findAll() throws Exception {
        //given
        long userId = 1L;
        FindAllPaymentResponse response1 = new FindAllPaymentResponse(
                1L,
                BigDecimal.valueOf(10000L),
                "title",
                LocalDate.now()
        );
        List<FindAllPaymentResponse> expected = List.of(response1);
        when(paymentUseCase.findAll(userId)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/payment")
                               .contentType(MediaType.APPLICATION_JSON)
                               .requestAttr("userId", userId)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].id").value(response1.id()))
               .andExpect(jsonPath("$.data[0].title").value(response1.title()))
               .andExpect(jsonPath("$.data[0].price").value(response1.price()))
               .andExpect(jsonPath("$.data[0].startsAt").value(response1.startsAt().toString()));

    }
}