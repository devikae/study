package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        MatchResult matchResult = new MatchResult();
        InputParser inputParser = new InputParser();
        TicketWallet myWallet = new TicketWallet();
        NumberGenerator generator = new NumberGenerator();

        System.out.print("구매할 로또 수량을 입력해 주세요: ");
        Scanner scanner = new Scanner(System.in);
        int defaultBuy = 1;

        try {
            defaultBuy = Integer.parseInt(scanner.nextLine().trim());
            if (defaultBuy <= 0) {
                defaultBuy = 1;
            }
        } catch (Exception e) {
            // 숫자가 아니거나 공백 등 '좆같은 거' 입력하면 예외 터지면서 자연스럽게 1장이 됩니다.
            System.out.println("[알림] 올바르지 않은 입력으로 인해 1장만 구매합니다.");
        }

        System.out.println("로또 번호를 입력 해주세요 (예: 1 2 3 4 5 6 ): ");


        Set<NumberValue> se = new HashSet<>();
        for(int i = 0; i<5; i++) {
            inputParser.inputValues();
            se = inputParser.inputtedValues;
            myWallet.addTicket(generator.run(se));
        }
        System.out.println();

        System.out.println("=============my lotto=============");
        List<NumberTicket> tickets = myWallet.getTiedTickets();
        for(NumberTicket ticket : tickets) {
            for (NumberValue lotto : ticket.getNumbers()) {
                System.out.print(lotto.getValue() + " ");
            }
            System.out.println("\n----------------------------------");
        }
        System.out.println("==================================");

        matchResult.getWinningNumber();
        matchResult.numberMatch(tickets);

    }
}
