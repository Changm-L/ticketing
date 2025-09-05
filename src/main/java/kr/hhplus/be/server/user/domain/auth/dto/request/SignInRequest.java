package kr.hhplus.be.server.user.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        String email,
        @NotBlank String password
) {
}
