package kr.hhplus.be.server.user.domain.auth.core.port.in;

import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPairUserResponse;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignInCommand;

public interface SignInUseCase {
    TokenPairUserResponse signIn(SignInCommand command);
}
