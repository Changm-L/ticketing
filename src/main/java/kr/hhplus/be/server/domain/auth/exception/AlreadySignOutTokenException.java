package kr.hhplus.be.server.domain.auth.exception;

public class AlreadySignOutTokenException extends AuthException {
    public static final String MESSAGE = "이미 로그아웃 한 사용자입니다.";

    public AlreadySignOutTokenException() {
        super(MESSAGE);
    }
}
