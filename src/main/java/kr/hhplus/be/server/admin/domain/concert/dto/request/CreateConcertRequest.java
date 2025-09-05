package kr.hhplus.be.server.admin.domain.concert.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

public record CreateConcertRequest(
        @NotBlank String title,
        @NotBlank String address,
        @NotBlank LocalDate startsAt,
        @NotBlank LocalDate endsAt
) {

    @AssertTrue
    public boolean isValidPeriod() {
        return !endsAt.isBefore(startsAt);
    }
}
