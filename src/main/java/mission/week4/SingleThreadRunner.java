package mission.week4;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SingleThreadRunner implements ThreadRunner {

    // 리팩토링: 단일 스레드 실행도 호출마다 ExecutorService를 만들지 않고 공유한다.
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "single-worker");
        thread.setDaemon(true);
        return thread;
    });

    @Override
    public void execute(List<Runnable> tasks) {
        validateTasks(tasks);

        try {
            Future<?> future = EXECUTOR_SERVICE.submit(tasks.get(0));
            future.get();
        } catch (InterruptedException e) {
            // 리팩토링: Future.get()이 interrupt 상태를 지울 수 있으므로 다시 표시한다.
            Thread.currentThread().interrupt();
            throw new IllegalStateException("작업 대기 중 인터럽트가 발생했습니다.", e);
        } catch (ExecutionException e) {
            throw new IllegalStateException("작업 실행 중 예외가 발생했습니다.", e.getCause());
        }
    }

    @Override
    public String getName() {
        return "SingleThread";
    }

    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
    }
}
