package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.adapter;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatInventoryJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper.ReservationMapper;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository.ReservationJpaRepository;
import kr.hhplus.be.server.user.domain.seat.exception.SeatNotFoundException;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Component
@RequiredArgsConstructor
public class ReservationPersistenceAdapter implements ReservationPort {

    private final EntityManager               entityManager;
    private final ReservationJpaRepository    reservationJpaRepository;
    private final ReservationMapper           mapper;
    private final SeatInventoryReadRepository seatInventoryReadRepository;

    @Override
    public List<FindAllReservationResponse> findAllByUserId(long userId) {
        return reservationJpaRepository.findAllByUserId(userId);
    }

    @Override
    public Reservation place(
            long userId,
            long concertId,
            long seatInventoryId
    ) {
        ConcertJpaEntity concertJpaEntityRef = entityManager.getReference(ConcertJpaEntity.class, concertId);
        UserJpaEntity userJpaEntityRef = entityManager.getReference(UserJpaEntity.class, userId);
        SeatInventoryJpaEntity seatInventoryJpaEntity = seatInventoryReadRepository.getById(seatInventoryId);
        BigDecimal price = seatInventoryReadRepository.getPriceBy(concertId, seatInventoryId).orElseThrow(
                SeatNotFoundException::new);

        ReservationJpaEntity entity = mapper.toJpaEntity(
                concertJpaEntityRef,
                userJpaEntityRef,
                seatInventoryJpaEntity,
                price
        );

        ReservationJpaEntity savedEntity = reservationJpaRepository.save(entity);

        return mapper.toDomain(savedEntity, price);
    }

    @Override
    public Reservation getById(long id) {
        ReservationJpaEntity entity = reservationJpaRepository.getById(id);

        return mapper.toDomain(entity, entity.getPrice());
    }
}
