package kr.hhplus.be.server.user.domain.seat.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatInventory;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;
import kr.hhplus.be.server.user.domain.concert.repository.ConcertRepository;
import kr.hhplus.be.server.user.domain.concert.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final ConcertRepository           concertRepository;
    private final SeatInventoryReadRepository seatInventoryReadRepository;

    public List<FindAllSeatsResponse> findAllAvailableSeats(long concertId) {
        Concert concert = concertRepository.getById(concertId);
        List<SeatInventory> activeSeatInventoryList = seatInventoryReadRepository.findAllSeatInventoryByConcert(concert);

        return activeSeatInventoryList
                .stream()
                .map(seatInventory -> {
                    SeatMaster seatMaster = seatInventory.getSeatMaster();
                    return FindAllSeatsResponse.of(seatMaster.getConcert(), seatMaster);
                })
                .collect(Collectors.toList());
    }
}
