package kr.hhplus.be.server.user.domain.reservation.core.port.out;

import java.util.List;
import java.util.Optional;

import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;

public interface ReservationPort {
    Optional<Reservation> findById(long id);

    Reservation getById(long id);

    Reservation save(Reservation reservation);

    List<Reservation> findAllByUserId(long userId);

    Reservation toDomain(ReservationJpaEntity reservationJpaEntity);

    ReservationJpaEntity toJpaEntity(Reservation reservation);
}
