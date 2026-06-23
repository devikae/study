package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MatchResultTest {

    @Test
    @DisplayName("6개 번호가 모두 일치하면 1등이다")
    void sixMatchesIsFirstRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(1, 2, 3, 4, 5, 6));

        assertTrue(result.startsWith("1"));
    }

    @Test
    @DisplayName("5개 번호와 보너스 번호가 일치하면 2등이다")
    void fiveMatchesWithBonusIsSecondRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(1, 2, 3, 4, 5, 7));

        assertTrue(result.startsWith("2"));
    }

    @Test
    @DisplayName("5개 번호만 일치하면 3등이다")
    void fiveMatchesWithoutBonusIsThirdRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(2, 3, 4, 5, 6, 7));

        assertTrue(result.startsWith("3"));
    }

    @Test
    @DisplayName("4개 번호가 일치하면 4등이다")
    void fourMatchesIsFourthRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(1, 2, 3, 4, 7, 8));

        assertTrue(result.startsWith("4"));
    }

    @Test
    @DisplayName("3개 번호가 일치하면 5등이다")
    void threeMatchesIsFifthRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(1, 2, 3, 7, 8, 9));

        assertTrue(result.startsWith("5"));
    }

    @Test
    @DisplayName("2개 이하 번호가 일치하면 당첨 등수가 없다")
    void twoOrLessMatchesHasNoRank() {
        MatchResult matchResult = fixedMatchResult();

        String result = matchResultOf(matchResult, ticket(1, 2, 7, 8, 9, 10));

        assertFalse(result.startsWith("1"));
        assertFalse(result.startsWith("2"));
        assertFalse(result.startsWith("3"));
        assertFalse(result.startsWith("4"));
        assertFalse(result.startsWith("5"));
    }

    private MatchResult fixedMatchResult() {
        return new MatchResult(ticket(1, 2, 3, 4, 5, 6), NumberValue.getInstance(1));
    }

    private String matchResultOf(MatchResult matchResult, NumberTicket ticket) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            matchResult.numberMatch(List.of(ticket));
        } finally {
            System.setOut(originalOut);
        }

        String line = output.toString(StandardCharsets.UTF_8).trim();
        return line.split(": ", 2)[1];
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
