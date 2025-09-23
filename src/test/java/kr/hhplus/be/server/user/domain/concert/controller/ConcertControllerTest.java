package kr.hhplus.be.server.user.domain.concert.controller;

import java.time.LocalDate;
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
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.service.ConcertService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = ConcertController.class)
class ConcertControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConcertService concertService;

    @Test
    void findAllConcert() throws Exception {
        //given
        ConcertListResponse concertListResponse = new ConcertListResponse(
                1L,
                "title1",
                "address1",
                50,
                LocalDate.now()
        );
        List<ConcertListResponse> expected = List.of(concertListResponse);
        when(concertService.findAllConcerts()).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/concerts")
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data[0].concertId").value(concertListResponse.concertId()))
               .andExpect(jsonPath("$.data[0].title").value(concertListResponse.title()))
               .andExpect(jsonPath("$.data[0].address").value(concertListResponse.address()))
               .andExpect(jsonPath("$.data[0].availableSeatCount").value(concertListResponse.availableSeatCount()))
               .andExpect(jsonPath("$.data[0].startsAt").value(concertListResponse.startsAt().toString()));

    }

    @Test
    void getConcertById() throws Exception {
        //given
        long concertId = 1L;
        Concert concert = ConcertFixture.concert();
        ReflectionTestUtils.setField(concert, "id", concertId);
        ConcertDetailResponse concertDetailResponse = ConcertDetailResponse.of(concert);
        when(concertService.getConcertById(concertId)).thenReturn(concertDetailResponse);

        mockMvc.perform(
                       get("/v1/concerts/{concertId}", concertId)
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.concertId").value(concertId))
               .andExpect(jsonPath("$.data.title").value(concertDetailResponse.title()))
               .andExpect(jsonPath("$.data.address").value(concertDetailResponse.address()))
               .andExpect(jsonPath("$.data.availableSeatCount").value(concertDetailResponse.availableSeatCount()))
               .andExpect(jsonPath("$.data.startsAt").value(concertDetailResponse.startsAt().toString()))
               .andExpect(jsonPath("$.data.endsAt").value(concertDetailResponse.endsAt().toString()));

    }
}