package kr.hhplus.be.server.user.domain.concert.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.exception.ConcertNotFoundException;

@Repository
public interface ConcertRepository extends JpaRepository<Concert, Long> {

    default Concert getById(long id) {
        return findById(id).orElseThrow(ConcertNotFoundException::new);
    }

}
