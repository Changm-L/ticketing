package kr.hhplus.be.server.user.domain.concert.exception;

public class ConcertNotFoundException extends ConcertException {
    private static final String MESSAGE = "콘서트를 찾을 수 없습니다.";

    public ConcertNotFoundException() {
        super(MESSAGE);
    }
}
