package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.adapter;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper.ReservationMapper;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository.ReservationJpaRepository;
import kr.hhplus.be.server.user.domain.seat.exception.SeatNotFoundException;
import kr.hhplus.be.server.user.domain.user.entity.User;

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
        Concert concertRef = entityManager.getReference(Concert.class, concertId);
        User userRef = entityManager.getReference(User.class, userId);
        SeatInventory seatInventory = seatInventoryReadRepository.getById(seatInventoryId);
        BigDecimal price = seatInventoryReadRepository.getPriceBy(concertId, seatInventoryId).orElseThrow(
                SeatNotFoundException::new);

        ReservationJpaEntity entity = mapper.toJpaEntity(
                concertRef,
                userRef,
                seatInventory,
                price
        );

        seatInventory.held();
        reservationJpaRepository.save(entity);

        return Reservation.createWith(
                entity.getId(),
                entity.getUser(),
                entity.getConcert(),
                entity.getSeatInventory(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                price
        );
    }

    @Override
    public Reservation getById(long id) {
        ReservationJpaEntity entity = reservationJpaRepository.getById(id);

        return Reservation.createWith(
                entity.getId(),
                entity.getUser(),
                entity.getConcert(),
                entity.getSeatInventory(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getSeatInventory().getPrice()
        );
    }
}
