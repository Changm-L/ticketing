package kr.hhplus.be.server.user.domain.reservation.core.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public class ReservationException extends ApiException {
    public ReservationException(String message) {
        super(message);
    }
}
