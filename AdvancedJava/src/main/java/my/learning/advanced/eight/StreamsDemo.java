package my.learning.advanced.eight;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        // Here demo file processing with stream

        try {
            // sorting
            Files.lines(
                    Paths.get("/", "usr", "share", "dict", "web2")
            ).filter(s -> s.length() > 10)
            .map(String::toLowerCase)
            .sorted(Comparator.comparingInt(String::length).reversed())
            .limit(10)
            .forEach((String w) ->
                System.out.printf("%s (%d)%n", w, w.length())
            );

            // find max. Demo of Optional
            Optional<String> max = Files.lines(Paths.get("/", "usr", "share", "dict", "web2"))
                    .filter(s -> s.length() > 10)
                    .map(String::toLowerCase)
                    .sorted(Comparator.comparingInt(String::length).reversed())
                    .findFirst();
            System.out.println(max.orElse("not found"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
