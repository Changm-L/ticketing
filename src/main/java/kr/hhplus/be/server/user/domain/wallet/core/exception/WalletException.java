package kr.hhplus.be.server.user.domain.wallet.core.exception;

import kr.hhplus.be.server._core.exception.ApiException;

public class WalletException extends ApiException {

    public WalletException(String message) {
        super(message);
    }
}
