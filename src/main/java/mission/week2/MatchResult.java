package mission.week2;

public class MatchResult {

    private final int ticketNumber;
    private final Rank rank;

    public MatchResult(int ticketNumber, Rank rank) {
        this.ticketNumber = ticketNumber;
        this.rank = rank;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public Rank getRank() {
        return rank;
    }

    public enum Rank {
        FIRST("1등"),
        SECOND("2등"),
        THIRD("3등"),
        FOURTH("4등"),
        FIFTH("5등"),
        NONE("낙첨");

        private static final int FIRST_MATCH_COUNT = 6;
        private static final int SECOND_OR_THIRD_MATCH_COUNT = 5;
        private static final int FOURTH_MATCH_COUNT = 4;
        private static final int FIFTH_MATCH_COUNT = 3;

        private final String displayName;

        Rank(String displayName) {
            this.displayName = displayName;
        }

        public static Rank from(int matchCount, boolean hasBonus) {
            if (matchCount == FIRST_MATCH_COUNT) {
                return FIRST;
            }
            if (matchCount == SECOND_OR_THIRD_MATCH_COUNT) {
                return hasBonus ? SECOND : THIRD;
            }
            if (matchCount == FOURTH_MATCH_COUNT) {
                return FOURTH;
            }
            if (matchCount == FIFTH_MATCH_COUNT) {
                return FIFTH;
            }
            return NONE;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
