package kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.mapper;

import kr.hhplus.be.server.user.domain.reservation.core.model.Reservation;
import kr.hhplus.be.server.user.domain.reservation.infrastructure.jpa.entity.ReservationJpaEntity;

public class ReservationMapper {

    public Reservation toDomain(ReservationJpaEntity entity) {
        Reservation reservation = new Reservation();
        // entity 값 직접 매핑
        // 파라미터로 넘겨서 처리하는게 깔끔하겠지만 도메인은 자기 말고 아무것도 몰라야함
        return reservation;
    }

    public ReservationJpaEntity toJpaEntity(Reservation reservation) {
        ReservationJpaEntity entity = new ReservationJpaEntity();
        // order 값 직접 매핑
        return entity;
    }
}
