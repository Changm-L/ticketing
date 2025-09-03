package kr.hhplus.be.server.domain.auth.dto.request;

public record SignUpRequest(
        String email,
        String name,
        String password
) {
}
