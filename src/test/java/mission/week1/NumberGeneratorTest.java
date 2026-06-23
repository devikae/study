package mission.week1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberGeneratorTest {

    @Test
    @DisplayName("숫자 6개짜리 티켓을 생성한다")
    void generatorCreatesSixNumbers() {
        NumberGenerator generator = new NumberGenerator();

        NumberTicket ticket = generator.run(new HashSet<>());

        assertEquals(6, ticket.getNumbers().size());
    }

    @Test
    @DisplayName("생성된 숫자는 1 이상 45 이하")
    void generatorCreatesNumbersBetween1And45() {
        NumberGenerator generator = new NumberGenerator();

        NumberTicket ticket = generator.run(new HashSet<>());

        for (NumberValue number : ticket.getNumbers()) {
            assertTrue(number.getValue() >= 1);
            assertTrue(number.getValue() <= 45);
        }
    }

    @Test
    @DisplayName("여러 번 실행해도 항상 유효한 티켓을 생성")
    void generatorAlwaysCreatesValidTickets() {
        NumberGenerator generator = new NumberGenerator();

        for (int i = 0; i < 100; i++) {
            NumberTicket ticket = generator.run(new HashSet<>());

            assertEquals(6, ticket.getNumbers().size());

            for (NumberValue number : ticket.getNumbers()) {
                assertTrue(number.getValue() >= 1);
                assertTrue(number.getValue() <= 45);
            }
        }
    }
}
