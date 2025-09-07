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
import kr.hhplus.be.server.fixture.Concert.AdminConcertFixture;
import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private AdminConcertService adminConcertService;

    @Test
    void findAllConcerts() {
        //given
        CreateConcertRequest request = new CreateConcertRequest(
                "title",
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
        Concert concert = new Concert(request);
        when(concertRepository.findAll()).thenReturn(List.of(concert));
        List<AdminConcertListResponse> expected = List.of(AdminConcertListResponse.of(concert));

        //when
        List<AdminConcertListResponse> result = adminConcertService.findAllConcerts();

        //then
        assertEquals(expected, result);
        verify(concertRepository).findAll();
    }

    @Nested
    class findConcertById {

        @Test
        void 존재하는_아이디로_조회_시_AdminConcertDetailResponse를_반환한다() {
            //given
            long id = 1L;
            Concert concert = AdminConcertFixture.concert();
            when(concertRepository.getById(id)).thenReturn(concert);
            AdminConcertDetailResponse expected = AdminConcertDetailResponse.of(concert);

            //when
            AdminConcertDetailResponse result = adminConcertService.getById(id);

            //then
            assertEquals(expected, result);
            verify(concertRepository).getById(id);
        }

        @Test
        void 존재하지_않는_아이디로_조회_시_ConcertNotFoundException이_발생한다() {
            try (MockedStatic<AdminConcertListResponse> mockedStatic = mockStatic(AdminConcertListResponse.class)) {
                //given
                long id = 1L;
                Concert concert = AdminConcertFixture.concert();
                when(concertRepository.getById(id)).thenThrow(ConcertNotFoundException.class);

                //when
                assertThrows(
                        ConcertNotFoundException.class,
                        () -> adminConcertService.getById(id)
                );

                //then
                mockedStatic.verify(() -> AdminConcertListResponse.of(concert), never());
            }
        }

    }

    @Test
    void create() {
        //given
        long id = 1L;
        CreateConcertRequest request = AdminConcertFixture.createConcertRequest();
        when(concertRepository.save(any(Concert.class)))
                .thenAnswer(invocation -> {
                                Concert concert = invocation.getArgument(0);
                                ReflectionTestUtils.setField(concert, "id", id);
                                return concert;
                            }
                );

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
        Concert concert = spy(AdminConcertFixture.concert());
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