package kr.hhplus.be.server.user.domain.auth.core.dto;

import java.time.Instant;

public record TokenPairUserResponse(
        Access accessToken,
        Refresh refreshToken
) {

    public static TokenPairUserResponse of(TokenPair tokenPair) {
        return new TokenPairUserResponse(
                new Access(tokenPair.access().token(), tokenPair.access().expiredAt()),
                new Refresh(tokenPair.refresh().token(), tokenPair.refresh().expiredAt())
        );
    }

    public record Access(
            String token,
            Instant expiredAt
    ) {
    }

    public record Refresh(
            String token,
            Instant expiredAt
    ) {
    }
}
