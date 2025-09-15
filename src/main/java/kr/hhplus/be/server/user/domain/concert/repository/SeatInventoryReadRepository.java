package kr.hhplus.be.server.user.domain.concert.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;

public interface SeatInventoryReadRepository extends JpaRepository<SeatInventory, Long> {

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse(
                            c.id,
                            c.startsAt,
                            si.id,
                            sm.seatNo,
                            si.seatStatus
                )
                FROM SeatInventory si
                INNER JOIN FETCH SeatMaster sm on si.seatMaster.id = sm.id
                INNER JOIN FETCH Concert c on sm.concert.id = :concertId
            """)
    List<FindAllSeatsResponse> findAllSeatInventoryListWith(long concertId);
}