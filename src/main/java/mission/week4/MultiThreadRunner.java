package mission.week4;

import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MultiThreadRunner implements ThreadRunner {

    @Override
    public void execute(List<Runnable> tasks) {

        validateTasks(tasks);

        ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());

        List<Future<?>> futures = new ArrayList<>();

        try {
            for (Runnable task : tasks) {
                futures.add(executorService.submit(task));
            }

            for (Future<?> future : futures) {
                future.get();
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }

    @Override
    public String getName() {
        return "Multi Thread";
    }

    public List<NumberTicket> executeAndCollect(List<Callable<List<NumberTicket>>> tasks) {

        validateTasks(tasks);

        ExecutorService executorService = Executors.newFixedThreadPool(tasks.size());

        List<NumberTicket> assembledTickets = new ArrayList<>();
        List<Future<List<NumberTicket>>> futures = new ArrayList<>();

        try {
            for (Callable<List<NumberTicket>> task : tasks) {
                futures.add(executorService.submit(task));
            }

            for (Future<List<NumberTicket>> future : futures) {
                List<NumberTicket> tickets = future.get();
                assembledTickets.addAll(tickets);
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }

        return assembledTickets;
    }
}
