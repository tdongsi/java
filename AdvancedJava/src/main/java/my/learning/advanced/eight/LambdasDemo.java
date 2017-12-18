package my.learning.advanced.eight;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by tdongsi on 12/17/17.
 */
public class LambdasDemo {

    private static void lambdas() {

        final int MULTIPLIER = 2;
        int aMultiplier = 3;

        // Simplest lambda form
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(n -> System.out.println(n));

        // Full lambda form
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach((Integer n) -> {
                    System.out.println(n * MULTIPLIER);
                });

        // Classic equivalent: anonymous inner class
        Stream.of(3, 1, 4, 1, 5, 9)
                .forEach(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) {
                        System.out.println("Second: " + integer * aMultiplier );
                    }
                });

        // Uncomment the following line will fail with error:
        // "local variables referenced from a lambda expression/inner class must be final or effectively final"
        // aMultiplier = 4;

        // Assigning consumer
        Consumer<Integer> printIt = n -> System.out.println("Third: " + n * aMultiplier);
        Stream.of(1,2,3).forEach( printIt );
    }

    public static void main(String[] args) {
        lambdas();
    }

}
