package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 단순 숫자로 결과를 반환할 것인가
// 결과도 의미 있는 객체로 만들 것인가
// 테스트하기 쉬운 구조인가
public class MatchResult {

    private final NumberTicket winningNumber;
    private NumberValue bonusNumber = null;

    private final int FIRST = 6;
    private final int SECOND_THIRD = 5;
    private final int FOURTH = 4;
    private final int FIFTH = 3;


    public MatchResult() {
        NumberGenerator generator = new NumberGenerator();

        this.winningNumber = generator.run(new HashSet<>());

        Set<NumberValue> winningNumbers = winningNumber.getNumbers();
        this.bonusNumber = winningNumbers.stream().findAny().get();

    }

    MatchResult(NumberTicket winningNumber, NumberValue bonusNumber) {
        this.winningNumber = winningNumber;
        this.bonusNumber = bonusNumber;
    }

    public void numberMatch(List<NumberTicket> tickets){

        for(int i = 0; i<tickets.size(); i++){
            NumberTicket myTicket = tickets.get(i);
            Set<NumberValue> matchSet = new HashSet<>(myTicket.getNumbers());

            boolean haveBonus = matchSet.contains(bonusNumber);

            matchSet.retainAll(winningNumber.getNumbers());

            int matchCount = matchSet.size();
            String result = "낙첨";

            if(matchCount == FIRST){
                result = "1등";
            }else if(matchCount == SECOND_THIRD){
                result = haveBonus ? "2등" : "3등";
            }else if(matchCount == FOURTH){
                result = "4등";
            }else if(matchCount == FIFTH){
                result = "5등";
            }

            System.out.println(i+1 + "번 : " +result);

        }

    }

    public void getWinningNumber(){
        System.out.println("============당첨 번호==============");
        System.out.println("⭐Bonus number: " + bonusNumber.getValue() + "⭐");

        for(NumberValue num : winningNumber.getNumbers()){
            System.out.print(num.getValue() + " ");
        }

        System.out.println("\n=================================");
    }

}
