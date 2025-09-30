package kr.hhplus.be.server.user.domain.reservation.presentation.controller;

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
import kr.hhplus.be.server.user.domain.concert.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.dto.PlaceReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.port.in.ReservationUseCase;
import kr.hhplus.be.server.user.domain.reservation.presentation.dto.ReservationRequest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = ReservationController.class)
class ReservationControllerTest {

    @MockitoBean
    private ReservationUseCase reservationUseCase;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllReservations() throws Exception {
        //given
        long userId = 1L;
        FindAllReservationResponse result1 = new FindAllReservationResponse(
                1L,
                1L,
                "title",
                SeatStatus.RESERVED,
                LocalDate.now()
        );
        List<FindAllReservationResponse> expected = List.of(result1);
        when(reservationUseCase.findAll(userId)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/reservations")
                               .contentType(MediaType.APPLICATION_JSON)
                               .requestAttr("userId", userId)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].id").value(result1.id()))
               .andExpect(jsonPath("$.data[0].concertId").value(result1.concertId()))
               .andExpect(jsonPath("$.data[0].title").value(result1.title()))
               .andExpect(jsonPath("$.data[0].status").value(result1.status().name()))
               .andExpect(jsonPath("$.data[0].startsAt").value(result1.startsAt().toString()));

    }

    @Test
    void reservation() throws Exception {
        //given
        long userId = 1L;
        ReservationRequest request = new ReservationRequest(1L, 1L);
        PlaceReservationResponse result = new PlaceReservationResponse(1L, 1L, BigDecimal.valueOf(10000L));

        when(reservationUseCase.placeReservation(userId, request.concertId(), request.seatInventoryId())).thenReturn(
                result);

        //when & then
        mockMvc.perform(
                       post("/v1/reservations")
                               .requestAttr("userId", userId)
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.concertId").value(result.concertId()))
               .andExpect(jsonPath("$.data.seatInventoryId").value(result.seatInventoryId()))
               .andExpect(jsonPath("$.data.price").value(result.price()));
    }
}