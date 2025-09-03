package kr.hhplus.be.server.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import kr.hhplus.be.server.domain.user.entity.User;
import kr.hhplus.be.server.domain.user.exception.UserNotFoundException;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByName(String name);

    boolean existsByEmail(String email);

    default User getByUserName(String username) {
        return findUserByName(username).orElseThrow(UserNotFoundException::new);
    }

    default User getUserById(long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }
}
