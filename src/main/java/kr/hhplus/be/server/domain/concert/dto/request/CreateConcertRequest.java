package kr.hhplus.be.server.domain.concert.dto.request;

import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;

public record CreateConcertRequest(
        @NotBlank String title,
        @NotBlank String address,
        @NotBlank LocalDate startsAt,
        @NotBlank LocalDate endsAt
) {
}
