package kr.hhplus.be.server.user.domain.seat.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;
import kr.hhplus.be.server.user.domain.concert.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

    @Mock
    private SeatInventoryReadRepository readRepository;

    @InjectMocks
    private SeatService seatService;

    @Test
    void findAllAvailableSeats() {
        //given
        long concertId = 1L;
        Concert concert = ConcertFixture.concert();
        SeatMaster seatMaster = SeatMaster.of(
                concert,
                BigDecimal.valueOf(10000L),
                1,
                1
        );

        ReflectionTestUtils.setField(concert, "id", concertId);

        List<FindAllSeatsResponse> expected = List.of(
                FindAllSeatsResponse.of(
                        concert,
                        seatMaster
                )
        );
        when(readRepository.findAllSeatInventoryListWith(concertId)).thenReturn(expected);

        //when
        List<FindAllSeatsResponse> result = seatService.findAllAvailableSeats(concertId);

        //then
        assertEquals(expected, result);
        verify(readRepository).findAllSeatInventoryListWith(concertId);
    }
}