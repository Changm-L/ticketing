package kr.hhplus.be.server.user.domain.concert.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    default Concert getById(long id) {
        return findById(id).orElseThrow(ConcertNotFoundException::new);
    }

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.concert.dto.response.ConcertListResponse(
                   c.id,
                    c.title,
                    c.address,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0),
                    c.startsAt
                )
                FROM Concert c
                LEFT JOIN SeatMaster sm on sm.concert.id = c.id
                LEFT JOIN SeatInventory si on sm.id = si.seatMaster.id
                GROUP BY c.id, c.title, c.address, c.startsAt
            """)
    List<ConcertListResponse> findAllConcertWithAvailableSeatsCount();

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.concert.dto.response.ConcertDetailResponse(
                    c.id,
                    c.title,
                    c.address,
                    c.startsAt,
                    c.endsAt,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0 )
                )
                FROM Concert c
                LEFT JOIN  SeatMaster sm on sm.concert.id = c.id
                LEFT JOIN SeatInventory si on si.seatMaster.id = sm.id
                WHERE c.id = :concertId
                GROUP BY c.id, c.title, c.address, c.startsAt, c.endsAt
            """)
    ConcertDetailResponse getConcertDetailById(long concertId);

    @Query("""
            SELECT new kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertDetailResponse(
                c.id,
                c.title,
                c.address,
                c.startsAt,
                c.endsAt,
                COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0),
                c.status
            )
            FROM Concert c
            LEFT JOIN SeatMaster sm on sm.concert.id = c.id
            LEFT JOIN SeatInventory si on si.seatMaster.id = sm.id
            WHERE c.id = :concertId
            GROUP BY c.id, c.title, c.address, c.startsAt, c.endsAt, c.status
            """)
    AdminConcertDetailResponse getAdminConcertDetailById(long concertId);

    @Query("""
                 SELECT new kr.hhplus.be.server.admin.domain.concert.dto.response.AdminConcertListResponse(
                    c.id,
                    c.title,
                    c.address,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0 ),
                    c.startsAt,
                    c.status
                )
                 FROM Concert c
                 LEFT JOIN SeatMaster sm on sm.concert.id = c.id
                 LEFT JOIN SeatInventory si on si.seatMaster.id = sm.id
                 GROUP BY c.id, c.title, c.address, c.startsAt, c.status
            """)
    List<AdminConcertListResponse> findAllAdminConcerts();

}
