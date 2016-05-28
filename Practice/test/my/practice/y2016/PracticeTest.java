package my.practice.y2016;

import static org.junit.Assert.*;
import static my.practice.y2016.Practice.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class PracticeTest {

	@Test
	public void test_maximalSubarray() {
		List<Integer> list = Arrays.asList(1, -3, 5, -2, 9, -8, -6, 4);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(5, -2, 9);
		
		assertEquals(expected, subArray);
	}

}
