package mission.week4;

public class ExecutionDuration {
    private final long nanos;

    private ExecutionDuration(long nanos) {
        if (nanos < 0) {
            throw new IllegalArgumentException("실행 시간은 0 이상이어야 합니다.");
        }
        this.nanos = nanos;
    }

    // 시간 측정값은 double 원시값으로 흘리지 않고 값 클래스로 보관
    public static ExecutionDuration fromNanos(long nanos) {
        return new ExecutionDuration(nanos);
    }

    public double toMillis() {
        return nanos / 1_000_000.0;
    }
}
