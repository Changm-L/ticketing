package kr.hhplus.be.server.domain.user.exception;

public class UserAlreadyExistException extends UserException {
    private static final String MESSAGE = "이미 존재하는 사용자입니다.";

    public UserAlreadyExistException() {
        super(MESSAGE);
    }
}
