package kr.hhplus.be.server.user.domain.auth.core.port.in.command;

public record SignUpCommand(
        String email,
        String name,
        String rawPassword
) {
}
