package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LottoMatcher {

    private final NumberTicket winningNumber;
    private final NumberValue bonusNumber;

    public LottoMatcher(NumberTicket winningNumber, NumberValue bonusNumber) {
        this.winningNumber = winningNumber;
        this.bonusNumber = bonusNumber;
    }

    public List<MatchResult> matchAll(List<NumberTicket> tickets) {
        List<MatchResult> results = new ArrayList<>();

        for (int i = 0; i < tickets.size(); i++) {
            results.add(match(i + 1, tickets.get(i)));
        }

        return results;
    }

    private MatchResult match(int ticketNumber, NumberTicket ticket) {
        Set<NumberValue> matchSet = new HashSet<>(ticket.getNumbers());
        boolean hasBonus = matchSet.contains(bonusNumber);

        matchSet.retainAll(winningNumber.getNumbers());

        MatchResult.Rank rank = MatchResult.Rank.from(matchSet.size(), hasBonus);
        return new MatchResult(ticketNumber, rank);
    }
}
