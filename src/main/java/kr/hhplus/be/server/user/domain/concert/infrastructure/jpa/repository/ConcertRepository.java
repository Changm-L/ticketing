package kr.hhplus.be.server.user.domain.concertJpaEntity.infrastructure.jpa.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.admin.domain.concertJpaEntity.dto.response.AdminConcertDetailResponse;
import kr.hhplus.be.server.admin.domain.concertJpaEntity.dto.response.AdminConcertListResponse;
import kr.hhplus.be.server.user.domain.concertJpaEntity.core.dto.ConcertDetailResponse;
import kr.hhplus.be.server.user.domain.concertJpaEntity.core.dto.ConcertListResponse;
import kr.hhplus.be.server.user.domain.concertJpaEntity.core.exception.ConcertNotFoundException;
import kr.hhplus.be.server.user.domain.concertJpaEntity.infrastructure.jpa.entity.ConcertJpaEntity;

@Repository
public interface ConcertRepository extends JpaRepository<ConcertJpaEntity, Long> {

    default ConcertJpaEntity getById(long id) {
        return findById(id).orElseThrow(ConcertNotFoundException::new);
    }

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.concertJpaEntity.core.dto.ConcertListResponse(
                   c.id,
                    c.title,
                    c.address,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0),
                    c.startsAt
                )
                FROM ConcertJpaEntity c
                LEFT JOIN SeatMaster sm on sm.concertJpaEntity.id = c.id
                LEFT JOIN SeatInventory si on sm.id = si.seatMasterJpaEntity.id
                GROUP BY c.id, c.title, c.address, c.startsAt
            """)
    List<ConcertListResponse> findAllConcertWithAvailableSeatsCount();

    @Query("""
                SELECT new kr.hhplus.be.server.user.domain.concertJpaEntity.core.dto.ConcertDetailResponse(
                    c.id,
                    c.title,
                    c.address,
                    c.startsAt,
                    c.endsAt,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0 )
                )
                FROM ConcertJpaEntity c
                LEFT JOIN  SeatMaster sm on sm.concertJpaEntity.id = c.id
                LEFT JOIN SeatInventory si on si.seatMasterJpaEntity.id = sm.id
                WHERE c.id = :concertId
                GROUP BY c.id, c.title, c.address, c.startsAt, c.endsAt
            """)
    ConcertDetailResponse getConcertDetailById(long concertId);

    @Query("""
            SELECT new kr.hhplus.be.server.admin.domain.concertJpaEntity.dto.response.AdminConcertDetailResponse(
                c.id,
                c.title,
                c.address,
                c.startsAt,
                c.endsAt,
                COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0),
                c.status
            )
            FROM ConcertJpaEntity c
            LEFT JOIN SeatMaster sm on sm.concertJpaEntity.id = c.id
            LEFT JOIN SeatInventory si on si.seatMasterJpaEntity.id = sm.id
            WHERE c.id = :concertId
            GROUP BY c.id, c.title, c.address, c.startsAt, c.endsAt, c.status
            """)
    AdminConcertDetailResponse getAdminConcertDetailById(long concertId);

    @Query("""
                 SELECT new kr.hhplus.be.server.admin.domain.concertJpaEntity.dto.response.AdminConcertListResponse(
                    c.id,
                    c.title,
                    c.address,
                    COALESCE(CAST(COUNT(CASE WHEN si.seatStatus = "AVAILABLE" THEN 1 END) as int), 0 ),
                    c.startsAt,
                    c.status
                )
                 FROM ConcertJpaEntity c
                 LEFT JOIN SeatMaster sm on sm.concertJpaEntity.id = c.id
                 LEFT JOIN SeatInventory si on si.seatMasterJpaEntity.id = sm.id
                 GROUP BY c.id, c.title, c.address, c.startsAt, c.status
            """)
    List<AdminConcertListResponse> findAllAdminConcerts();

}
