package kr.hhplus.be.server._core.environment;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "security")
public record SecurityProperties(
        JwtProperties jwt
) {

    public record JwtProperties(
            AccessProperties access,
            RefreshProperties refresh
    ) {
    }

    public record AccessProperties(
            String secret,
            int expiration
    ) {
    }

    public record RefreshProperties(
            String secret,
            int expiration
    ) {
    }
}
