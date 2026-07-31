package mission.week4;

import mission.week1.NumberTicket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ResultValidatorTest {

    @Test
    @DisplayName("isValid는 실제 개수와 기대 개수가 같으면 true를 반환한다.")
    void isValidReturnsTrueWhenActualCountEqualsExpectedCount() {
        ResultValidator validator = new ResultValidator();
        List<NumberTicket> tickets = Collections.nCopies(3, null);

        boolean result = validator.isValid(tickets, 3);

        assertTrue(result);
    }

    @Test
    @DisplayName("isValid는 실제 개수가 기대 개수보다 작으면 false를 반환한다.")
    void isValidReturnsFalseWhenActualCountIsLessThanExpectedCount() {
        ResultValidator validator = new ResultValidator();
        List<NumberTicket> tickets = Collections.nCopies(2, null);

        boolean result = validator.isValid(tickets, 3);

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid는 실제 개수가 기대 개수보다 크면 false를 반환한다.")
    void isValidReturnsFalseWhenActualCountIsGreaterThanExpectedCount() {
        ResultValidator validator = new ResultValidator();
        List<NumberTicket> tickets = Collections.nCopies(4, null);

        boolean result = validator.isValid(tickets, 3);

        assertFalse(result);
    }

    @Test
    @DisplayName("isValid는 티켓 목록이 null이면 예외가 발생한다.")
    void isValidThrowsExceptionWhenTicketsIsNull() {
        ResultValidator validator = new ResultValidator();

        assertThrows(IllegalArgumentException.class,
                () -> validator.isValid(null, 3));
    }
}
