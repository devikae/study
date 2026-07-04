package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        ConsoleInputView consoleInputView = new ConsoleInputView();
        ConsoleOutputView consoleOutputView = new ConsoleOutputView();
        InputParser inputParser = new InputParser();
        TicketWallet myWallet = new TicketWallet();
        NumberGenerator generator = new NumberGenerator();
        NumberTicket winningNumber = generator.run(new HashSet<>());
        NumberValue bonusNumber = generateBonusNumber(winningNumber);
        LottoMatcher lottoMatcher = new LottoMatcher(winningNumber, bonusNumber);

        int purchaseCount = 1;

        try {
            purchaseCount = Integer.parseInt(consoleInputView.inputPurchaseCount().trim());
            if (purchaseCount <= 0) {
                purchaseCount = 1;
            }
        } catch (NumberFormatException e) {
            System.out.println("[알림] 올바르지 않은 입력으로 인해 1장만 구매합니다.");
        }

        for(int i = 0; i < purchaseCount; i++) {
            String input = consoleInputView.inputTicketNumbers();
            while (true) {
                try {
                    myWallet.addTicket(inputParser.parseTicket(input));
                    break;
                } catch (IllegalArgumentException e) {
                    System.out.println("[알림] " + e.getMessage());
                    input = consoleInputView.retryTicketNumbers();
                }
            }
        }
        System.out.println();

        List<NumberTicket> tickets = myWallet.getTiedTickets();
        consoleOutputView.printTickets(tickets);
        consoleOutputView.printWinningNumbers(winningNumber, bonusNumber);
        consoleOutputView.printMatchResults(lottoMatcher.matchAll(tickets));

    }

    private static NumberValue generateBonusNumber(NumberTicket winningNumber) {
        Set<NumberValue> winningNumbers = winningNumber.getNumbers();
        NumberValue bonusNumber;

        do {
            bonusNumber = NumberValue.getInstance((int) (Math.random() * NumberValue.MAX_NUM) + NumberValue.MIN_NUM);
        } while (winningNumbers.contains(bonusNumber));

        return bonusNumber;
    }
}
