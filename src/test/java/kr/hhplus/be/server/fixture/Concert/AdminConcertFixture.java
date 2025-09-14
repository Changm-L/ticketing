package kr.hhplus.be.server.fixture.Concert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;

public class AdminConcertFixture {

    public static CreateConcertRequest createConcertRequest() {
        return new CreateConcertRequest(
                "title",
                "address",
                LocalDate.now(),
                LocalDate.now().plusDays(1)
        );
    }

    public static Concert concert() {
        CreateConcertRequest request = createConcertRequest();
        return new Concert(request);
    }

    public static List<SeatMaster> seatMasterList() {
        Concert concert = concert();
        List<SeatMaster> seatMasterList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            SeatMaster seatMaster = SeatMaster.of(concert, BigDecimal.valueOf(10000L), i % 10, i);
            seatMasterList.add(seatMaster);
        }

        return seatMasterList;
    }
}
