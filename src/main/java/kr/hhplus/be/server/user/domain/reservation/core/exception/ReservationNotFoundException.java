package kr.hhplus.be.server.user.domain.reservation.core.exception;

public class ReservationNotFoundException extends ReservationException {
    public final static String MESSAGE = "예약을 찾을 수 없습니다.";

    public ReservationNotFoundException() {
        super(MESSAGE);
    }
}
