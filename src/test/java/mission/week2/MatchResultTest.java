package mission.week2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MatchResultTest {

    @Test
    @DisplayName("MatchResult는 티켓 번호와 등수를 가진다")
    void matchResultHasTicketNumberAndRank() {
        MatchResult result = new MatchResult(1, MatchResult.Rank.FIRST);

        assertEquals(1, result.getTicketNumber());
        assertEquals(MatchResult.Rank.FIRST, result.getRank());
    }

    @Test
    @DisplayName("6개 번호가 일치하면 1등이다")
    void sixMatchesIsFirstRank() {
        assertEquals(MatchResult.Rank.FIRST, MatchResult.Rank.from(6, false));
    }

    @Test
    @DisplayName("5개 번호와 보너스 번호가 일치하면 2등이다")
    void fiveMatchesWithBonusIsSecondRank() {
        assertEquals(MatchResult.Rank.SECOND, MatchResult.Rank.from(5, true));
    }

    @Test
    @DisplayName("5개 번호만 일치하면 3등이다")
    void fiveMatchesWithoutBonusIsThirdRank() {
        assertEquals(MatchResult.Rank.THIRD, MatchResult.Rank.from(5, false));
    }

    @Test
    @DisplayName("4개 번호가 일치하면 4등이다")
    void fourMatchesIsFourthRank() {
        assertEquals(MatchResult.Rank.FOURTH, MatchResult.Rank.from(4, false));
    }

    @Test
    @DisplayName("3개 번호가 일치하면 5등이다")
    void threeMatchesIsFifthRank() {
        assertEquals(MatchResult.Rank.FIFTH, MatchResult.Rank.from(3, false));
    }

    @Test
    @DisplayName("2개 이하 번호가 일치하면 등수가 없다")
    void twoOrLessMatchesHasNoRank() {
        assertEquals(MatchResult.Rank.NONE, MatchResult.Rank.from(2, false));
        assertEquals(MatchResult.Rank.NONE, MatchResult.Rank.from(1, false));
        assertEquals(MatchResult.Rank.NONE, MatchResult.Rank.from(0, false));
    }
}
