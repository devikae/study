package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LottoMatcherTest {

    @Test
    @DisplayName("여러 티켓을 순서대로 비교하고 티켓 번호와 등수를 반환한다")
    void matchAllReturnsTicketNumbersAndRanks() {
        LottoMatcher matcher = new LottoMatcher(
                ticket(1, 2, 3, 4, 5, 6),
                NumberValue.getInstance(7)
        );

        List<MatchResult> results = matcher.matchAll(List.of(
                ticket(1, 2, 3, 4, 5, 6),
                ticket(1, 2, 3, 4, 5, 7),
                ticket(1, 2, 3, 8, 9, 10)
        ));

        assertEquals(1, results.get(0).getTicketNumber());
        assertEquals(MatchResult.Rank.FIRST, results.get(0).getRank());
        assertEquals(2, results.get(1).getTicketNumber());
        assertEquals(MatchResult.Rank.SECOND, results.get(1).getRank());
        assertEquals(3, results.get(2).getTicketNumber());
        assertEquals(MatchResult.Rank.FIFTH, results.get(2).getRank());
    }

    private NumberTicket ticket(int first, int second, int third, int fourth, int fifth, int sixth) {
        return new NumberTicket(Set.of(
                NumberValue.getInstance(first),
                NumberValue.getInstance(second),
                NumberValue.getInstance(third),
                NumberValue.getInstance(fourth),
                NumberValue.getInstance(fifth),
                NumberValue.getInstance(sixth)
        ));
    }
}
