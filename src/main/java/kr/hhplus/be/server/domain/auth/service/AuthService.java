package kr.hhplus.be.server.domain.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import kr.hhplus.be.server._core.infrastructure.jwt.JwtProvider;
import kr.hhplus.be.server.domain.auth.constant.JwtTokenType;
import kr.hhplus.be.server.domain.auth.dto.request.ReIssueTokenRequest;
import kr.hhplus.be.server.domain.auth.dto.request.SignInRequest;
import kr.hhplus.be.server.domain.auth.dto.request.SignUpRequest;
import kr.hhplus.be.server.domain.auth.dto.response.TokenPair;
import kr.hhplus.be.server.domain.auth.exception.AlreadySignOutTokenException;
import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.exception.UserAlreadyExistException;
import kr.hhplus.be.server.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository     userRepository;
    private final PasswordEncoder    passwordEncoder;
    private final JwtProvider        jwtProvider;
    private final RedisTokenProvider redisTokenProvider;

    @Transactional
    public long signUp(SignUpRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new UserAlreadyExistException();
        }

        User user = new User(request);
        return userRepository.save(user).getId();
    }

    @Transactional
    public TokenPair signIn(SignInRequest request) {
        User user = userRepository.getByUserName(request.name());
        if (!passwordEncoder.matches(user.getPassword(), request.password())) {
            throw new IllegalArgumentException("아이디 또는 비밀번호가 올바르지 않습니다.");
        }

        TokenPair pair = jwtProvider.issueTokens(user);
        redisTokenProvider.saveRefreshToken(pair.refresh(), user.getId());
        return pair;
    }

    @Transactional
    public void signOut(String token) {
        if (redisTokenProvider.isBlackList(token)) {
            throw new AlreadySignOutTokenException();
        }
        Claims claims = jwtProvider.parseToken(token);

        redisTokenProvider.blackList(claims);
    }

    @Transactional
    public TokenPair reIssue(ReIssueTokenRequest request) {
        Claims claims = jwtProvider.parseToken(request.refreshToken());
        if (!claims.get(JwtProvider.TOKEN_TYPE_CLAIM_KEY).equals(JwtTokenType.REFRESH.name())) {
            throw new IllegalArgumentException("Refresh 토큰이 아닙니다.");
        }

        String oldJwtTokenId = claims.getId();
        if (redisTokenProvider.isBlackList(oldJwtTokenId)) {
            throw new AlreadySignOutTokenException();
        }

        long userId = Long.parseLong(claims.getSubject());
        User user = userRepository.getUserById(userId);
        redisTokenProvider.blackList(claims);

        TokenPair pair = jwtProvider.issueTokens(user);
        redisTokenProvider.saveRefreshToken(pair.refresh(), user.getId());

        return pair;
    }

}
