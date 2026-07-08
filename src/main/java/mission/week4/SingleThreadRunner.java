package mission.week4;

import java.util.List;
import java.util.concurrent.*;


public class SingleThreadRunner implements ThreadRunner {

    @Override
    public void execute(List<Runnable> tasks) {

        validateTasks(tasks);

        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalArgumentException("실행할 작업은 1개 이상이어야 합니다.");
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Future<?> future = executorService.submit(tasks.get(0));
            future.get();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("작업이 중단되었습니다.", e);

        } catch (Exception e) {
            throw new IllegalStateException("작업 실행 중 예외가 발생했습니다.", e);

        } finally {
            executorService.shutdown();
        }

    }

    @Override
    public String getName() {
        return "SingleThread";
    }

}
