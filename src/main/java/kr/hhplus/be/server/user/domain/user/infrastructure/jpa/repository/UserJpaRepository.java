package kr.hhplus.be.server.user.domain.user.infrastructure.jpa.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.user.core.exception.UserNotFoundException;
import kr.hhplus.be.server.user.domain.user.infrastructure.jpa.entity.UserJpaEntity;

@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findUserByEmail(String email);

    boolean existsByEmail(String email);

    default UserJpaEntity getUserByEmail(String email) {
        return findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    default UserJpaEntity getById(long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }

}
