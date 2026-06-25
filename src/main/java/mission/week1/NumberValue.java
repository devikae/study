package mission.week1;
// 생성자로 값을 받음
// 허용 범위를 벗어나면 예외
// 값을 외부에서 변경X
// 같은 값이면 같은 객체로 판단해야함.

public class NumberValue implements Comparable<NumberValue> {
    public static final int MIN_NUM = 1;
    public static final int MAX_NUM = 45;

    private final int value;

//    private static final NumberValue[] NUMS = new NumberValue[46];
    private static final NumberValue[] NUMS = new NumberValue[MAX_NUM + 1];

    static {
        for (int i = MIN_NUM; i <= MAX_NUM; i++) {
            NUMS[i] = new NumberValue(i);
        }
    }

    private NumberValue(int num) {
        this.value = num;
    }

    public static NumberValue getInstance(int num) {
        if (num < MIN_NUM || num > MAX_NUM) {
            throw new IllegalArgumentException(MIN_NUM + "~" + MAX_NUM + " 범위의 숫자만 입력할 수 있습니다.");
        }
        return NUMS[num];
    }

    public int getValue() {
        return value;
    }

    @Override
    public int compareTo(NumberValue other) {
        return Integer.compare(this.value, other.value);
    }
}
