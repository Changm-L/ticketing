package kr.hhplus.be.server.user.domain.user.infrastructure.jpa.mapper;

import org.springframework.stereotype.Component;

import kr.hhplus.be.server.user.domain.user.core.model.User;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Component
public class UserMapper {

    public User toDomain(UserJpaEntity userJpaEntity) {
        return User.of(
                userJpaEntity.getId(),
                userJpaEntity.getEmail(),
                userJpaEntity.getEmail(),
                userJpaEntity.getPassword(),
                userJpaEntity.getRole(),
                userJpaEntity.getCreatedAt(),
                userJpaEntity.getUpdatedAt()
        );
    }
}
