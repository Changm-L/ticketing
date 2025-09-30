package kr.hhplus.be.server.user.domain.reservation.core.exception;

public class AlreadyReservedException extends ReservationException {
    public static final String MESSAGE = "이미 예약된 좌석입니다.";

    public AlreadyReservedException() {
        super(MESSAGE);
    }
}
