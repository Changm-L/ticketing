package kr.hhplus.be.server.user.domain.user.core.port.out;

import kr.hhplus.be.server.user.domain.user.core.model.User;

public interface UserPort {
    boolean existsByEmail(String email);

    User getUserByEmail(String email);

    User getById(long id);

    User save(User user);

}
