package mission.week4;

public interface ThreadRunner {
    void execute(Runnable task);

    String getName();
}
