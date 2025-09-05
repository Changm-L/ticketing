package kr.hhplus.be.server.user.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import kr.hhplus.be.server._core.dto.ApiResponse;
import kr.hhplus.be.server._core.dto.CommonResult;
import kr.hhplus.be.server._core.dto.SingleResult;
import kr.hhplus.be.server._core.dto.response.CreateRes;
import kr.hhplus.be.server.user.domain.auth.dto.request.ReIssueTokenRequest;
import kr.hhplus.be.server.user.domain.auth.dto.request.SignInRequest;
import kr.hhplus.be.server.user.domain.auth.dto.request.SignUpRequest;
import kr.hhplus.be.server.user.domain.auth.dto.response.TokenPair;
import kr.hhplus.be.server.user.domain.auth.service.AuthService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<SingleResult<CreateRes>> signUp(
            @Valid @RequestBody SignUpRequest request
    ) {
        long id = authService.signUp(request);
        return ApiResponse.ok(new CreateRes(id));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SingleResult<TokenPair>> signIn(
            @Valid @RequestBody SignInRequest request
    ) {
        return ApiResponse.ok(authService.signIn(request));
    }

    @PostMapping("/re-issue")
    public ResponseEntity<SingleResult<TokenPair>> reIssue(
            @Valid @RequestBody ReIssueTokenRequest request
    ) {
        return ApiResponse.ok(authService.reIssue(request));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<CommonResult> signOut(
            @RequestAttribute("token") String token
    ) {
        authService.signOut(token);
        return ApiResponse.ok();
    }

}
