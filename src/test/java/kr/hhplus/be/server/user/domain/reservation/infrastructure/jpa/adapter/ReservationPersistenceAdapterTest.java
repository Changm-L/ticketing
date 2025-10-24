package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.adapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import jakarta.persistence.EntityManager;
import org.springframework.test.util.ReflectionTestUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.hhplus.be.server.fixture.auth.AuthFixture;
import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.core.constant.SeatStatus;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper.ReservationMapper;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository.ReservationJpaRepository;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationPersistenceAdapterTest {

    @Mock
    private EntityManager entityManager;

    @Mock
    private ReservationJpaRepository jpaRepository;

    @Mock
    private ReservationMapper reservationMapper;

    @Mock
    private SeatInventoryReadRepository seatInventoryReadRepository;

    @InjectMocks
    private ReservationPersistenceAdapter reservationPersistenceAdapter;

    @Test
    void findAllByUserId() {
        //given
        long userId = 1L;
        FindAllReservationResponse findAllReservationResponse = new FindAllReservationResponse(
                1L, 1L, "title", SeatStatus.RESERVED,
                LocalDate.now()
        );
        List<FindAllReservationResponse> expected = List.of(findAllReservationResponse);
        when(jpaRepository.findAllByUserId(userId)).thenReturn(expected);

        //when
        List<FindAllReservationResponse> result = reservationPersistenceAdapter.findAllByUserId(userId);

        //then
        assertEquals(expected.size(), result.size());
        assertEquals(expected.get(0), result.get(0));
        verify(jpaRepository).findAllByUserId(userId);
    }

    @Test
    void place() {
        long reservationId = 1L;
        long userId = 1L;
        long concertId = 1L;
        long seatInventoryId = 1L;
        UserJpaEntity userJpaEntity = AuthFixture.user();
        BigDecimal price = BigDecimal.valueOf(10000L);
        ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
        SeatMasterJpaEntity seatMasterJpaEntity = ConcertFixture.seatMasterList().get(0);
        SeatInventoryJpaEntity seatInventoryJpaEntity = SeatInventoryJpaEntity.of(seatMasterJpaEntity, price);
        ReservationJpaEntity jpaEntity = ReservationJpaEntity.createWith(
                concertJpaEntity,
                seatInventoryJpaEntity,
                userJpaEntity,
                price
        );
        ReflectionTestUtils.setField(jpaEntity, "id", reservationId);
        Reservation reservation = Reservation.createWith(
                reservationId,
                userJpaEntity,
                concertJpaEntity,
                seatInventoryJpaEntity,
                LocalDateTime.now(),
                LocalDateTime.now(),
                price
        );

        when(entityManager.getReference(ConcertJpaEntity.class, concertId)).thenReturn(concertJpaEntity);
        when(entityManager.getReference(UserJpaEntity.class, userId)).thenReturn(userJpaEntity);
        when(seatInventoryReadRepository.getById(seatInventoryId)).thenReturn(seatInventoryJpaEntity);
        when(seatInventoryReadRepository.getPriceBy(concertId, seatInventoryId)).thenReturn(Optional.of(price));
        when(reservationMapper.toJpaEntity(
                concertJpaEntity,
                userJpaEntity,
                seatInventoryJpaEntity,
                price
        )).thenReturn(jpaEntity);
        when(jpaRepository.save(any(ReservationJpaEntity.class))).thenReturn(jpaEntity);

        when(reservationMapper.toDomain(any(ReservationJpaEntity.class), any(BigDecimal.class)))
                .thenReturn(reservation);

        //when
        Reservation result = reservationPersistenceAdapter.place(userId, concertId, seatInventoryId);

        //then
        verify(jpaRepository).save(jpaEntity);
        assertEquals(jpaEntity.getSeatInventoryJpaEntity().getId(), result.getSeatInventoryId());
    }

    @Test
    void getById() {
        //given
        long reservationId = 1L;
        UserJpaEntity userJpaEntity = AuthFixture.user();
        ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
        SeatMasterJpaEntity seatMasterJpaEntity = ConcertFixture.seatMasterList().get(0);
        SeatInventoryJpaEntity seatInventoryJpaEntity = seatMasterJpaEntity.getSeatInventoryJpaEntity();
        BigDecimal price = BigDecimal.valueOf(10000L);

        ReservationJpaEntity jpaEntity = ReservationJpaEntity.createWith(
                concertJpaEntity,
                seatInventoryJpaEntity,
                userJpaEntity,
                price
        );
        ReflectionTestUtils.setField(jpaEntity, "id", reservationId);
        Reservation expected = Reservation.createWith(
                jpaEntity.getId(),
                userJpaEntity,
                concertJpaEntity,
                seatInventoryJpaEntity,
                LocalDateTime.now(),
                LocalDateTime.now(),
                price
        );
        when(jpaRepository.getById(reservationId)).thenReturn(jpaEntity);
        when(reservationMapper.toDomain(jpaEntity, price)).thenReturn(expected);

        //when
        Reservation result = reservationPersistenceAdapter.getById(reservationId);

        //then
        verify(jpaRepository).getById(reservationId);
        assertEquals(jpaEntity.getSeatInventoryJpaEntity().getId(), result.getSeatInventoryId());
        assertEquals(jpaEntity.getPrice(), result.getPrice());
        assertEquals(jpaEntity.getId(), result.getId());
        verify(reservationMapper).toDomain(jpaEntity, price);
    }
}