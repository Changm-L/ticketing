package kr.hhplus.be.server.admin.domain.concert.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.SeatGenerator;
import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @Mock
    private SeatGenerator seatGenerator;

    @InjectMocks
    private AdminConcertService adminConcertService;

    @Test
    void findAllConcerts() {
        //given
        AdminConcertListResponse adminConcertListResponse = new AdminConcertListResponse(
                1L,
                "title",
                "address",
                50,
                LocalDate.now(),
                ConcertStatus.ACTIVE
        );
        List<AdminConcertListResponse> expected = List.of(adminConcertListResponse);
        when(concertRepository.findAllAdminConcerts()).thenReturn(expected);

        //when
        List<AdminConcertListResponse> result = adminConcertService.findAllConcerts();

        //then
        assertEquals(expected, result);
        verify(concertRepository).findAllAdminConcerts();
    }

    @Nested
    class findConcertById {

        @Test
        void 존재하는_아이디로_조회_시_AdminConcertDetailResponse를_반환한다() {
            //given
            long id = 1L;
            AdminConcertDetailResponse expected = new AdminConcertDetailResponse(
                    id,
                    "title",
                    "address",
                    LocalDate.now(),
                    LocalDate.now().minusDays(5),
                    50,
                    ConcertStatus.ACTIVE
            );
            when(concertRepository.getAdminConcertDetailById(id)).thenReturn(expected);

            //when
            AdminConcertDetailResponse result = adminConcertService.getById(id);

            //then
            assertEquals(expected, result);
            verify(concertRepository).getAdminConcertDetailById(id);
        }

        @Test
        void 존재하지_않는_아이디로_조회_시_ConcertNotFoundException이_발생한다() {
            //given
            long id = 1L;
            when(concertRepository.getAdminConcertDetailById(id)).thenReturn(null);

            //when & then
            assertThrows(
                    ConcertNotFoundException.class,
                    () -> adminConcertService.getById(id)
            );

        }

    }

    @Test
    void create() {
        //given
        long id = 1L;
        CreateConcertRequest request = ConcertFixture.createConcertRequest();
        List<SeatMaster> seatMasterList = ConcertFixture.seatMasterList();

        when(seatGenerator.generateSeatMasterAndInventory(any(Concert.class), eq(50))).thenReturn(seatMasterList);
        when(concertRepository.save(any(Concert.class)))
                .thenAnswer(invocation -> {
                    Concert c = invocation.getArgument(0);
                    ReflectionTestUtils.setField(c, "id", id);
                    return c;
                });

        //when
        long result = adminConcertService.create(request);

        //then
        assertEquals(id, result);
        verify(concertRepository).save(any(Concert.class));
    }

    @Test
    void update() {
        //given
        long id = 1L;
        UpdateConcertRequest request = new UpdateConcertRequest(
                "updatedTitle",
                ConcertStatus.INACTIVE,
                "updatedAddress",
                LocalDate.now(),
                LocalDate.now().plusDays(2)
        );
        Concert concert = spy(ConcertFixture.concert());
        when(concertRepository.getById(id)).thenReturn(concert);
        when(concertRepository.save(any(Concert.class)))
                .thenAnswer(invocationOnMock -> {
                    Concert responsedConcert = invocationOnMock.getArgument(0);
                    ReflectionTestUtils.setField(responsedConcert, "id", id);

                    return responsedConcert;
                });

        //when
        long result = adminConcertService.update(id, request);

        //then
        assertEquals(id, result);
        verify(concertRepository).save(any(Concert.class));
        verify(concert).update(request);
    }
}