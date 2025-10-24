package kr.hhplus.be.server.user.domain.concert.application;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.concert.core.model.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.core.model.SeatMaster;

@Component
public class SeatGenerator {

    /**
     * 좌석 수를 파라미터로 받고
     * 해당 파라미터를 통해 SeatMasters, SeatInventories를 반환하는 메소드
     * SeatMaster의 rowNo의 경우 한줄의 10개의 좌석이 있다고 가정하고 생성
     * SeatMaster생성 시 SeatInventory 함께 생성
     *
     * @param maxSeatNo Seat좌석 수
     *
     * @return List<SeatMaster>
     */
    public List<SeatMaster> generateSeatMasterAndInventory(int maxSeatNo) {
        List<SeatMaster> seatMasters = new ArrayList<>();

        for (int i = 1; i <= maxSeatNo; i++) {
            SeatInventory seatInventory = SeatInventory.available(BigDecimal.valueOf(10000L));
            SeatMaster seatMasterJpaEntity = SeatMaster.create(
                    (i - 1) / 10 + 1,
                    i,
                    seatInventory
            );
            seatMasters.add(seatMasterJpaEntity);
        }

        return seatMasters;
    }
}
