package kr.hhplus.be.server.user.domain.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import kr.hhplus.be.server._core.infrastructure.jwt.JwtProvider;
import kr.hhplus.be.server.user.domain.auth.core.constant.JwtTokenType;
import kr.hhplus.be.server.user.domain.auth.core.dto.ReissueResponse;
import kr.hhplus.be.server.user.domain.auth.core.dto.TokenPair;
import kr.hhplus.be.server.user.domain.auth.core.exception.AlreadySignOutTokenException;
import kr.hhplus.be.server.user.domain.auth.core.port.in.ReIssueUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.ReIssueTokenCommand;
import kr.hhplus.be.server.user.domain.user.core.model.User;
import kr.hhplus.be.server.user.domain.user.core.port.out.UserPort;

@Service
@RequiredArgsConstructor
public class ReIssueService implements ReIssueUseCase {

    private final JwtProvider        jwtProvider;
    private final RedisTokenProvider redisTokenProvider;
    private final UserPort           userPort;

    @Override
    @Transactional
    public ReissueResponse reissue(ReIssueTokenCommand command) {
        Claims claims = jwtProvider.parseToken(command.refreshToken());
        if (!claims.get(JwtProvider.TOKEN_TYPE_CLAIM_KEY).equals(JwtTokenType.REFRESH.name())) {
            throw new IllegalArgumentException("Refresh 토큰이 아닙니다.");
        }

        if (redisTokenProvider.isBlackList(claims)) {
            throw new AlreadySignOutTokenException();
        }

        long userId = Long.parseLong(claims.getSubject());
        User user = userPort.getById(userId);
        redisTokenProvider.blackList(claims);

        TokenPair pair = jwtProvider.issueTokens(user);
        redisTokenProvider.saveRefreshToken(pair.refresh(), user.getId());

        return ReissueResponse.of(pair);
    }
}
