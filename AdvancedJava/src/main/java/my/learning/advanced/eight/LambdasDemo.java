package my.learning.advanced.eight;

import java.io.File;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Created by tdongsi on 12/17/17.
 */
public class LambdasDemo {

    /**
     * Demo of lambdas
     */
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

        // Using method reference
        Stream.of(1,2,3).forEach(System.out::println);
    }

    /**
     * Demo of FileFilter
     */
    private static void fileFilter() {
        File directory = new File("./src/main/java/my/learning");
        Arrays.stream(
                directory.list((dir, name) -> name.endsWith("java"))
        ).forEach(System.out::println);
    }

    public static void main(String[] args) {
        lambdas();
        fileFilter();
    }

}
