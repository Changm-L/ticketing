package kr.hhplus.be.server.fixture.auth;

import org.springframework.test.util.ReflectionTestUtils;

import kr.hhplus.be.server.user.domain.auth.presentation.dto.SignUpRequest;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

public class AuthFixture {

    public static SignUpRequest signUpRequest() {
        return new SignUpRequest(
                "test@gmail.com",
                "tester",
                "p@assW0rd"
        );
    }

    public static UserJpaEntity user() {
        SignUpRequest request = signUpRequest();
        UserJpaEntity userJpaEntity = UserJpaEntity.of(request.email(), request.name(), request.password());

        ReflectionTestUtils.setField(userJpaEntity, "id", 1L);
        return userJpaEntity;
    }
}
