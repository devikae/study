package mission.week4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExecutionDurationTest {

    @Test
    @DisplayName("fromNanos는 나노초 값을 밀리초로 변환해 반환한다.")
    void fromNanosConvertsNanosToMillis() {
        ExecutionDuration duration = ExecutionDuration.fromNanos(12_345_000);

        assertEquals(12.345, duration.toMillis());
    }

    @Test
    @DisplayName("fromNanos는 0 나노초를 허용한다.")
    void fromNanosAllowsZero() {
        ExecutionDuration duration = ExecutionDuration.fromNanos(0);

        assertEquals(0.0, duration.toMillis());
    }

    @Test
    @DisplayName("fromNanos는 음수 나노초를 허용하지 않는다.")
    void fromNanosThrowsExceptionWhenNanosIsNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> ExecutionDuration.fromNanos(-1));
    }
}
