package kr.hhplus.be.server.user.domain.user.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public abstract class UserException extends ApiException {
    public UserException(String message) {
        super(message);
    }
}
