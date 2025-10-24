package kr.hhplus.be.server.user.domain.auth.core.port.in;

public interface SignOutUseCase {
    void signOut(String token);
}
