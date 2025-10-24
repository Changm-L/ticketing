package kr.hhplus.be.server.user.domain.concertJpaEntity.infrastructure.jpa.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.user.domain.concertJpaEntity.infrastructure.jpa.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;
import kr.hhplus.be.server.user.domain.seat.exception.SeatNotFoundException;

public interface SeatInventoryReadRepository extends JpaRepository<SeatInventory, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse(
                            c.id,
                            c.startsAt,
                            si.id,
                            sm.seatNo,
                            si.seatStatus
                )
                FROM SeatInventoryJpaEntity si
                INNER JOIN FETCH SeatMaster sm on si.seatMasterJpaEntity.id = sm.id
                INNER JOIN FETCH Concert c on sm.concertJpaEntity.id = :concertId
            """)
    List<FindAllSeatsResponse> findAllSeatInventoryListWith(long concertId);

    @Query("""
                SELECT si
                FROM SeatInventoryJpaEntity si
                INNER JOIN FETCH SeatMaster sm on si.seatMasterJpaEntity.id = sm.id
                INNER JOIN FETCH Concert c on c.id = :concertId
                AND si.id = :seatInventoryId
                AND si.seatStatus = 'AVAILABLE'
            """)
    Optional<SeatInventory> findByConcertIdAndSeatInventoryId(long concertId, long seatInventoryId);

    @Query("""
                SELECT si.price
                FROM SeatInventoryJpaEntity si
                JOIN SeatMaster sm on si.seatMasterJpaEntity.id = sm.id
                JOIN Concert c on c.id = sm.concertJpaEntity.id
                WHERE si.id = :seatInventoryId
                AND c.id = :concertId
            """)
    Optional<BigDecimal> getPriceBy(long concertId, long seatInventoryId);

    default SeatInventory getById(long id) {
        return findById(id).orElseThrow(SeatNotFoundException::new);
    }

    Optional<SeatInventory> findById(long id);
}