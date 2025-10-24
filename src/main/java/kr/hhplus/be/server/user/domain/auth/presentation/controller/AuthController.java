package kr.hhplus.be.server.user.domain.auth.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.CommonResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server._core.dto.response.CreateRes;
import kr.hhplus.be.server.user.domain.auth.application.facade.AuthFacade;
import kr.hhplus.be.server.user.domain.auth.core.dto.ReissueResponse;
import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPairUserResponse;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.ReIssueTokenCommand;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignInCommand;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignUpCommand;
import kr.hhplus.be.server.user.domain.auth.presentation.dto.ReIssueTokenRequest;
import kr.hhplus.be.server.user.domain.auth.presentation.dto.SignInRequest;
import kr.hhplus.be.server.user.domain.auth.presentation.dto.SignUpRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthFacade authFacade;

    @PostMapping("/sign-up")
    public ResponseEntity<SingleResult<CreateRes>> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        SignUpCommand signUpCommand = new SignUpCommand(request.email(), request.name(), request.password());
        long id = authFacade.signUp(signUpCommand);
        return ApiResponse.ok(new CreateRes(id));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SingleResult<TokenPairUserResponse>> signIn(
            @Valid @RequestBody SignInRequest request
    ) {
        SignInCommand signInCommand = new SignInCommand(request.email(), request.password());

        return ApiResponse.ok(authFacade.signIn(signInCommand));
    }

    @PostMapping("/re-issue")
    public ResponseEntity<SingleResult<ReissueResponse>> reIssue(
            @Valid @RequestBody ReIssueTokenRequest request
    ) {
        ReIssueTokenCommand command = new ReIssueTokenCommand(request.accessToken(), request.refreshToken());
        return ApiResponse.ok(authFacade.reIssue(command));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<CommonResult> signOut(
            @RequestAttribute("token") String token
    ) {
        authFacade.signOut(token);
        return ApiResponse.ok();
    }

}
