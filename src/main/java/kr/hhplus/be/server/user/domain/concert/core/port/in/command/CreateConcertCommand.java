package kr.hhplus.be.server.user.domain.concert.core.port.in.command;

import java.time.LocalDate;

public record CreateConcertCommand(
        String title,
        String address,
        LocalDate startsAt,
        LocalDate endsAt
) {
}
