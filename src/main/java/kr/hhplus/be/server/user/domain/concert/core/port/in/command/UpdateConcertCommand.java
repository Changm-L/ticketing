package kr.hhplus.be.server.user.domain.concert.core.port.in.command;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.core.constant.ConcertStatus;

public record UpdateConcertCommand(
        String title,
        String address,
        ConcertStatus status,
        LocalDate startsAt,
        LocalDate endsAt
) {
}
