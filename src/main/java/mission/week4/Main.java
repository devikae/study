package mission.week4;

import mission.week1.NumberTicket;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Main {
    public static void main(String[] args) {

        final int MAKE_TICKET_COUNT = 10_000_000;

        TaskFactory taskFactory = new TaskFactory();
        PerformanceMeasurer performanceMeasurer = new PerformanceMeasurer();
        ResultValidator resultValidator = new ResultValidator();
        SingleThreadRunner singleThreadRunner = new SingleThreadRunner();
        MultiThreadRunner multiThreadRunner = new MultiThreadRunner();

        try {
            runSingleThreadExperiment(MAKE_TICKET_COUNT, taskFactory, performanceMeasurer, resultValidator, singleThreadRunner);
            runUnsafeSharedListExperiment(MAKE_TICKET_COUNT, taskFactory, performanceMeasurer, resultValidator, multiThreadRunner);
            runSynchronizedSharedListExperiment(MAKE_TICKET_COUNT, taskFactory, performanceMeasurer, resultValidator, multiThreadRunner);
            runAvoidSharedStateExperiment(MAKE_TICKET_COUNT, taskFactory, performanceMeasurer, resultValidator, multiThreadRunner);
        } finally {
            SingleThreadRunner.shutdown();
            MultiThreadRunner.shutdown();
        }
    }

    private static void runSingleThreadExperiment(
            int ticketCount,
            TaskFactory taskFactory,
            PerformanceMeasurer performanceMeasurer,
            ResultValidator resultValidator,
            SingleThreadRunner singleThreadRunner
    ) {
        List<NumberTicket> tickets = new ArrayList<>(ticketCount);
        List<Runnable> makeTicketTask = new ArrayList<>();
        makeTicketTask.add(taskFactory.makeTicketTask(tickets, ticketCount));

        ExecutionDuration duration = performanceMeasurer.measure(() -> singleThreadRunner.execute(makeTicketTask));
        boolean isValid = resultValidator.isValid(tickets, ticketCount);

        PerformanceResult performanceResult = new PerformanceResult(
                "single thread",
                duration,
                ticketCount,
                tickets.size(),
                isValid,
                singleThreadRunner
        );

        System.out.println(performanceResult.getResult());
    }

    private static void runUnsafeSharedListExperiment(
            int ticketCount,
            TaskFactory taskFactory,
            PerformanceMeasurer performanceMeasurer,
            ResultValidator resultValidator,
            MultiThreadRunner multiThreadRunner
    ) {

        int taskCount = multiThreadRunner.getRecommendedTaskCount(ticketCount);
        List<NumberTicket> sharedTickets = new ArrayList<>(ticketCount);
        List<Runnable> sharedTicketTasks = new ArrayList<>(taskCount);

        for (int i = 0; i < taskCount; i++) {
            sharedTicketTasks.add(taskFactory.makeTicketTask(sharedTickets, workCountFor(i, taskCount, ticketCount)));
        }

        ExecutionDuration sharedDuration = performanceMeasurer.measure(() -> multiThreadRunner.execute(sharedTicketTasks));
        boolean sharedIsValid = resultValidator.isValid(sharedTickets, ticketCount);

        PerformanceResult sharedResult = new PerformanceResult(
                "multi thread not control concurrency",
                sharedDuration,
                ticketCount,
                sharedTickets.size(),
                sharedIsValid,
                multiThreadRunner
        );

        System.out.println(sharedResult.getResult());
    }

    private static void runSynchronizedSharedListExperiment(
            int ticketCount,
            TaskFactory taskFactory,
            PerformanceMeasurer performanceMeasurer,
            ResultValidator resultValidator,
            MultiThreadRunner multiThreadRunner
    ) {
        int taskCount = multiThreadRunner.getRecommendedTaskCount(ticketCount);
        List<NumberTicket> synchronizedAddTickets = new ArrayList<>(ticketCount);
        List<Runnable> synchronizedTicketTasks = new ArrayList<>(taskCount);

        for (int i = 0; i < taskCount; i++) {
            synchronizedTicketTasks.add(taskFactory.makeSynchronizedTicketTask(synchronizedAddTickets, workCountFor(i, taskCount, ticketCount)));
        }

        ExecutionDuration synchronizedAddDuration = performanceMeasurer.measure(() -> multiThreadRunner.execute(synchronizedTicketTasks));
        boolean synchronizedAddIsValid = resultValidator.isValid(synchronizedAddTickets, ticketCount);

        PerformanceResult synchronizedAddResult = new PerformanceResult(
                "synchronized block control concurrency",
                synchronizedAddDuration,
                ticketCount,
                synchronizedAddTickets.size(),
                synchronizedAddIsValid,
                multiThreadRunner
        );

        System.out.println(synchronizedAddResult.getResult());
    }

    private static void runAvoidSharedStateExperiment(
            int ticketCount,
            TaskFactory taskFactory,
            PerformanceMeasurer performanceMeasurer,
            ResultValidator resultValidator,
            MultiThreadRunner multiThreadRunner
    ) {
        int taskCount = multiThreadRunner.getRecommendedTaskCount(ticketCount);
        List<NumberTicket> avoidTickets = new ArrayList<>(ticketCount);
        List<Callable<List<NumberTicket>>> avoidTicketTasks = new ArrayList<>(taskCount);

        for (int i = 0; i < taskCount; i++) {
            avoidTicketTasks.add(taskFactory.makeTicketCallable(workCountFor(i, taskCount, ticketCount)));
        }

        ExecutionDuration avoidDuration = performanceMeasurer.measure(() -> avoidTickets.addAll(
                multiThreadRunner.executeAndCollect(avoidTicketTasks)
        ));
        boolean avoidIsValid = resultValidator.isValid(avoidTickets, ticketCount);

        PerformanceResult avoidResult = new PerformanceResult(
                "multi thread avoid shared state",
                avoidDuration,
                ticketCount,
                avoidTickets.size(),
                avoidIsValid,
                multiThreadRunner
        );

        System.out.println(avoidResult.getResult());
    }

    // 총 작업 수가 스레드 수로 나누어떨어지지 않아도 누락 없이 분배
    private static int workCountFor(int taskIndex, int taskCount, int totalWorkCount) {
        int baseCount = totalWorkCount / taskCount;
        int remainder = totalWorkCount % taskCount;

        if (taskIndex < remainder) {
            return baseCount + 1;
        }

        return baseCount;
    }
}
