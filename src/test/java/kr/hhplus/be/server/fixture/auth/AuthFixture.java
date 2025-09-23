package kr.hhplus.be.server.fixture.auth;

import org.springframework.test.util.ReflectionTestUtils;

import kr.hhplus.be.server.user.domain.auth.dto.request.SignUpRequest;
import kr.hhplus.be.server.user.domain.user.entity.User;

public class AuthFixture {

    public static SignUpRequest signUpRequest() {
        return new SignUpRequest(
                "test@gmail.com",
                "tester",
                "p@assW0rd"
        );
    }

    public static User user() {
        SignUpRequest request = signUpRequest();
        User user = User.of(request);

        ReflectionTestUtils.setField(user, "id", 1L);
        return user;
    }
}
