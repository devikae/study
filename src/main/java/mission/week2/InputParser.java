package mission.week2;

import mission.week1.NumberGenerator;
import mission.week1.NumberValue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Set;
import java.util.StringTokenizer;

// 입력 형식 어디까지 허용할지
// 변환할 수 없는 입력처리
// 파싱 책임과 검증 책임 분리

// 1-1. 어떻게 받을것인가.
// 1-2. 구분자 기준으로 값을 나눈다.
// 1-3. 파싱 진행

// 2. 입력값 파싱
// 2-1. 파싱 불가 예외 처리 (RuntimeException)
// 2-2. 공백 제거

// 3. 검증

// 4. 변환 결과 도메인 객체로 넘김
// 4-1. 어떤 타입으로 어떻게 넘기는가.
public class InputParser {

    public final Set<NumberValue> inputtedValues;

    public InputParser() {
        this.inputtedValues = new HashSet<>();
    }

    public void inputValues() {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in)
        );

        inputtedValues.clear();

        while (inputtedValues.size() < NumberGenerator.SIZE) {
            try {
                String line = br.readLine();
                parseAndTrim(line, inputtedValues);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private int verifyInput(int inputNum) {
        if (NumberValue.MIN_NUM <= inputNum && inputNum <= NumberValue.MAX_NUM){
            return inputNum;
        }else {
            throw new InputMismatchException("1~45 범위에서 벗어난 숫자 입니다.");
        }
    }

    public void parseAndTrim(String input, Set<NumberValue> set ) {
        if (input.isEmpty()) throw new IllegalArgumentException("입력된 값이 없습니다.");

        input = input.trim();

        if (input.contains(",")) input = input.replaceAll("," ," ");

        StringTokenizer st = new StringTokenizer(input);
        int addedCount = 0;

        while (st.hasMoreTokens()) {
            String token = st.nextToken();

            int num = Integer.parseInt(token);

            num = verifyInput(num);

            if (!set.add(NumberValue.getInstance(num))) {
                System.out.println("중복된 값 [" + num + "]이(가) 포함되어 있습니다.");
                continue;
            }

            addedCount++;

            if (set.size() == NumberGenerator.SIZE) {
                break;
            }
        }
    }


}
