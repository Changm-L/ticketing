package kr.hhplus.be.server.domain.user.exception;

public class UserNotFoundException extends UserException {
    private static final String MESSAGE = "유저를 찾을 수 없습니다.";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
