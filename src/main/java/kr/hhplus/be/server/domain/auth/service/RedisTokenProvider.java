package kr.hhplus.be.server.domain.auth.service;

import java.time.Duration;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Component
@RequiredArgsConstructor
public class RedisTokenProvider {
    private final        StringRedisTemplate redisTemplate;
    private static final String              REFRESH_TOKEN_PREFIX = "rt:", BLACK_LIST_PREFIX = "bt:";

    public boolean isBlackList(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACK_LIST_PREFIX + jti));
    }

    public void blackList(Claims claims) {
        String jti = claims.getId();
        Duration ttl = Duration.between(Instant.now(), claims.getExpiration().toInstant());
        redisTemplate.opsForValue().set(BLACK_LIST_PREFIX + jti, "1", ttl);
    }

    public void saveRefreshToken(Claims claims) {
        String jti = claims.getId();
        Duration ttl = Duration.between(Instant.now(), claims.getExpiration().toInstant());
        redisTemplate.opsForValue().set(REFRESH_TOKEN_PREFIX + jti, String.valueOf(claims.getSubject()), ttl);
    }

    public boolean isRefreshTokenActive(Claims claims) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(REFRESH_TOKEN_PREFIX + claims.getId()));
    }
}
