package my.learning.advanced.eight;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by tdongsi on 12/27/17.
 */
public class ConcurrentProcessingDemos {
    private List<Integer> numbers = Arrays.asList(3,1,4,1,5,9,2,6,5,3,5,8,9,7,9,3,2,3,8,4,6,2,6,4,3,3);

    private Function<Integer, Integer> doubler = n -> n*2;
    private Function<Integer, Integer> doublerWithSleep = (Integer n) -> {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // System.out.println(Thread.currentThread().getName());
        return n*2;
    };

    public void printNums(Function<Integer, Integer> functor) {
        System.out.println(numbers.stream()
                .map(functor)
                .collect(Collectors.toList())
        );
    }

    public void printNumsParallel(Function<Integer, Integer> functor) {
        System.out.println(numbers.parallelStream()
                .map(functor)
                .collect(Collectors.toList())
        );
    }

    public static void main(String args[]) {
        ConcurrentProcessingDemos demo = new ConcurrentProcessingDemos();

        long start = System.nanoTime();
        demo.printNums(demo.doubler);
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end-start)/1e9);

        start = System.nanoTime();
        demo.printNums(demo.doublerWithSleep);
        end = System.nanoTime();
        System.out.println("Time taken: " + (end-start)/1e9);

        start = System.nanoTime();
        demo.printNumsParallel(demo.doublerWithSleep);
        end = System.nanoTime();
        System.out.println("Time taken: " + (end-start)/1e9);
    }
}
