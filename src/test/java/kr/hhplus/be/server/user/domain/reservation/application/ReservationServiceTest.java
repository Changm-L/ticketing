package kr.hhplus.be.server.user.domain.reservation.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;
import kr.hhplus.be.server.user.domain.concert.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.dto.PlaceReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.exception.AlreadyReservedException;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.seat.exception.SeatNotFoundException;
import kr.hhplus.be.server.user.domain.user.entity.User;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationPort reservationPort;

    @Mock
    private SeatInventoryReadRepository seatInventoryReadRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void findAll() {
        //given
        long userId = 1L;
        FindAllReservationResponse result1 = new FindAllReservationResponse(
                1L,
                1L,
                "title",
                SeatStatus.RESERVED,
                LocalDate.now()
        );
        FindAllReservationResponse result2 = new FindAllReservationResponse(
                2L,
                2L,
                "title2",
                SeatStatus.RESERVED,
                LocalDate.now()
        );

        List<FindAllReservationResponse> expected = List.of(result1, result2);

        when(reservationPort.findAllByUserId(userId)).thenReturn(expected);

        //when
        List<FindAllReservationResponse> result = reservationService.findAll(userId);

        //then
        verify(reservationPort).findAllByUserId(userId);
        assertEquals(expected, result);
        assertEquals(expected.size(), result.size());
    }

    @Nested
    class placeReservation {
        @Test
        void 예약_가능한_좌석이_존재할_시_좌석상태를_변경하고_예약을_생성한다() {
            //given
            User user = AuthFixture.user();
            long userId = 1L;
            long concertId = 1L;
            long seatInventoryId = 1L;
            BigDecimal price = BigDecimal.valueOf(10000L);
            SeatMaster seatMaster = ConcertFixture.seatMasterList().get(0);
            SeatInventory seatInventory = SeatInventory.of(seatMaster, price);
            when(seatInventoryReadRepository.findByConcertIdAndSeatInventoryId(concertId, seatInventoryId)).thenReturn(
                    Optional.of(seatInventory));
            Reservation reservation = Reservation.createWith(
                    1L,
                    user,
                    ConcertFixture.concert(),
                    seatInventory,
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    price
            );
            PlaceReservationResponse expected = PlaceReservationResponse.of(reservation);
            when(reservationPort.place(userId, concertId, seatInventoryId)).thenReturn(reservation);

            //when
            PlaceReservationResponse result = reservationService.placeReservation(userId, concertId, seatInventoryId);

            //then
            verify(seatInventoryReadRepository).findByConcertIdAndSeatInventoryId(concertId, seatInventoryId);
            verify(reservationPort).place(userId, concertId, seatInventoryId);
            assertEquals(expected.concertId(), result.concertId());
            assertEquals(expected.seatInventoryId(), result.seatInventoryId());
            assertEquals(expected.price(), result.price());
        }

        @Test
        void 예약가능한_좌석이_없을_시_SeatNotFoundException을_던진다() {
            //given
            long userId = 1L;
            long concertId = 1L;
            long seatInventoryId = 1L;

            when(seatInventoryReadRepository.findByConcertIdAndSeatInventoryId(concertId, seatInventoryId)).thenReturn(
                    Optional.empty()
            );
            //when & then
            assertThrows(
                    SeatNotFoundException.class,
                    () -> reservationService.placeReservation(userId, concertId, seatInventoryId)
            );
        }

        @Test
        void 조회결과_좌석이_예약된_좌석이라면_AlreadyReservedException을_던진다() {
            //given
            long userId = 1L;
            long concertId = 1L;
            long seatInventoryId = 1L;

            SeatInventory seatInventory = SeatInventory.of(null, BigDecimal.valueOf(10000L));
            ReflectionTestUtils.setField(seatInventory, "seatStatus", SeatStatus.HELD);
            seatInventory.reserve();
            when(seatInventoryReadRepository.findByConcertIdAndSeatInventoryId(concertId, seatInventoryId)).thenReturn(
                    Optional.of(seatInventory)
            );

            //when & then
            assertThrows(
                    AlreadyReservedException.class,
                    () -> reservationService.placeReservation(userId, concertId, seatInventoryId)
            );
        }

    }
}