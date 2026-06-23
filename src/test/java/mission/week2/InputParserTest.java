package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputParserTest {

    @Test
    @DisplayName("null 입력")
    void nullInputThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(NullPointerException.class,
                () -> inputParser.parseAndTrim(null, new HashSet<>()));
    }

    @Test
    @DisplayName("0")
    void zeroThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(InputMismatchException.class,
                () -> inputParser.parseAndTrim("0", new HashSet<>()));
    }

    @Test
    @DisplayName("-1")
    void minusOneThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(InputMismatchException.class,
                () -> inputParser.parseAndTrim("-1", new HashSet<>()));
    }

    @Test
    @DisplayName("중복된 숫자")
    void duplicatedNumberIsIgnored() {
        InputParser inputParser = new InputParser();
        Set<NumberValue> numbers = new HashSet<>();

        inputParser.parseAndTrim("1 1", numbers);

        assertEquals(1, numbers.size());
        assertTrue(numbers.contains(NumberValue.getInstance(1)));
    }

    @Test
    @DisplayName("가장 큰 양수")
    void maxPositiveNumberThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(InputMismatchException.class,
                () -> inputParser.parseAndTrim(String.valueOf(Integer.MAX_VALUE), new HashSet<>()));
    }

    @Test
    @DisplayName("가장 작은 음수")
    void minNegativeNumberThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(InputMismatchException.class,
                () -> inputParser.parseAndTrim(String.valueOf(Integer.MIN_VALUE), new HashSet<>()));
    }
}
