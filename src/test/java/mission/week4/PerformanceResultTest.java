package mission.week4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerformanceResultTest {

    @Test
    @DisplayName("생성자로 받은 측정 결과 값을 보관한다.")
    void storesPerformanceResultValues() {
        ThreadRunner runner = new TestThreadRunner("Test Runner");

        PerformanceResult result = new PerformanceResult(
                "test experiment",
                ExecutionDuration.fromNanos(12_345_000),
                1000,
                900,
                false,
                runner
        );

        assertEquals(12.345, result.getDuration());
        assertEquals(1000, result.getExpectedCount());
        assertEquals(900, result.getActualCount());
        assertFalse(result.isSuccess());
        assertEquals("Test Runner", result.getThreadName());
    }

    @Test
    @DisplayName("getResult는 출력 문자열에 실험명과 측정 값을 포함한다.")
    void getResultContainsPerformanceResultValues() {
        ThreadRunner runner = new TestThreadRunner("Test Runner");

        PerformanceResult result = new PerformanceResult(
                "test experiment",
                ExecutionDuration.fromNanos(12_345_000),
                1000,
                900,
                true,
                runner
        );

        String output = result.getResult();

        assertTrue(output.contains("test experiment"));
        assertTrue(output.contains("Test Runner"));
        assertTrue(output.contains("12.345000"));
        assertTrue(output.contains("1,000"));
        assertTrue(output.contains("900"));
    }

    private static class TestThreadRunner implements ThreadRunner {
        private final String name;

        private TestThreadRunner(String name) {
            this.name = name;
        }

        @Override
        public void execute(List<Runnable> task) {
        }

        @Override
        public String getName() {
            return name;
        }
    }
}
