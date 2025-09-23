package kr.hhplus.be.server.user.domain.auth.service;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import kr.hhplus.be.server.user.domain.auth.dto.response.TokenPair;

@Component
@RequiredArgsConstructor
public class RedisTokenProvider {
    private final        StringRedisTemplate redisTemplate;
    private static final String              REFRESH_TOKEN_PREFIX = "rt:", BLACK_LIST_PREFIX = "bt:";

    public boolean isBlackList(Claims claims) {
        String jti = claims.getId();
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACK_LIST_PREFIX + jti));
    }

    public void blackList(Claims claims) {
        String jti = claims.getId();
        String stringUserId = claims.getSubject();
        Duration ttl = Duration.between(Instant.now(), claims.getExpiration().toInstant());
        redisTemplate.opsForValue().set(BLACK_LIST_PREFIX + jti, stringUserId, ttl);
    }

    public void saveRefreshToken(
            TokenPair.Refresh refreshToken,
            long userId
    ) {
        Duration ttl = Duration.between(Instant.now(), refreshToken.expiredAt());
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + userId, refreshToken.token(), ttl);
    }

    public boolean isRefreshTokenActive(Claims claims) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(REFRESH_TOKEN_PREFIX + claims.getId()));
    }
}
