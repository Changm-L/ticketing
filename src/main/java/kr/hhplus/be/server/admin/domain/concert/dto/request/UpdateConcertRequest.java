package kr.hhplus.be.server.admin.domain.concert.dto.request;

import java.time.LocalDate;

import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;

public record UpdateConcertRequest(
        String title,
        ConcertStatus status,
        String address,
        LocalDate startsAt,
        LocalDate endsAt
) {
}
