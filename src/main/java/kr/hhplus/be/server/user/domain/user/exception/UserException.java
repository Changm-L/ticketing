package kr.hhplus.be.server.user.domain.user.exception;

public abstract class UserException extends RuntimeException {
    public UserException(String message) {
        super(message);
    }
}
