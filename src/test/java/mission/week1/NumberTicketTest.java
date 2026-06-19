package mission.week1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberTicketTest {
    @Test
    @DisplayName("숫자가 6개면 NumberTicket을 만들 수 있다")
    void createTicketWithSixNumbers() {
        Set<NumberValue> numbers = Set.of(
                NumberValue.getInstance(1),
                NumberValue.getInstance(2),
                NumberValue.getInstance(3),
                NumberValue.getInstance(4),
                NumberValue.getInstance(5),
                NumberValue.getInstance(6)
        );

        NumberTicket ticket = new NumberTicket(numbers);

        assertEquals(6, ticket.getNumbers().size());
    }

    @Test
    @DisplayName("숫자가 5개면 예외가 발생한다")
    void fiveNumbersThrowsException() {
        Set<NumberValue> numbers = Set.of(
                NumberValue.getInstance(1),
                NumberValue.getInstance(2),
                NumberValue.getInstance(3),
                NumberValue.getInstance(4),
                NumberValue.getInstance(5)
        );

        assertThrows(IllegalArgumentException.class,
                () -> new NumberTicket(numbers));
    }


    @Test
    @DisplayName("같은 숫자가 6개면 NumberTicket을 만들 수 없다")
    void createTicketWithSameNumbers() {
        Set<NumberValue> numbers = Set.of(
                NumberValue.getInstance(1),
                NumberValue.getInstance(1),
                NumberValue.getInstance(1),
                NumberValue.getInstance(1),
                NumberValue.getInstance(1),
                NumberValue.getInstance(1)
        );

        NumberTicket ticket = new NumberTicket(numbers);

        assertEquals(6, ticket.getNumbers().size());
    }
}
