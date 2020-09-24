package forkjoin;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

import static java.util.stream.Collectors.toList;

public class MainApp {

    public static void main(String[] args) {
        Random random = new Random();

        List<Long> data = random
                .longs(10, 1, 5)
                .boxed()
                .collect(toList());

        ForkJoinPool pool = new ForkJoinPool();

        //task that doesn't return result
        SumAction recursiveAction = new SumAction(data);
        pool.invoke(recursiveAction);

        //task that returns result
        SumTask recursiveTask = new SumTask(data);
        Long totalSum = pool.invoke(recursiveTask);
        System.out.println("Total sum: " + totalSum);
    }
}
