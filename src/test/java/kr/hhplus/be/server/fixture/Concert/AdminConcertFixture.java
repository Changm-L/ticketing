package kr.hhplus.be.server.fixture.Concert;

import java.time.LocalDate;

import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;

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
}
