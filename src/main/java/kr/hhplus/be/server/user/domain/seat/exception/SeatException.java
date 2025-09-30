package kr.hhplus.be.server.user.domain.seat.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public class SeatException extends ApiException {
    public SeatException(String message) {
        super(message);
    }
}
