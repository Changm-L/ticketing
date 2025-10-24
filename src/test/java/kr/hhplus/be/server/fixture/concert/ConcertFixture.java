package kr.hhplus.be.server.fixture.concert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;

public class ConcertFixture {

    public static CreateConcertRequest createConcertRequest() {
        return new CreateConcertRequest(
                "title",
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }

    public static ConcertJpaEntity concert() {
        CreateConcertRequest request = createConcertRequest();
        return new ConcertJpaEntity(request);
    }

    public static List<SeatMasterJpaEntity> seatMasterList() {
        ConcertJpaEntity concertJpaEntity = concert();
        List<SeatMasterJpaEntity> seatMasterJpaEntityList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            SeatMasterJpaEntity seatMasterJpaEntity = SeatMasterJpaEntity.of(
                    concertJpaEntity,
                    BigDecimal.valueOf(10000L),
                    i % 10,
                    i
            );
            seatMasterJpaEntityList.add(seatMasterJpaEntity);
        }

        return seatMasterJpaEntityList;
    }
}
