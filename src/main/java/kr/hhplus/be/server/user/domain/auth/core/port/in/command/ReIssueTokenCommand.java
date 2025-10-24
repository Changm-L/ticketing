package kr.hhplus.be.server.user.domain.auth.core.port.in.command;

import jakarta.validation.constraints.NotBlank;

public record ReIssueTokenCommand(
        @NotBlank String accessToken,
        @NotBlank String refreshToken
) {
}
