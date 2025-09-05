package kr.hhplus.be.server.user.domain.concert.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Concert extends BaseTimeEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ConcertStatus status;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private LocalDate startsAt;

    @Column(nullable = false)
    private LocalDate endsAt;

    private Concert(
            String title,
            ConcertStatus status,
            String address,
            LocalDate startsAt,
            LocalDate endsAt
    ) {
        this.title = title;
        this.status = status;
        this.address = address;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public Concert(CreateConcertRequest request) {
        this(
                request.title(),
                ConcertStatus.ACTIVE,
                request.address(),
                request.startsAt(),
                request.endsAt()
        );
    }

}
