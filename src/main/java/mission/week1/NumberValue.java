package mission.week1;
// 생성자로 값을 받음
// 허용 범위를 벗어나면 예외
// 값을 외부에서 변경X
// 같은 값이면 같은 객체로 판단해야함.

public class NumberValue implements Comparable<NumberValue> {
    private static final int MIN_NUM = 1;
    private static final int MAX_NUM = 45;

    private final int value;

    private static final NumberValue[] NUMS = new NumberValue[46];

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
            throw new IllegalArgumentException();
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

