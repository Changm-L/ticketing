package kr.hhplus.be.server.user.domain.wallet.exception;

public class WalletNotFoundException extends WalletException {
    public static final String MESSAGE = "지갑을 찾을 수 없습니다.";

    public WalletNotFoundException() {
        super(MESSAGE);
    }
}
