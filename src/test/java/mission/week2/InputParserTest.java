package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputParserTest {

    @Test
    @DisplayName("null 입력이면 예외가 발생한다")
    void nullInputThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket(null));
    }

    @Test
    @DisplayName("빈 입력이면 예외가 발생한다")
    void blankInputThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("   "));
    }

    @Test
    @DisplayName("0은 로또 번호 범위가 아니므로 예외가 발생한다")
    void zeroThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("0 1 2 3 4 5"));
    }

    @Test
    @DisplayName("-1은 로또 번호 범위가 아니므로 예외가 발생한다")
    void minusOneThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("-1 1 2 3 4 5"));
    }

    @Test
    @DisplayName("중복된 숫자가 있으면 예외가 발생한다")
    void duplicatedNumberThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("1 1 2 3 4 5"));
    }

    @Test
    @DisplayName("숫자가 6개보다 적으면 예외가 발생한다")
    void lessThanSixNumbersThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("1 2 3 4 5"));
    }

    @Test
    @DisplayName("숫자가 6개보다 많으면 예외가 발생한다")
    void moreThanSixNumbersThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("1 2 3 4 5 6 7"));
    }

    @Test
    @DisplayName("숫자가 아닌 값이 있으면 예외가 발생한다")
    void nonNumberThrowsException() {
        InputParser inputParser = new InputParser();

        assertThrows(IllegalArgumentException.class,
                () -> inputParser.parseTicket("1 2 3 4 5 a"));
    }

    @Test
    @DisplayName("공백으로 구분된 6개 숫자를 티켓으로 변환한다")
    void parseSpaceSeparatedNumbers() {
        InputParser inputParser = new InputParser();

        NumberTicket ticket = inputParser.parseTicket("1 2 3 4 5 6");

        assertEquals(6, ticket.getNumbers().size());
        assertTrue(ticket.getNumbers().contains(NumberValue.getInstance(1)));
        assertTrue(ticket.getNumbers().contains(NumberValue.getInstance(6)));
    }

    @Test
    @DisplayName("쉼표로 구분된 6개 숫자를 티켓으로 변환한다")
    void parseCommaSeparatedNumbers() {
        InputParser inputParser = new InputParser();

        NumberTicket ticket = inputParser.parseTicket("1,2,3,4,5,6");

        assertEquals(6, ticket.getNumbers().size());
        assertTrue(ticket.getNumbers().contains(NumberValue.getInstance(1)));
        assertTrue(ticket.getNumbers().contains(NumberValue.getInstance(6)));
    }
}
