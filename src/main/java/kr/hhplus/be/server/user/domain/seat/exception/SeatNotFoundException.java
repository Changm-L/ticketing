package kr.hhplus.be.server.user.domain.seat.exception;

public class SeatNotFoundException extends SeatException {
    public final static String MESSAGE = "좌석을 찾을 수 없습니다.";

    public SeatNotFoundException() {
        super(MESSAGE);
    }
}
