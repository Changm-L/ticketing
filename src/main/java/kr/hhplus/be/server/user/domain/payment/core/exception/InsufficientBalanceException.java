package kr.hhplus.be.server.user.domain.payment.core.exception;

import kr.hhplus.be.server.user.domain.reservation.core.exception.ReservationException;

public class InsufficientBalanceException extends ReservationException {
    public static final String MESSAGE = "지갑 잔액이 부족합니다.";

    public InsufficientBalanceException() {
        super(MESSAGE);
    }
}
