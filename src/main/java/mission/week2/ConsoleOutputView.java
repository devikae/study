package mission.week2;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.List;

public class ConsoleOutputView {

    public void printTickets(List<NumberTicket> tickets) {
        System.out.println("=============my lotto=============");
        for (NumberTicket ticket : tickets) {
            for (NumberValue lotto : ticket.getNumbers()) {
                System.out.print(lotto.getValue() + " ");
            }
            System.out.println("\n----------------------------------");
        }
        System.out.println("==================================");
    }

    public void printWinningNumbers(NumberTicket winningNumber, NumberValue bonusNumber) {
        System.out.println("============당첨 번호==============");
        System.out.println("⭐Bonus number: " + bonusNumber.getValue() + "⭐");

        for (NumberValue num : winningNumber.getNumbers()) {
            System.out.print(num.getValue() + " ");
        }

        System.out.println("\n=================================");
    }

    public void printMatchResults(List<MatchResult> results) {
        for (MatchResult result : results) {
            System.out.println(result.getTicketNumber() + "번 : " + result.getRank().getDisplayName());
        }
    }
}
