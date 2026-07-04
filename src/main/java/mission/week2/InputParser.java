package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberTicket;
import mission.week1.NumberValue;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class InputParser {

    public NumberTicket parseTicket(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("입력값은 비어 있을 수 없습니다.");
        }

        Set<NumberValue> numbers = parseNumbers(input);

        if (numbers.size() != NumberGenerator.SIZE) {
            throw new IllegalArgumentException(NumberGenerator.SIZE + "개의 번호를 입력해야 합니다.");
        }

        return new NumberTicket(numbers);
    }

    private Set<NumberValue> parseNumbers(String input) {
        Set<NumberValue> numbers = new HashSet<>();
        StringTokenizer tokenizer = new StringTokenizer(input.trim().replace(",", " "));

        while (tokenizer.hasMoreTokens()) {
            if (numbers.size() >= NumberGenerator.SIZE) {
                throw new IllegalArgumentException(NumberGenerator.SIZE + "개를 초과해서 입력할 수 없습니다.");
            }

            int number = parseNumber(tokenizer.nextToken());
            NumberValue numberValue = NumberValue.getInstance(number);

            if (!numbers.add(numberValue)) {
                throw new IllegalArgumentException("중복된 값 [" + number + "]이(가) 포함되어 있습니다.");
            }
        }

        return numbers;
    }

    private int parseNumber(String token) {
        try {
            return Integer.parseInt(token);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력할 수 있습니다: " + token);
        }
    }
}
