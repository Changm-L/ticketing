package kr.hhplus.be.server.user.domain.concert.dto.response;

import java.util.List;

import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;

public record SeatBatch(
        List<SeatMaster> seatMasterList,
        List<SeatInventory> seatInventoryList
) {

    public static SeatBatch of(List<SeatMaster> seatMasterList, List<SeatInventory> seatInventoryList) {
        return new SeatBatch(seatMasterList, seatInventoryList);
    }
}
