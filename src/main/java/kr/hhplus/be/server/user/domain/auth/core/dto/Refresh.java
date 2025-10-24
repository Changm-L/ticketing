package kr.hhplus.be.server.user.domain.auth.core.dto;

import java.time.Instant;

public record Refresh(
        String token,
        Instant expiredAt
) {
}
