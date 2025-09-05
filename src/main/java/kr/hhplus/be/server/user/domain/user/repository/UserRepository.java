package kr.hhplus.be.server.user.domain.user.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.user.entity.User;
import kr.hhplus.be.server.user.domain.user.exception.UserNotFoundException;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    boolean existsByEmail(String email);

    default User getUserByEmail(String email) {
        return findUserByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    default User getUserById(long id) {
        return findById(id).orElseThrow(UserNotFoundException::new);
    }

}
