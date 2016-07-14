package my.practice.collection;

import static org.junit.Assert.*;
//Replace this with another class/package for practice: e.g. my.practice.y2016.Practice.*.
import static my.practice.collection.SortUtility.*;

import org.junit.Test;

public class SortUtilityTest {

	@Test
	public void test_binarySearch() {
		int[] numbers = {1, 2, 3};
		
		assertEquals(-1, binarySearch(numbers, 4));
		assertEquals(2, binarySearch(numbers, 3));
		assertEquals(1, binarySearch(numbers, 2));
		assertEquals(0, binarySearch(numbers, 1));
		
		// Empty array
		numbers = new int[0];
		assertEquals(-1,  binarySearch(numbers, 2));
		
		// Singleton array
		int[] numbers2 = {1};
		assertEquals(-1, binarySearch(numbers2, 2));
		assertEquals(-1, binarySearch(numbers2, -2));
		assertEquals(0, binarySearch(numbers2, 1));
		
		int[] numbers3 = {1, 3, 4, 6, 7};
		assertEquals(1, binarySearch(numbers3, 3));
		assertEquals(-1, binarySearch(numbers3, 2));
		assertEquals(0, binarySearch(numbers3, 1));
	}

}
