package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {
}


