package kr.hhplus.be.server._core.exception;

import lombok.Getter;

@Getter
public abstract class ApiException {
    private final String message;

    public ApiException(String message) {
        this.message = message;
    }
}
