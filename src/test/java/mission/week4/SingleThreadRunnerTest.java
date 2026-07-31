package mission.week4;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SingleThreadRunnerTest {

    @Test
    @DisplayName("execute는 작업 목록이 null이면 예외가 발생한다.")
    void executeThrowsExceptionWhenTasksIsNull() {
        SingleThreadRunner runner = new SingleThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.execute(null));
    }

    @Test
    @DisplayName("execute는 작업 목록이 비어 있으면 예외가 발생한다.")
    void executeThrowsExceptionWhenTasksIsEmpty() {
        SingleThreadRunner runner = new SingleThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.execute(new ArrayList<>()));
    }

    @Test
    @DisplayName("execute는 전달받은 작업을 실행한다.")
    void executeRunsTask() {
        SingleThreadRunner runner = new SingleThreadRunner();
        AtomicInteger count = new AtomicInteger(0);
        List<Runnable> tasks = List.of(count::incrementAndGet);

        runner.execute(tasks);

        assertEquals(1, count.get());
    }

    @Test
    @DisplayName("execute는 작업 실행 중 예외가 발생하면 예외를 다시 던진다.")
    void executeThrowsExceptionWhenTaskFails() {
        SingleThreadRunner runner = new SingleThreadRunner();
        List<Runnable> tasks = List.of(() -> {
            throw new RuntimeException("fail");
        });

        assertThrows(IllegalStateException.class,
                () -> runner.execute(tasks));
    }

    @Test
    @DisplayName("execute는 Future 대기 중 인터럽트가 발생하면 인터럽트 상태를 복구한다.")
    void executeRestoresInterruptStatus() {
        SingleThreadRunner runner = new SingleThreadRunner();

        Thread.currentThread().interrupt();

        try {
            assertThrows(IllegalStateException.class,
                    () -> runner.execute(List.of(() -> {
                    })));
            assertEquals(true, Thread.currentThread().isInterrupted());
        } finally {
            Thread.interrupted();
        }
    }
}
