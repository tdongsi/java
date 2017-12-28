package my.learning.advanced.eight;

import org.junit.Test;
import static org.hamcrest.number.IsCloseTo.*;

import static org.junit.Assert.*;

/**
 * Created by tdongsi on 12/22/17.
 */
public class StreamsDemoTest {
    @Test
    public void sumRandom() throws Exception {
        demo.sumRandom(10);
    }

    private StreamsDemo demo = new StreamsDemo();

    @Test
    public void sumFirstBigDecimals() throws Exception {
        assertThat(demo.sumFirstBigDecimals(10), closeTo(
                1+2+3+4+5+6+7+8+9+10, 0.01
        ));
    }

    @Test
    public void getTotalLength() throws Exception {
        assertEquals(20, demo.getTotalLength());
    }

    @Test
    public void joinStream() throws Exception {
        assertEquals("this is a list of strings", demo.joinStream());
    }

    @Test
    public void joinUpperCase() throws Exception {
        assertEquals( "THIS IS A LIST OF STRINGS", demo.joinUpperCase());
    }

}