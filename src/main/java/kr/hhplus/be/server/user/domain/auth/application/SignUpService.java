package kr.hhplus.be.server.user.domain.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.hhplus.be.server.user.domain.auth.core.port.in.SignUpUseCase;
import kr.hhplus.be.server.user.domain.auth.core.port.in.command.SignUpCommand;
import kr.hhplus.be.server.user.domain.user.core.exception.UserAlreadyExistException;
import kr.hhplus.be.server.user.domain.user.core.model.User;
import kr.hhplus.be.server.user.domain.user.core.port.out.UserPort;

@Service
@RequiredArgsConstructor
public class SignUpService implements SignUpUseCase {

    private final UserPort userPort;

    @Override
    @Transactional
    public long signUp(SignUpCommand command) {
        if (userPort.existsByEmail(command.email())) {
            throw new UserAlreadyExistException();
        }

        User user = User.create(command.email(), command.name(), command.rawPassword());
        User createdUser = userPort.save(user);

        return createdUser.getId();
    }
}
