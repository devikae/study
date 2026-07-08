package mission.week4;

public class PerformanceMeasurer {

    public double measure(Runnable task) {
        if (task == null) {
            throw new IllegalArgumentException("측정할 작업은 null일 수 없습니다.");
        }

        long startTime = System.nanoTime();

        task.run();

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1_000_000.0;
    }
}
