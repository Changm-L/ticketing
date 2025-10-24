package kr.hhplus.be.server.user.domain.auth.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignInRequest(
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        @Pattern(
                regexp = "^[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,253}\\.[A-Za-z]{2,63}$",
                message = "허용되지 않는 이메일 형식입니다.")
        String email,
        @NotBlank String password
) {
}
