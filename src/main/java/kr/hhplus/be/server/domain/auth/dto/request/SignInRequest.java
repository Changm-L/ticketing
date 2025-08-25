package kr.hhplus.be.server.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank String name,
        @NotBlank String password
) {
}
