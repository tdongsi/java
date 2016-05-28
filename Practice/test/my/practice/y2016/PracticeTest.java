package my.practice.y2016;

import static org.junit.Assert.*;
import static my.practice.y2016.Practice.*;

import java.util.ArrayList;
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
	
	@Test
	public void test_maximalSubarray2() {
		List<Integer> list = Arrays.asList(1, -3, 5, -2, 9, -8, -6, 4, 2, -1, 5, 3, -4);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(4, 2, -1, 5, 3);
		
		assertEquals(expected, subArray);
	}
	
	@Test
	public void test_maximalSubarray3() {
		List<Integer> list = Arrays.asList(1, -3, 5, -2, 9, -8, -6, 4, 2, -1, 5, 3, -4, 6);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(4, 2, -1, 5, 3, -4, 6);
		
		assertEquals(expected, subArray);
	}
	
	@Test
	public void test_maximalSubarray_empty() {
		List<Integer> list = new ArrayList<>();
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = list;
		
		assertEquals(expected, subArray);
	}
	
	@Test
	public void test_maximalSubarray_singleton() {
		List<Integer> list = Arrays.asList(1);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(1);
		
		assertEquals(expected, subArray);
	}
	
	@Test
	public void test_maximalSubarray_double() {
		List<Integer> list = Arrays.asList(-1, 2);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(2);
		
		assertEquals(expected, subArray);
	}
	
	@Test
	public void test_maximalSubarray_double2() {
		List<Integer> list = Arrays.asList(1, -2);
		List<Integer> subArray = maximalSubarray(list);
		List<Integer> expected = Arrays.asList(1);
		
		assertEquals(expected, subArray);
	}

}
