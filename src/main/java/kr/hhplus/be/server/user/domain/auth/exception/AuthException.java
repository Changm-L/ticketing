package kr.hhplus.be.server.user.domain.auth.exception;

public abstract class AuthException extends IllegalStateException {
    public static final String DEFAULT_MESSAGE = "미 인증 사용자입니다.";

    public AuthException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthException(String message) {
        super(message);
    }
}
