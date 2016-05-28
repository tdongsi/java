package my.practice.string;

import static org.junit.Assert.*;
// Replace this with another class/package for practice: e.g. my.practice.y2016.Practice.*.
import static my.practice.string.StringUtility.*;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void test_atoi() {
		assertEquals(123, atoi("123"));
		assertEquals(-123, atoi("-123"));
		assertEquals(0, atoi("0"));
		assertEquals(0, atoi("0000"));
		assertEquals(0, atoi("-0"));
	}
	
	@Test
	public void test_hasSubString() {
		assertTrue( hasSubstring("", ""));
		
		assertTrue( hasSubstring("master", "mast"));
		assertTrue( hasSubstring("master", "er"));
		
		assertTrue( hasSubstring("master", "m"));
		assertTrue( hasSubstring("master", "r"));
		assertTrue( hasSubstring("master", ""));
		
		assertFalse( hasSubstring("master", "mate"));
		assertFalse( hasSubstring("master", "mastex"));
	}

}
