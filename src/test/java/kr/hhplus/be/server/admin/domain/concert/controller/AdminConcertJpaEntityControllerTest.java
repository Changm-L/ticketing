package kr.hhplus.be.server.admin.domain.concert.controller;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.admin.domain.concert.service.AdminConcertService;
import kr.hhplus.be.server.core.utils.ControllerTest;
import kr.hhplus.be.server.user.domain.concert.core.constant.ConcertStatus;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ControllerTest(controllers = AdminConcertController.class)
class AdminConcertJpaEntityControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AdminConcertService adminConcertService;
    @Autowired
    private ObjectMapper        objectMapper;

    @Test
    void findAll() throws Exception {
        //given
        AdminConcertListResponse listResponse1 = new AdminConcertListResponse(
                1L,
                "title",
                "address",
                50,
                LocalDate.now(),
                ConcertStatus.ACTIVE
        );
        AdminConcertListResponse listResponse2 = new AdminConcertListResponse(
                1L,
                "title",
                "address",
                50,
                LocalDate.now(),
                ConcertStatus.ACTIVE
        );
        List<AdminConcertListResponse> expected = List.of(listResponse1, listResponse2);
        when(adminConcertService.findAllConcerts()).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/admin/concerts")
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.length()").value(expected.size()))
               .andExpect(jsonPath("$.data[0].concertId").value(listResponse1.concertId()))
               .andExpect(jsonPath("$.data[0].title").value(listResponse1.title()))
               .andExpect(jsonPath("$.data[0].address").value(listResponse1.address()))
               .andExpect(jsonPath("$.data[0].availableSeatCount").value(listResponse1.availableSeatCount()))
               .andExpect(jsonPath("$.data[0].startsAt").value(listResponse1.startsAt().toString()))
               .andExpect(jsonPath("$.data[0].status").value(listResponse1.status().name()))
               .andExpect(jsonPath("$.data[1].concertId").value(listResponse2.concertId()))
               .andExpect(jsonPath("$.data[1].title").value(listResponse2.title()))
               .andExpect(jsonPath("$.data[1].address").value(listResponse2.address()))
               .andExpect(jsonPath("$.data[1].availableSeatCount").value(listResponse2.availableSeatCount()))
               .andExpect(jsonPath("$.data[1].startsAt").value(listResponse2.startsAt().toString()))
               .andExpect(jsonPath("$.data[1].status").value(listResponse2.status().name()));
    }

    @Test
    void getById() throws Exception {
        //given
        long id = 1L;
        AdminConcertDetailResponse expected = new AdminConcertDetailResponse(
                1L,
                "title",
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                50,
                ConcertStatus.ACTIVE
        );
        when(adminConcertService.getById(id)).thenReturn(expected);

        //when & then
        mockMvc.perform(
                       get("/v1/admin/concerts/{id}", id)
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.concertId").value(expected.concertId()))
               .andExpect(jsonPath("$.data.title").value(expected.title()))
               .andExpect(jsonPath("$.data.address").value(expected.address()))
               .andExpect(jsonPath("$.data.startsAt").value(expected.startsAt().toString()))
               .andExpect(jsonPath("$.data.endsAt").value(expected.endsAt().toString()))
               .andExpect(jsonPath("$.data.availableSeatCount").value(expected.availableSeatCount()))
               .andExpect(jsonPath("$.data.status").value(expected.status().name()));
    }

    @Test
    void create() throws Exception {
        //given
        CreateConcertRequest request = new CreateConcertRequest(
                "title",
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
        when(adminConcertService.create(request)).thenReturn(1L);

        //when & then
        mockMvc.perform(
                       post("/v1/admin/concerts")
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.data.id").value(1L));
    }

    @Test
    void update() throws Exception {
        long id = 1L;
        UpdateConcertRequest request = new UpdateConcertRequest(
                "title",
                ConcertStatus.ACTIVE,
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
        when(adminConcertService.update(id, request)).thenReturn(1L);

        mockMvc.perform(
                       patch("/v1/admin/concerts/{id}", id)
                               .content(objectMapper.writeValueAsString(request))
                               .contentType(MediaType.APPLICATION_JSON)
               )
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.data.id").value(id));
    }
}