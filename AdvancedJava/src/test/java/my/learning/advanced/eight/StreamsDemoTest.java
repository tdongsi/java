package my.learning.advanced.eight;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tdongsi on 12/22/17.
 */
public class StreamsDemoTest {
    @Test
    public void getTotalLength() throws Exception {
        assertEquals(20, demo.getTotalLength());
    }

    private StreamsDemo demo = new StreamsDemo();

    @Test
    public void joinStream() throws Exception {
        assertEquals("this is a list of strings", demo.joinStream());
    }

    @Test
    public void joinUpperCase() throws Exception {
        assertEquals( "THIS IS A LIST OF STRINGS", demo.joinUpperCase());
    }

}