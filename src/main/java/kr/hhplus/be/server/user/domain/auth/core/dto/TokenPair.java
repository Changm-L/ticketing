package kr.hhplus.be.server.user.domain.auth.core.dto;

import java.time.Instant;

public record TokenPair(
        Access access,
        Refresh refresh
) {

    public record Access(
            String token,
            String jti,
            Instant expiredAt
    ) {}

    public record Refresh(
            String token,
            String jti,
            Instant expiredAt
    ) {}
}
