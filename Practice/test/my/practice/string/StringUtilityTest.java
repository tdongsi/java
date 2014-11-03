package my.practice.string;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void test_atoi() {
		assertEquals(123, StringUtility.atoi("123"));
		assertEquals(-123, StringUtility.atoi("-123"));
		assertEquals(0, StringUtility.atoi("0"));
		assertEquals(0, StringUtility.atoi("0000"));
	}
	
	@Test
	public void test_hasString() {
		assertTrue( StringUtility.hasSubstring("", ""));
		
		assertTrue( StringUtility.hasSubstring("master", "mast"));
		assertTrue( StringUtility.hasSubstring("master", "er"));
		
		assertTrue( StringUtility.hasSubstring("master", "m"));
		assertTrue( StringUtility.hasSubstring("master", "r"));
		assertTrue( StringUtility.hasSubstring("master", ""));
		
		assertFalse( StringUtility.hasSubstring("master", "mate"));
		assertFalse( StringUtility.hasSubstring("master", "mastex"));
	}

}
