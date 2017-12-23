package my.learning.advanced.eight;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tdongsi on 12/22/17.
 */
public class SortingDemoTest {

    private final String[] STRINGS = {"this", "is", "a", "list", "of", "strings"};
    private List<String> input;
    private final List<String> NATURAL_ORDER = Arrays.asList("a", "is", "list", "of", "strings", "this");
    private final List<String> LENGTH_ORDER = Arrays.asList("a", "is", "of", "this", "list", "strings");
    private final List<String> LENGTH_ALPHA_ORDER = Arrays.asList("a", "is", "of", "list", "this", "strings");

    @Before
    public void setUp() throws Exception {
        input = Arrays.asList(STRINGS);
    }

    @Test
    public void naturalSort() throws Exception {
        List<String> output = SortingDemo.naturalSort(input);
        assertTrue(output == input); // same reference
        assertEquals(NATURAL_ORDER, output);
    }

    @Test
    public void sortByLength() throws Exception {
        List<String> output = SortingDemo.sortByLength(input);
        assertTrue(output == input); // same reference
        assertEquals(LENGTH_ORDER, output);
    }

    @Test
    public void sortByLengthLambda() throws Exception {
        List<String> output = SortingDemo.sortByLength(input);
        assertTrue(output == input); // same reference
        assertEquals(LENGTH_ORDER, output);
    }

    @Test
    public void sortByLengthComparator() throws Exception {
        List<String> output = SortingDemo.sortByLength(input);
        assertTrue(output == input); // same reference
        assertEquals(LENGTH_ORDER, output);
    }

    @Test
    public void sortByLengthThenAlpha() throws Exception {
        List<String> output = SortingDemo.sortByLengthThenAlpha(input);
        assertTrue(output == input); // same reference
        assertEquals(LENGTH_ALPHA_ORDER, output);
    }

    @Test
    public void streamSortByAlpha() throws Exception {
        List<String> output = SortingDemo.streamSortByAlpha(input);
        assertTrue(output != input); // different reference
        assertEquals(NATURAL_ORDER, output);
    }

    @Test
    public void streamSortByLength() throws Exception {
        List<String> output = SortingDemo.streamSortByLength(input);
        assertTrue(output != input); // different reference
        assertEquals(LENGTH_ORDER, output);
    }

    @Test
    public void streamSortByLengthThenAlpha() throws Exception {
        List<String> output = SortingDemo.streamSortByLengthThenAlpha(input);
        assertTrue(output != input); // different reference
        assertEquals(LENGTH_ALPHA_ORDER, output);
    }

}