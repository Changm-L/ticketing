package kr.hhplus.be.server.domain.concert.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public abstract class ConcertException extends ApiException {
    public ConcertException(String message) {
        super(message);
    }
}
