package kr.hhplus.be.server.user.domain.auth.core.port.in.command;

public record SignInCommand(
        String email,
        String rawPassword
) {
}
