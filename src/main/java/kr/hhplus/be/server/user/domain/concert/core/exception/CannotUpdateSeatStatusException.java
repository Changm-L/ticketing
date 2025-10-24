package kr.hhplus.be.server.user.domain.concert.core.exception;

public class CannotUpdateSeatStatusException extends ConcertException {
    public static final String MESSAGE = "좌석을 예약할 수 없습니다.";

    public CannotUpdateSeatStatusException() {
        super(MESSAGE);
    }
}
