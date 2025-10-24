package kr.hhplus.be.server.user.domain.concert;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.hhplus.be.server.fixture.concert.ConcertFixture;
import kr.hhplus.be.server.user.domain.concert.application.SeatGenerator;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.ConcertJpaEntity;
import kr.hhplus.be.server.user.domain.concert.infrastructure.jpa.entity.SeatMasterJpaEntity;

import static org.assertj.core.api.Assertions.assertThat;

class SeatGeneratorTest {

    private final SeatGenerator seatGenerator = new SeatGenerator();

    @Nested
    class generateSeatInventoryJpaEntity {
        @Test
        void concert와_maxSeatNo로_seatBatch를_생성한다() {
            //given
            ConcertJpaEntity concertJpaEntity = ConcertFixture.concert();
            int maxSeatNo = 50;

            //when
            List<SeatMasterJpaEntity> expected = seatGenerator.generateSeatMasterAndInventory(
                    concertJpaEntity,
                    maxSeatNo
            );

            //then
            assertThat(expected).hasSize(maxSeatNo);

            SeatMasterJpaEntity firstSeatMasterJpaEntity = expected.get(0);
            SeatMasterJpaEntity tenthSeatMasterJpaEntity = expected.get(9);
            SeatMasterJpaEntity elevenSeatMasterJpaEntity = expected.get(10);
            assertThat(firstSeatMasterJpaEntity.getRowNo()).isEqualTo(1);
            assertThat(tenthSeatMasterJpaEntity.getRowNo()).isEqualTo(1);
            assertThat(elevenSeatMasterJpaEntity.getRowNo()).isEqualTo(2);

        }
    }

}