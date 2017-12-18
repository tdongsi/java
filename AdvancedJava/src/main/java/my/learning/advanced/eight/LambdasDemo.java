package my.learning.advanced.eight;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by tdongsi on 12/17/17.
 */
public class LambdasDemo {

    private static void sessionOne() {
        // Simplest lambda form
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(n -> System.out.println(n));

        // Full lambda form
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach((Integer n) -> {
                    System.out.println(n);
                });

        // Classic equivalent: anonymous inner class
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        System.out.println("Second: " + integer);
                    }
                });

        // Assigning consumer
        Consumer<Integer> printIt = n -> System.out.println("Third: " + n);
        Stream.of(1,2,3).forEach( printIt );
    }

    public static void main(String[] args) {
        sessionOne();
    }

}
