package my.practice.math;

import static org.junit.Assert.*;

import org.junit.Test;

public class MathUtilityTest {

	@Test
	public void test_gcd() {
		assertEquals(5, MathUtility.gcd(5, 5));
		assertEquals(5, MathUtility.gcd(50, 35));
		assertEquals(5, MathUtility.gcd(35, 50));
		
		assertEquals(7, MathUtility.gcd(14, 21));
	}
	
	@Test
	public void test_lcm() {
		assertEquals( 42, MathUtility.lcm(14, 21));
	}

}
