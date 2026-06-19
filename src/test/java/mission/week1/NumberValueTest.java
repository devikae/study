package mission.week1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NumberValueTest {
    @Test
    @DisplayName("0")
    void zeroThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> NumberValue.getInstance(0));
    }

    @Test
    @DisplayName("최대값 초과 / 46 ")
    void overMaxNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberValue.getInstance(46));
    }

    @Test
    @DisplayName("음수")
    void minusNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberValue.getInstance(-1));
    }

    @Test
    @DisplayName("가장 큰 양수")
    void maxNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberValue.getInstance(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("가장 큰 음수")
    void maxMinusNumberThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NumberValue.getInstance(Integer.MAX_VALUE + 1));
    }

    @Test
    @DisplayName("같은 숫자는 같은 NumberValue 객체를 반환한다")
    void sameNumberReturnsSameInstance() {
        NumberValue first = NumberValue.getInstance(10);
        NumberValue second = NumberValue.getInstance(10);

        assertSame(first, second);
    }

    @Test
    @DisplayName("다른 숫자는 다른 NumberValue 객체를 반환한다")
    void differentNumberReturnsDifferentInstance() {
        NumberValue first = NumberValue.getInstance(10);
        NumberValue second = NumberValue.getInstance(11);

        assertNotSame(first, second);
    }
}
