package mission.week4;

import java.util.List;

public interface ThreadRunner {
    void execute(List<Runnable> task);

    String getName();

    default void validateTasks(List<?> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalArgumentException("실행할 작업은 1개 이상이어야 합니다.");
        }
    }
}
