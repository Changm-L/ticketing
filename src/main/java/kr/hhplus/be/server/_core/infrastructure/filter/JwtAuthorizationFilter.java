package kr.hhplus.be.server._core.infrastructure.filter;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import kr.hhplus.be.server._core.infrastructure.jwt.JwtProvider;
import kr.hhplus.be.server.domain.auth.constant.JwtTokenType;
import kr.hhplus.be.server.domain.auth.exception.UnAuthorizationException;

@Slf4j
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionResolver;
    private final JwtProvider              jwtProvider;
    private final String                   HEADER_STRING = "Authorization";
    private final String                   TOKEN_PREFIX  = "Bearer ";

    public JwtAuthorizationFilter(
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver handlerExceptionResolver,
            JwtProvider jwtProvider
    ) {
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws ServletException, IOException {
        try {

            String path = request.getRequestURI();
            if (path.startsWith("/auth/") || "OPTIONS".equals(request.getMethod())) {
                chain.doFilter(request, response);
                return;
            }

            String header = request.getHeader(HEADER_STRING);
            if (header == null || !header.startsWith(TOKEN_PREFIX)) {
                throw new UnAuthorizationException("Bearer 토큰이 없습니다.");
            }

            String token = header.replace(TOKEN_PREFIX, "");
            Claims claims = jwtProvider.parseToken(token);
            if (claims.get(JwtProvider.TOKEN_TYPE_CLAIM_KEY).equals(JwtTokenType.REFRESH.name())) {
                throw new UnAuthorizationException("Refresh Token을 사용할 수 없습니다.");
            }

            request.setAttribute("userId", Long.valueOf(claims.getSubject()));
            request.setAttribute("token", token);
            chain.doFilter(request, response);
        } catch (JwtException e) {
            handlerExceptionResolver.resolveException(
                    request,
                    response,
                    null,
                    new UnAuthorizationException("유효하지 않은 토큰입니다.")
            );
        } catch (UnAuthorizationException e) {
            handlerExceptionResolver.resolveException(request, response, null, e);
        } catch (Exception e) {
            log.error("인증 시 에러가 발생했습니다. 메세지: {}", e.getMessage());
            handlerExceptionResolver.resolveException(request, response, null, e);
        }

    }
}
