package forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class SumTask extends RecursiveTask<Long> {
    private static final int SEQUENTIAL_THRESHOLD = 5;

    private List<Long> data;

    public SumTask(List<Long> data) {
        this.data = data;
    }

    @Override
    protected Long compute() {
        if (data.size() <= SEQUENTIAL_THRESHOLD) { // base case
            long sum = computeSumDirectly();
            System.out.format("Sum of %s: %d\n", data.toString(), sum);
            return sum;
        } else { // recursive case
            // Calculate new range
            int mid = data.size() / 2;
            SumTask firstSubtask =
                    new SumTask(data.subList(0, mid));
            SumTask secondSubtask =
                    new SumTask(data.subList(mid, data.size()));

            // queue the first task
            firstSubtask.fork();

            // Return the sum of all sub tasks
            return secondSubtask.compute()
                    +
                    firstSubtask.join();
        }
    }

    private long computeSumDirectly() {
        long sum = 0;
        for (Long l : data) {
            sum += l;
        }
        return sum;
    }
}
