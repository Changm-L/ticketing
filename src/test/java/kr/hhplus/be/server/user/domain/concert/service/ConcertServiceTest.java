package kr.hhplus.be.server.user.domain.concert.service;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
            ConcertDetailResponse expected = new ConcertDetailResponse(
                    concertId,
                    "title",
                    "address",
                    LocalDate.now(),
                    LocalDate.now().plusDays(1),
                    50
            );
            when(concertRepository.getConcertDetailById(concertId)).thenReturn(expected);

            //when
            ConcertDetailResponse result = concertService.getConcertById(concertId);

            //then
            verify(concertRepository).getConcertDetailById(concertId);
            assertEquals(expected, result);
        }

        @Test
        void 유효하지_않은_콘서트_아이디로_조회시_NotFoundConcertException이_발생한다() {
            //given
            long concertId = 2L;
            when(concertRepository.getConcertDetailById(concertId)).thenReturn(null);

            //when & then
            assertThrows(
                    ConcertNotFoundException.class,
                    () -> concertService.getConcertById(concertId)
            );

        }
    }

}