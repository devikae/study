package mission.week4;

import mission.week1.NumberTicket;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MultiThreadRunnerTest {

    @Test
    @DisplayName("execute는 작업 목록이 null이면 예외가 발생한다.")
    void executeThrowsExceptionWhenTasksIsNull() {
        MultiThreadRunner runner = new MultiThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.execute(null));
    }

    @Test
    @DisplayName("execute는 작업 목록이 비어 있으면 예외가 발생한다.")
    void executeThrowsExceptionWhenTasksIsEmpty() {
        MultiThreadRunner runner = new MultiThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.execute(new ArrayList<>()));
    }

    @Test
    @DisplayName("execute는 전달받은 모든 작업을 실행한다.")
    void executeRunsAllTasks() {
        MultiThreadRunner runner = new MultiThreadRunner();
        AtomicInteger count = new AtomicInteger(0);
        List<Runnable> tasks = List.of(
                count::incrementAndGet,
                count::incrementAndGet,
                count::incrementAndGet
        );

        runner.execute(tasks);

        assertEquals(3, count.get());
    }

    @Test
    @DisplayName("execute는 작업 실행 중 예외가 발생하면 예외를 다시 던진다.")
    void executeThrowsExceptionWhenTaskFails() {
        MultiThreadRunner runner = new MultiThreadRunner();
        List<Runnable> tasks = List.of(() -> {
            throw new RuntimeException("fail");
        });

        assertThrows(RuntimeException.class,
                () -> runner.execute(tasks));
    }

    @Test
    @DisplayName("executeAndCollect는 작업 목록이 null이면 예외가 발생한다.")
    void executeAndCollectThrowsExceptionWhenTasksIsNull() {
        MultiThreadRunner runner = new MultiThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.executeAndCollect(null));
    }

    @Test
    @DisplayName("executeAndCollect는 작업 목록이 비어 있으면 예외가 발생한다.")
    void executeAndCollectThrowsExceptionWhenTasksIsEmpty() {
        MultiThreadRunner runner = new MultiThreadRunner();

        assertThrows(IllegalArgumentException.class,
                () -> runner.executeAndCollect(new ArrayList<>()));
    }

    @Test
    @DisplayName("executeAndCollect는 Callable 결과들을 하나의 리스트로 병합한다.")
    void executeAndCollectMergesCallableResults() {
        TaskFactory taskFactory = new TaskFactory();
        MultiThreadRunner runner = new MultiThreadRunner();
        List<Callable<List<NumberTicket>>> tasks = List.of(
                taskFactory.makeTicketCallable(3),
                taskFactory.makeTicketCallable(7)
        );

        List<NumberTicket> result = runner.executeAndCollect(tasks);

        assertEquals(10, result.size());
    }
}
