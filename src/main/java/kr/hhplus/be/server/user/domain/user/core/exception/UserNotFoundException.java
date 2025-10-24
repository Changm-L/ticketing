package kr.hhplus.be.server.user.domain.user.core.exception;

public class UserNotFoundException extends UserException {
    private static final String MESSAGE = "유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
