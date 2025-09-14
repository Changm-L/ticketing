package kr.hhplus.be.server.user.domain.seat.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server.user.domain.concert.repository.SeatInventoryReadRepository;
import kr.hhplus.be.server.user.domain.seat.dto.response.FindAllSeatsResponse;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatInventoryReadRepository seatInventoryReadRepository;

    public List<FindAllSeatsResponse> findAllAvailableSeats(long concertId) {
        return seatInventoryReadRepository.findAllSeatInventoryListWith(concertId);
    }
}
