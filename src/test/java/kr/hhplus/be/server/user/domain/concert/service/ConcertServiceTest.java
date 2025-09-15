package kr.hhplus.be.server.user.domain.concert.service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.Concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConcertServiceTest {

    @Mock
    private ConcertRepository concertRepository;

    @InjectMocks
    private ConcertService concertService;

    @Test
    void findAllConcerts() {
        //given
        List<ConcertListResponse> expected = List.of(
                new ConcertListResponse(
                        1L,
                        "title",
                        "address",
                        50,
                        LocalDate.now()
                )
        );
        when(concertRepository.findAllConcertWithAvailableSeatsCount()).thenReturn(expected);

        //when
        List<ConcertListResponse> result = concertService.findAllConcerts();

        //then
        verify(concertRepository).findAllConcertWithAvailableSeatsCount();
        assertEquals(expected, result);
    }

    @Nested
    class getConcertById {

        @Test
        void 유효한_콘서트_아이디로_조회시_ConcertDetailResponse를_반환한다() {
            //given
            long concertId = 1L;
            Concert concert = ConcertFixture.concert();
            ConcertDetailResponse expected = ConcertDetailResponse.of(concert);
            when(concertRepository.getById(concertId)).thenReturn(concert);

            //when
            ConcertDetailResponse result = concertService.getConcertById(concertId);

            //then
            verify(concertRepository).getById(concertId);
            assertEquals(expected, result);
        }

        @Test
        void 유효하지_않은_콘서트_아이디로_조회시_NotFoundConcertException이_발생한다() {
            try (MockedStatic<ConcertDetailResponse> mockedStatic = Mockito.mockStatic(ConcertDetailResponse.class)) {
                //given
                long concertId = 2L;
                Concert concert = ConcertFixture.concert();
                when(concertRepository.getById(concertId)).thenThrow(ConcertNotFoundException.class);

                //when
                assertThrows(
                        ConcertNotFoundException.class,
                        () -> concertService.getConcertById(concertId)
                );

                //then
                mockedStatic.verify(() -> ConcertDetailResponse.of(concert), never());
            }
        }
    }

}