package kr.hhplus.be.server.user.domain.user.infrastructure.jpa.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.user.core.model.User;
import kr.hhplus.be.server.user.domain.user.core.port.out.UserPort;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.mapper.UserMapper;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.repository.UserJpaRepository;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {

    private final UserJpaRepository userJpaRepository;
    private final UserMapper        userMapper;

    @Override
    public User save(User user) {
        UserJpaEntity userJpaEntity = UserJpaEntity.of(user.getEmail(), user.getName(), user.getPassword());
        userJpaRepository.save(userJpaEntity);

        return userMapper.toDomain(userJpaEntity);
    }

    @Override
    public User getById(long id) {
        UserJpaEntity userJpaEntity = userJpaRepository.getById(id);
        
        return userMapper.toDomain(userJpaEntity);
    }

    @Override
    public User getUserByEmail(String email) {
        UserJpaEntity userJpaEntity = userJpaRepository.getUserByEmail(email);

        return userMapper.toDomain(userJpaEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
