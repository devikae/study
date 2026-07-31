package mission.week4;

import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiThreadRunner implements ThreadRunner {
    private static final int WORKER_COUNT = Math.max(1, Runtime.getRuntime().availableProcessors());

    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(
            WORKER_COUNT,
            runnable -> {
                Thread thread = new Thread(runnable, "multi-worker");
                thread.setDaemon(true);
                return thread;
            }
    );

    @Override
    public void execute(List<Runnable> tasks) {
        validateTasks(tasks);

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        try {
            for (Runnable task : tasks) {
                futures.add(CompletableFuture.runAsync(task, EXECUTOR_SERVICE));
            }

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        } catch (CompletionException e) {
            throw new IllegalStateException("작업 실행 중 예외가 발생했습니다.", e.getCause());
        }
    }

    @Override
    public String getName() {
        return "Multi Thread";
    }

    public List<NumberTicket> executeAndCollect(List<Callable<List<NumberTicket>>> tasks) {
        validateTasks(tasks);

        List<NumberTicket> assembledTickets = new ArrayList<>();
        List<Future<List<NumberTicket>>> futures = new ArrayList<>();

        try {
            for (Callable<List<NumberTicket>> task : tasks) {
                futures.add(EXECUTOR_SERVICE.submit(task));
            }

            for (Future<List<NumberTicket>> future : futures) {
                List<NumberTicket> tickets = future.get();
                assembledTickets.addAll(tickets);
            }
        } catch (ExecutionException e) {
            throw new IllegalStateException("작업 결과 수집 중 예외가 발생했습니다.", e.getCause());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("작업 결과 대기 중 인터럽트가 발생했습니다.", e);
        }

        return assembledTickets;
    }

    public int getRecommendedTaskCount(int totalWorkCount) {
        if (totalWorkCount <= 0) {
            return 1;
        }

        return Math.min(WORKER_COUNT, totalWorkCount);
    }

    public static void shutdown() {
        EXECUTOR_SERVICE.shutdown();
    }
}
