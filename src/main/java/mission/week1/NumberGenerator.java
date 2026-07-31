package mission.week1;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// 유효한 값 6개를 생성
// 중복 값 나오지 않도록
// 생성 결과 NumberTicket로 반환

public class NumberGenerator {

    public static final int SIZE = 6;

    public NumberTicket makeTicket(Set<NumberValue> lotto){

        while (lotto.size() < SIZE){
            lotto.add(NumberValue.getInstance((int) (Math.random() * 45) + 1));
        }

        return new NumberTicket(lotto);
    }

    public List<NumberTicket> makeTickets(int makeTicketCount){

        if (makeTicketCount < 0) {
            throw new IllegalArgumentException("생성할 티켓 개수는 0 이상이어야 합니다.");
        }

        List<NumberTicket> tickets = new ArrayList<>(makeTicketCount);

        for(int i = 1; i <= makeTicketCount; i++){
            NumberTicket ticket = makeTicket(new HashSet<>());
            tickets.add(ticket);
        }

        return tickets;
    }

}
