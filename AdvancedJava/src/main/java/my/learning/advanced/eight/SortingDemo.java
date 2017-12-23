package my.learning.advanced.eight;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by tdongsi on 12/22/17.
 */
public class SortingDemo {

    /**
     * Java 7 - Sort based on natural ordering
     * @return
     */
    public List<String> naturalSort(List<String> sampleStrings) {
        Collections.sort(sampleStrings);
        return sampleStrings;
    }

    public List<String> sortByLength(List<String>  sampleStrings) {
        Collections.sort(sampleStrings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        return sampleStrings;
    }

    public List<String> sortByLengthLambda(List<String> sampleStrings) {
        Collections.sort(sampleStrings, (String s1, String s2) -> {
            return s2.length() - s1.length();
        });
        return sampleStrings;
    }

    public List<String> sortByLengthComparator(List<String>  sampleStrings) {
        Collections.sort(sampleStrings, Comparator.comparingInt(String::length));
        return sampleStrings;
    }

    public List<String> sortByLengthThenAlpha(List<String> sampleStrings) {
        Collections.sort(sampleStrings,
                Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder())
        );
        return sampleStrings;
    }

    public List<String> streamSortByLength(List<String>)

}
