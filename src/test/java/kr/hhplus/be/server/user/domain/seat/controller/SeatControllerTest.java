package kr.hhplus.be.server.user.domain.seat.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.core.utils.ControllerTest;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;
import kr.hhplus.be.server.user.domain.seat.service.SeatService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = SeatController.class)
class SeatControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SeatService seatService;

    @Test
    void findAllAvailableSeats() throws Exception {
        //given
        long concertId = 1L;
        ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
        ReflectionTestUtils.setField(concertJpaEntity, "id", concertId);
        SeatMasterJpaEntity seatMasterJpaEntity = ConcertFixture.seatMasterList().get(0);
        FindAllSeatsResponse seats1 = FindAllSeatsResponse.of(concertJpaEntity, seatMasterJpaEntity);
        List<FindAllSeatsResponse> expected = List.of(seats1);
        when(seatService.findAllAvailableSeats(concertId)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/concerts/{concertId}/seats", concertId)
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].concertId").value(concertId))
               .andExpect(jsonPath("$.data[0].seatId").value(seats1.seatId()))
               .andExpect(jsonPath("$.data[0].seatNo").value(seats1.seatNo()))
               .andExpect(jsonPath("$.data[0].status").value(seats1.status().name()))
               .andExpect(jsonPath("$.data[0].concertDate").value(seats1.concertDate().toString()));
    }
}