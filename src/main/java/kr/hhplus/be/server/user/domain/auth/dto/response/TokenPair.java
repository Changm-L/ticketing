package kr.hhplus.be.server.user.domain.auth.dto.response;

import java.time.Instant;

public record TokenPair(
        Access access,
        Refresh refresh
) {

    public record Access(
            String token,
            Instant expiredAt
    ) {}

    public record Refresh(
            String token,
            Instant expiredAt
    ) {}
}
