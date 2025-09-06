package kr.hhplus.be.server.admin.domain.concert.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

public record CreateConcertRequest(
        @NotBlank String title,
        @NotBlank String address,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @FutureOrPresent LocalDate startsAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") @PastOrPresent LocalDate endsAt
) {

    @AssertTrue
    public boolean isValidPeriod() {
        if (startsAt == null || endsAt == null) {
            return true;
        }
        return !endsAt.isBefore(startsAt);
    }
}
