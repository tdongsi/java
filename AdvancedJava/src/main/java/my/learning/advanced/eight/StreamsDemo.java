package my.learning.advanced.eight;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by tdongsi on 12/22/17.
 */
public class StreamsDemo {
    private List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");

    public String joinStream() {
        return Stream.of("this", "is", "a", "list", "of", "strings")
                .collect(Collectors.joining(" "));
    }

    public String joinUpperCase() {
        return strings.stream()
                .map(String::toUpperCase)
                .collect(Collectors.joining(" "));
    }

    public int getTotalLength() {
        return strings.stream()
                .collect(Collectors.summingInt(String::length));
    }

    public double sumFirstBigDecimals(int num) {
        return Stream.iterate(BigDecimal.ONE, val -> val.add(BigDecimal.ONE))
                .limit(num)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
    }

    public double sumRandom(int num) {
        return Stream.generate(Math::random)
                .limit(num)
                .reduce((acc, n) -> {
                    System.out.printf("Acc=%s, n=%s%n", acc, n);
                    return acc + n;
                })
                .orElse(0.0);
    }

    public static void main(String args[]) {
        StreamsDemo demo = new StreamsDemo();

        System.out.println(demo.joinStream());
        System.out.println(demo.joinUpperCase());
    }
}
