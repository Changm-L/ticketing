package kr.hhplus.be.server.user.domain.auth.core.port.in;

import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignUpCommand;

public interface SignUpUseCase {
    long signUp(SignUpCommand command);
}
