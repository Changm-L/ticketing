package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse;
import kr.hhplus.be.server.user.domain.reservation.core.exception.ReservationNotFoundException;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;

@Repository
public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.reservation.core.dto.FindAllReservationResponse(
                    r.id,
                    r.concert.id,
                    r.concert.title,
                    r.seatInventory.seatStatus,
                    r.concert.startsAt
                )
                FROM ReservationJpaEntity r
                INNER JOIN Concert c on r.concert.id = c.id
                WHERE r.user.id=:userId
                ORDER BY r.id DESC
            """)
    List<FindAllReservationResponse> findAllByUserId(long userId);

    default ReservationJpaEntity getById(long id) {
        return findById(id).orElseThrow(ReservationNotFoundException::new);
    }
}


