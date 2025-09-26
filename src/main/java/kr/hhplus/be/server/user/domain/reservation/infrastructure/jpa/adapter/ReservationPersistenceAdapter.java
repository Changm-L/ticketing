package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.adapter;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.core.port.out.ReservationPort;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository.ReservationJpaRepository;

@Component
public class ReservationPersistenceAdapter implements ReservationPort {

    private final ReservationJpaRepository reservationJpaRepository;

    public ReservationPersistenceAdapter(ReservationJpaRepository reservationJpaRepository) {
        this.reservationJpaRepository = reservationJpaRepository;
    }

    @Override
    public Optional<Reservation> findById(long id) {
        ReservationJpaEntity jpaEntity = reservationJpaRepository.findById(id);
        return mapper.toDomain(jpaEntity);
    }

    @Override
    public List<Reservation> findAllByUserId(long userId) {
        return List.of();
    }

    @Override
    public Reservation save(Reservation reservation) {
        Entity entity = mapper.toEntity(reservation);
        reservationJpaRepository.save(entity);
        return entity;
    }

    @Override
    public Reservation getById(long id) {
        return null;
    }

    @Override
    public Reservation toDomain(ReservationJpaEntity reservationJpaEntity) {
        return mapper.toDomain(reservationJpaEntity);
    }

    @Override
    public ReservationJpaEntity toJpaEntity(Reservation reservation) {
        return null;
    }
}
