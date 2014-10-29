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

}
