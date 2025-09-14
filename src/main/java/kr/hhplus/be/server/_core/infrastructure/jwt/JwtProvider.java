package kr.hhplus.be.server._core.infrastructure.jwt;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import kr.hhplus.be.server._core.environment.SecurityProperties;
import kr.hhplus.be.server.user.domain.auth.constant.JwtTokenType;
import kr.hhplus.be.server.user.domain.auth.dto.response.TokenPair;
import kr.hhplus.be.server.user.domain.user.constant.Role;
import kr.hhplus.be.server.user.domain.user.entity.User;

@Component
public class JwtProvider {

    private static final String                           ROLE_CLAIM_KEY       = Role.class.getSimpleName();
    public static final  String                           TOKEN_TYPE_CLAIM_KEY = JwtTokenType.class.getSimpleName();
    private final        SecurityProperties.JwtProperties jwtProperties;
    private final        SecretKey                        accessSecretKey;
    private final        SecretKey                        refreshSecretKey;

    public JwtProvider(SecurityProperties securityProperties) {
        this.jwtProperties = securityProperties.jwt();
        this.accessSecretKey = new SecretKeySpec(
                jwtProperties.access().secret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        this.refreshSecretKey = new SecretKeySpec(
                jwtProperties.refresh().secret().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    public TokenPair issueTokens(User user) {
        Instant now = Instant.now();

        int jitter = (int) (Math.floor(Math.random() * 10)) + 1;
        Instant accessExpiration = now.plusSeconds(jwtProperties.access().expiration()).plusSeconds(jitter);
        Instant refreshExpiration = now.plusSeconds(jwtProperties.refresh().expiration()).plusSeconds(jitter);

        TokenPair.Access accessToken = issueAccessToken(user, accessExpiration, now);
        TokenPair.Refresh refreshToken = issueRefreshToken(user, refreshExpiration, now);

        return new TokenPair(accessToken, refreshToken);
    }

    public Claims parseToken(String token) {
        return Jwts
                .parser()
                .verifyWith(accessSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private TokenPair.Access issueAccessToken(
            User user,
            Instant ttl,
            Instant now
    ) {
        String accessJti = UUID.randomUUID().toString();

        String token = Jwts.builder()
                           .subject(String.valueOf(user.getId()))
                           .id(accessJti)
                           .claim(ROLE_CLAIM_KEY, user.getRole())
                           .claim(TOKEN_TYPE_CLAIM_KEY, JwtTokenType.ACCESS.toString())
                           .issuedAt(Date.from(now))
                           .expiration(Date.from(ttl))
                           .signWith(accessSecretKey)
                           .compact();
        return new TokenPair.Access(token, accessJti, ttl);
    }

    private TokenPair.Refresh issueRefreshToken(
            User user,
            Instant ttl,
            Instant now
    ) {
        String refreshJti = UUID.randomUUID().toString();

        String token = Jwts.builder()
                           .subject(String.valueOf(user.getId()))
                           .id(refreshJti)
                           .issuedAt(Date.from(now))
                           .claim(TOKEN_TYPE_CLAIM_KEY, JwtTokenType.REFRESH.toString())
                           .expiration(Date.from(ttl))
                           .signWith(refreshSecretKey)
                           .compact();

        return new TokenPair.Refresh(token, refreshJti, ttl);
    }

}
