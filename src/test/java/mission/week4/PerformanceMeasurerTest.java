package mission.week4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceMeasurerTest {

    @Test
    @DisplayName("measure는 측정할 작업이 null이면 예외가 발생한다.")
    void measureThrowsExceptionWhenTaskIsNull() {
        PerformanceMeasurer measurer = new PerformanceMeasurer();

        assertThrows(IllegalArgumentException.class,
                () -> measurer.measure(null));
    }

    @Test
    @DisplayName("measure는 전달받은 작업을 실행한다.")
    void measureRunsTask() {
        PerformanceMeasurer measurer = new PerformanceMeasurer();
        AtomicInteger count = new AtomicInteger(0);

        measurer.measure(count::incrementAndGet);

        assertEquals(1, count.get());
    }

    @Test
    @DisplayName("measure는 실행 시간을 밀리초 단위로 반환한다.")
    void measureReturnsDurationMillis() {
        PerformanceMeasurer measurer = new PerformanceMeasurer();

        ExecutionDuration duration = measurer.measure(() -> {
        });

        assertTrue(duration.toMillis() >= 0);
    }
}
