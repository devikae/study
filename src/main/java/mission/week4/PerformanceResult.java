package mission.week4;

public class PerformanceResult {
    private final double durationMillis;
    private final int expectedCount;
    private final int actualCount;
    private final boolean isSuccess;
    private final String threadName;

    public PerformanceResult(double duration, int expectedCount, int actualCount, boolean success, ThreadRunner threadRunner) {
        this.durationMillis = duration;
        this.expectedCount = expectedCount;
        this.actualCount = actualCount;
        this.isSuccess = success;
        this.threadName = threadRunner.getName();
    }

    public double getDuration() {
        return durationMillis;
    }

    public int getExpectedCount() {
        return expectedCount;
    }

    public int getActualCount() {
        return actualCount;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getResult() {
        return """
            ==============================
            %s 성능 측정 결과
            ==============================
            실행 시간 : %.6f ms
            기대 개수 : %,d
            실제 개수 : %,d
            성공 여부 : %s
            ==============================
            """.formatted(
                threadName,
                durationMillis,
                expectedCount,
                actualCount,
                isSuccess ? "성공" : "실패"
        );
    }

}
