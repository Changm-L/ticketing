package kr.hhplus.be.server.domain.auth.exception;

public class UnAuthorizationException extends AuthException {
    private static final String DEFAULT_MESSAGE = "인증되지 않은 사용자입니다.";

    public UnAuthorizationException() {
        super(DEFAULT_MESSAGE);
    }

    public UnAuthorizationException(String message) {
        super(message);
    }
}
