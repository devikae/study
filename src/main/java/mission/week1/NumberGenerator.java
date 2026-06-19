package mission.week1;
import java.util.HashSet;
import java.util.Set;

// 유효한 값 6개를 생성
// 중복 값 나오지 않도록
// 생성 결과 NumberTicket로 반환

public class NumberGenerator {

    public static final int SIZE = 6;

    public NumberTicket run(){

        Set<NumberValue> lotto = new HashSet<>();

        while (lotto.size() < SIZE){
            lotto.add(NumberValue.getInstance((int) (Math.random() * 45) + 1));
        }

        return new NumberTicket(lotto);
    }

}
