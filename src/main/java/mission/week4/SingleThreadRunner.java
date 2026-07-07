package mission.week4;

import java.util.concurrent.*;


public class SingleThreadRunner implements ThreadRunner {

    @Override
    public void execute(Runnable task) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        try {
            Future<?> future = executorService.submit(task);
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
