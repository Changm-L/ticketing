package kr.hhplus.be.server.user.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReIssueTokenRequest(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) {
}
