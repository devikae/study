package mission.week4;

public class PerformanceMeasurer {

    public double measure(ThreadRunner threadRunner, Runnable task) {
        long startTime = System.nanoTime();

        threadRunner.execute(task);

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }

}
