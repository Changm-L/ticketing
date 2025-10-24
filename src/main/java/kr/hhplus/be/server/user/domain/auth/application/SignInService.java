package kr.hhplus.be.server.user.domain.auth.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.hhplus.be.server._core.infrastructure.jwt.JwtProvider;
import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPair;
import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPairUserResponse;
import kr.hhplus.be.server.user.domain.auth.core.exception.PasswordNotMatchException;
import kr.hhplus.be.server.user.domain.auth.core.port.in.SignInUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignInCommand;
import kr.hhplus.be.server.user.domain.user.core.model.User;
import kr.hhplus.be.server.user.domain.user.core.port.out.UserPort;

@Service
@RequiredArgsConstructor
public class SignInService implements SignInUseCase {

    private final PasswordEncoder    passwordEncoder;
    private final UserPort           userPort;
    private final JwtProvider        jwtProvider;
    private final RedisTokenProvider redisTokenProvider;

    @Override
    @Transactional
    public TokenPairUserResponse signIn(SignInCommand command) {
        User user = userPort.getUserByEmail((command.email()));
        if (!passwordEncoder.matches(command.rawPassword(), user.getPassword())) {
            throw new PasswordNotMatchException();
        }

        TokenPair pair = jwtProvider.issueTokens(user);
        redisTokenProvider.saveRefreshToken(pair.refresh(), user.getId());

        return TokenPairUserResponse.of(pair);
    }
}
