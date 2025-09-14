package kr.hhplus.be.server.user.domain.concert;

import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import kr.hhplus.be.server.fixture.Concert.AdminConcertFixture;
import kr.hhplus.be.server.user.domain.concert.entity.Concert;
import kr.hhplus.be.server.user.domain.concert.entity.SeatMaster;

import static org.assertj.core.api.Assertions.assertThat;

class SeatGeneratorTest {

    private final SeatGenerator seatGenerator = new SeatGenerator();

    @Nested
    class generateSeatInventory {
        @Test
        void concert와_maxSeatNo로_seatBatch를_생성한다() {
            //given
            Concert concert = AdminConcertFixture.concert();
            int maxSeatNo = 50;

            //when
            List<SeatMaster> expected = seatGenerator.generateSeatMasterAndInventory(concert, maxSeatNo);

            //then
            assertThat(expected).hasSize(maxSeatNo);

            SeatMaster firstSeatMaster = expected.get(0);
            SeatMaster tenthSeatMaster = expected.get(9);
            SeatMaster elevenSeatMaster = expected.get(10);
            assertThat(firstSeatMaster.getRowNo()).isEqualTo(1);
            assertThat(tenthSeatMaster.getRowNo()).isEqualTo(1);
            assertThat(elevenSeatMaster.getRowNo()).isEqualTo(2);

        }
    }

}