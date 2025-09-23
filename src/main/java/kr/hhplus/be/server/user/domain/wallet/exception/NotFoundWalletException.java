package kr.hhplus.be.server.user.domain.wallet.exception;

public class NotFoundWalletException extends WalletException {
    public static final String MESSAGE = "지갑을 찾을 수 없습니다.";

    public NotFoundWalletException() {
        super(MESSAGE);
    }
}
