package kr.hhplus.be.server.admin.domain.concert.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateConcertRequest(
        @NotBlank String title,
        @NotBlank String address,
        @NotNull LocalDate startsAt,
        @NotNull LocalDate endsAt
) {

    @AssertTrue
    public boolean isValidPeriod() {
        if (startsAt == null || endsAt == null) {
            return true;
        }
        return !endsAt.isBefore(startsAt);
    }
}
