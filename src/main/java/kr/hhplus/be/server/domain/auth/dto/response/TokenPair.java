package kr.hhplus.be.server.domain.auth.dto.response;

public record TokenPair(
        String accessToken,
        String refreshToken
        //        Instant issuedAt,
        //        Instant expiredAt,
        //        JwtTokenType type
) {
}
