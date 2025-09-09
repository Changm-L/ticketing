package kr.hhplus.be.server.fixture.Concert;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.dto.response.SeatBatch;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
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

    public static SeatBatch seatBatch() {
        Concert concert = concert();
        List<SeatMaster> seatMasterList = new ArrayList<>();
        List<SeatInventory> seatInventoryList = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            SeatMaster seatMaster = new SeatMaster(concert, i % 10, i);
            seatMasterList.add(seatMaster);
            seatInventoryList.add(
                    SeatInventory.of(concert, seatMaster, BigDecimal.valueOf(10000L))
            );
        }

        return SeatBatch.of(seatMasterList, seatInventoryList);
    }
}
