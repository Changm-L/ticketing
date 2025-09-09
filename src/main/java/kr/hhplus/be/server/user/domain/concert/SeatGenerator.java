package kr.hhplus.be.server.user.domain.concert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.dto.response.SeatBatch;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;

@Component
public class SeatGenerator {

    /**
     * Concert와 좌석 수를 파라미터로 받고
     * 해당 파라미터를 통해 SeatMasters, SeatInventories를 반환하는 메소드
     * SeatMaster의 rowNo의 경우 한줄의 10개의 좌석이 있다고 가정하고 생성
     *
     * @param concert   Seat에 연관된 Concert
     * @param maxSeatNo Seat좌석 수
     *
     * @return SeatBatch
     */
    public SeatBatch generateSeatMasterAndInventory(
            Concert concert,
            int maxSeatNo
    ) {
        List<SeatMaster> seatMasters = new ArrayList<>();
        List<SeatInventory> seatInventoryList = new ArrayList<>();

        for (int i = 1; i <= maxSeatNo; i++) {
            SeatMaster seatMaster = SeatMaster.of(concert, (i - 1) / 10 + 1, i);
            seatMasters.add(seatMaster);
            seatInventoryList.add(
                    SeatInventory.of(concert, seatMaster, BigDecimal.valueOf(10000L))
            );
        }

        return SeatBatch.of(seatMasters, seatInventoryList);
    }
}
