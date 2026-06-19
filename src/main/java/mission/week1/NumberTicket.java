package mission.week1;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class NumberTicket {

    private final Set<NumberValue> numbers;

    public NumberTicket(Set<NumberValue> numbers) {
        if (numbers.size() != NumberGenerator.SIZE) {
            throw new IllegalArgumentException(NumberGenerator.SIZE + "개가 아닙니다.");
        }
        this.numbers = new TreeSet<>(numbers);
    }

    public Set<NumberValue> getNumbers() {
        return Collections.unmodifiableSet(this.numbers);
    }
}
