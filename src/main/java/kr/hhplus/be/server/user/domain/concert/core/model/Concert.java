package kr.hhplus.be.server.user.domain.concert.core.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import kr.hhplus.be.server.user.domain.concert.core.constant.ConcertStatus;
import kr.hhplus.be.server.user.domain.concert.core.port.in.command.CreateConcertCommand;
import kr.hhplus.be.server.user.domain.concert.core.port.in.command.UpdateConcertCommand;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;

public class Concert {
    private       long                      id;
    private       String                    title;
    private       ConcertStatus             status;
    private       String                    address;
    private       LocalDate                 startsAt;
    private       LocalDate                 endsAt;
    private       LocalDateTime             createdAt;
    private       LocalDateTime             updatedAt;
    private final List<SeatMasterJpaEntity> seatMasterJpaEntityList = new ArrayList<>();
    //TODO: SeatInventory POJO ENTITY로 변경

    private Concert(
            long id,
            String title,
            ConcertStatus status,
            String address,
            LocalDate startsAt,
            LocalDate endsAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.address = address;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

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

    public Concert(
            CreateConcertCommand command
    ) {
        this(
                command.title(),
                ConcertStatus.ACTIVE,
                command.address(),
                command.startsAt(),
                command.endsAt()
        );
    }

    public void update(UpdateConcertCommand command) {
        if (command.title() != null) {
            this.title = command.title();
        }
        if (command.status() != null) {
            this.status = command.status();
        }
        if (command.address() != null) {
            this.address = command.address();
        }
        if (command.startsAt() != null) {
            this.startsAt = command.startsAt();
        }
        if (command.endsAt() != null) {
            this.endsAt = command.endsAt();
        }
    }

    public void resetSeatsWith(List<SeatMasterJpaEntity> seatMasterJpaEntityList) {
        this.seatMasterJpaEntityList.clear();
        this.seatMasterJpaEntityList.addAll(seatMasterJpaEntityList);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public ConcertStatus getStatus() {
        return status;
    }

    public String getAddress() {
        return address;
    }

    public LocalDate getStartsAt() {
        return startsAt;
    }

    public LocalDate getEndsAt() {
        return endsAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public List<SeatMasterJpaEntity> getSeatMasterJpaEntityList() {
        return seatMasterJpaEntityList;
    }
}
