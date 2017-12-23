package my.learning.advanced.eight;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tdongsi on 12/22/17.
 */
public class SortingDemo {

    /**
     * Java 7 - Sort based on natural ordering
     * @return
     */
    public static List<String> naturalSort(List<String> sampleStrings) {
        Collections.sort(sampleStrings);
        return sampleStrings;
    }

    public static List<String> sortByLength(List<String>  sampleStrings) {
        Collections.sort(sampleStrings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
        return sampleStrings;
    }

    public static List<String> sortByLengthLambda(List<String> sampleStrings) {
        Collections.sort(sampleStrings, (String s1, String s2) -> {
            return s1.length() - s2.length();
        });
        return sampleStrings;
    }

    public static List<String> sortByLengthComparator(List<String>  sampleStrings) {
        Collections.sort(sampleStrings, Comparator.comparingInt(String::length));
        return sampleStrings;
    }

    public static List<String> sortByLengthThenAlpha(List<String> sampleStrings) {
        Collections.sort(sampleStrings,
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        return sampleStrings;
    }

    public static List<String> streamSortByAlpha(List<String> sampleStrings) {
        return sampleStrings.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<String> streamSortByLength(List<String> sampleStrings) {
        return sampleStrings.stream()
                .sorted((s1, s2) -> s1.length() - s2.length())
                .collect(Collectors.toList());
    }

    public static List<String> streamSortByLengthThenAlpha(List<String> sampleStrings) {
        return sampleStrings.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
    }

}
