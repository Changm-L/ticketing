package kr.hhplus.be.server.user.domain.auth.core.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public abstract class AuthException extends ApiException {
    public static final String DEFAULT_MESSAGE = "미 인증 사용자입니다.";

    public AuthException() {
        super(DEFAULT_MESSAGE);
    }

    public AuthException(String message) {
        super(message);
    }
}
