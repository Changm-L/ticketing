package kr.hhplus.be.server.user.domain.auth.core.exception;

public class PasswordNotMatchException extends AuthException {
    private static final String MESSAGE = "아이디 또는 비밀번호가 올바르지 않습니다.";

    public PasswordNotMatchException() {
        super(MESSAGE);
    }
}
