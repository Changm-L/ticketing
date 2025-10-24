package kr.hhplus.be.server.user.domain.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import kr.hhplus.be.server._core.infrastructure.jwt.JwtProvider;
import kr.hhplus.be.server.user.domain.auth.core.exception.AlreadySignOutTokenException;
import kr.hhplus.be.server.user.domain.auth.core.port.in.SignOutUseCase;

@Service
@RequiredArgsConstructor
public class SignOutService implements SignOutUseCase {

    private final JwtProvider        jwtProvider;
    private final RedisTokenProvider redisTokenProvider;

    @Override
    @Transactional
    public void signOut(String token) {
        Claims claims = jwtProvider.parseToken(token);
        if (redisTokenProvider.isBlackList(claims)) {
            throw new AlreadySignOutTokenException();
        }

        redisTokenProvider.blackList(claims);
    }
}
