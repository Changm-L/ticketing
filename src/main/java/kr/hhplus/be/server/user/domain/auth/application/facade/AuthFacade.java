package kr.hhplus.be.server.user.domain.auth.application.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.auth.core.dto.ReissueResponse;
import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPairUserResponse;
import kr.hhplus.be.server.user.domain.auth.core.port.in.ReIssueUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.SignInUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.SignOutUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.SignUpUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.ReIssueTokenCommand;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignInCommand;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignUpCommand;

@Component
@RequiredArgsConstructor
public class AuthFacade {

    private final SignUpUseCase  signUpUseCase;
    private final SignInUseCase  signInUseCase;
    private final SignOutUseCase signOutUseCase;
    private final ReIssueUseCase reIssueUseCase;

    public long signUp(SignUpCommand signUpCommand) {
        return signUpUseCase.signUp(signUpCommand);
    }

    public TokenPairUserResponse signIn(SignInCommand signInCommand) {
        return signInUseCase.signIn(signInCommand);
    }

    public void signOut(String token) {
        signOutUseCase.signOut(token);
    }

    public ReissueResponse reIssue(ReIssueTokenCommand command) {
        return reIssueUseCase.reissue(command);
    }

}