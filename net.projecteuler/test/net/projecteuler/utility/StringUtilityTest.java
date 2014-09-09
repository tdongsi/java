package net.projecteuler.utility;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilityTest {

	@Test
	public void test_isPalindrome() {
		assertTrue(StringUtility.isPalindrome("dad"));
		assertTrue(StringUtility.isPalindrome("daad"));
		assertTrue(StringUtility.isPalindrome("da ad"));
		assertTrue(StringUtility.isPalindrome("d"));
		assertTrue(StringUtility.isPalindrome(""));
		
		assertFalse(StringUtility.isPalindrome("hello"));
		assertFalse(StringUtility.isPalindrome("longlong"));
		assertFalse(StringUtility.isPalindrome("da"));
	}

}
