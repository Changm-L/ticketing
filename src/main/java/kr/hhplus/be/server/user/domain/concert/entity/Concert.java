package kr.hhplus.be.server.user.domain.concert.entity;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import kr.hhplus.be.server._core.entity.BaseTimeEntity;
import kr.hhplus.be.server.admin.domain.concert.dto.request.CreateConcertRequest;
import kr.hhplus.be.server.admin.domain.concert.dto.request.UpdateConcertRequest;
import kr.hhplus.be.server.user.domain.concert.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.dto.response.SeatBatch;

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

    @OneToMany(
            mappedBy = "concert",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<SeatMaster> seatMasterList;

    @OneToMany(
            mappedBy = "concert",
            fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<SeatInventory> seatInventoryList;

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

    public void update(UpdateConcertRequest request) {
        if (request.title() != null) {
            this.title = request.title();
        }
        if (request.status() != null) {
            this.status = request.status();
        }
        if (request.address() != null) {
            this.address = request.address();
        }
        if (request.startsAt() != null) {
            this.startsAt = request.startsAt();
        }
        if (request.endsAt() != null) {
            this.endsAt = request.endsAt();
        }
    }

    public void resetSeatsWith(SeatBatch seatBatch) {
        this.seatMasterList = seatBatch.seatMasterList();
        this.seatInventoryList = seatBatch.seatInventoryList();
    }
}
