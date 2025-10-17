package kr.hhplus.be.server.user.domain.wallet.core.exception;

public class InvalidChargeAmountException extends WalletException {
    public static final String MESSAGE = "잘못된 충전 금액입니다.";

    public InvalidChargeAmountException() {
        super(MESSAGE);
    }

    public InvalidChargeAmountException(String message) {
        super(message);
    }
}
