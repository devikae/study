package mission.week1;

import mission.week1.NumberTicket;
import mission.week1.NumberValue;
import mission.week1.NumberGenerator;

public class Main {
    public static void main(String[] args) {

        NumberGenerator generator = new NumberGenerator();

        System.out.println("========== 로또 번호 발권 결과 ==========");
        for (int i = 0; i < 5; i++) {
            NumberTicket ticket = generator.run();

            for (NumberValue number : ticket.getNumbers()) {

                System.out.print(number.getValue() + " ");
            }
            System.out.println();
        }

        System.out.println("=======================================");
    }
}