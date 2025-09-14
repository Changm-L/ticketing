package kr.hhplus.be.server.user.domain.concert.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;

public interface SeatInventoryReadRepository extends JpaRepository<SeatInventory, Long> {

    @Query("""
                SELECT si FROM SeatInventory si
                    JOIN FETCH si.seatMaster sm
                WHERE sm.concert = :concert
                AND si.seatStatus = 'AVAILABLE'
                ORDER BY sm.rowNo ASC, sm.seatNo ASC
            """)
    List<SeatInventory> findAllSeatInventoryByConcert(Concert concert);

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse(
                            c.id,
                            c.startsAt,
                            si.id,
                            sm.seatNo,
                            si.seatStatus
                )
                FROM SeatInventory si
                LEFT JOIN FETCH SeatMaster sm on si.seatMaster.id = sm.id
                LEFT JOIN FETCH Concert c on sm.concert.id = :concertId
            """)
    List<FindAllSeatsResponse> findAllSeatInventoryListWith(long concertId);
}