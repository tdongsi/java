package net.projecteuler.utility;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class NumberUtilityTest {

	@Test
	public void test_isPrimeNumber() {
		assertTrue( NumberUtility.isPrimeNumber(2L) );
		assertTrue( NumberUtility.isPrimeNumber(3L) );
		assertTrue( NumberUtility.isPrimeNumber(5L) );
		assertTrue( NumberUtility.isPrimeNumber(97L) );
		
	}
	
	@Test
	public void test_getAllPrimesArray()
	{
		long maxNum = 20;
		NumberUtils util = new NumberUtils();
		List<Long> primeList2 = util.getAllPrimesLong( maxNum );
		long[] trueValues = {2, 3, 5, 7, 11, 13, 17, 19};
		
		for (int i = 0; i < primeList2.size(); i++) {
			
			assertEquals( primeList2.get(i).longValue(), trueValues[i] );
			
		}
	}
	
	@Test
	public void test_computeProperDivsiorSum()
	{
		assertEquals( NumberUtility.computeProperDivsiorSum(284), 220 );
		assertEquals( NumberUtility.computeProperDivsiorSum(220), 284 );
	}
	
	
	@Test
	public void test_computeReciprocalLength()
	{
		// Test prime numbers
		assertEquals( NumberUtility.computeReciprocalLength(3), 1);
		assertEquals( NumberUtility.computeReciprocalLength(7), 6);
		assertEquals( NumberUtility.computeReciprocalLength(11), 2);
		assertEquals( NumberUtility.computeReciprocalLength(17), 16);
		assertEquals( NumberUtility.computeReciprocalLength(31), 15);
		
		// Test composite numbers
		assertEquals( NumberUtility.computeReciprocalLength(7*17), 48);
		assertEquals( NumberUtility.computeReciprocalLength(6), 1);
		assertEquals( NumberUtility.computeReciprocalLength(15), 1);
		
		// Test 2 and 5
		assertEquals( NumberUtility.computeReciprocalLength(5), 0);
		assertEquals( NumberUtility.computeReciprocalLength(2), 0);
		
		// Test squares
		assertEquals( NumberUtility.computeReciprocalLength(3*3), 1);
		assertEquals( NumberUtility.computeReciprocalLength(3*3*3), 1*3);
		assertEquals( NumberUtility.computeReciprocalLength(3*3*3*3), 1*3*3);
		
		assertEquals( NumberUtility.computeReciprocalLength(7*7), 6*7);
		assertEquals( NumberUtility.computeReciprocalLength(7*7*7), 6*7*7);
		assertEquals( NumberUtility.computeReciprocalLength(3*7*7), 6*7);
		
		assertEquals( NumberUtility.computeReciprocalLength(11*11), 2*11 );
		assertEquals( NumberUtility.computeReciprocalLength(11*11*11), 2*11*11 );
		assertEquals( NumberUtility.computeReciprocalLength(7*11*11), 3*2*11 );
		
		assertEquals( NumberUtility.computeReciprocalLength(31*31), 15*31);
		assertEquals( NumberUtility.computeReciprocalLength(31*31*31), 15*31*31);
		
		assertEquals( NumberUtility.computeReciprocalLength(487), 486);
		assertEquals( NumberUtility.computeReciprocalLength(487*487), 486);
		assertEquals( NumberUtility.computeReciprocalLength(487*487*487), 486*487);
		
		assertEquals( NumberUtility.computeReciprocalLength(1009), 252);
	}
	
	@Test
	public void test_lcm()
	{
		assertEquals( NumberUtility.lcm(48, 16), 48 );
		assertEquals( NumberUtility.lcm(8, 6), 24 );
		
	}
	
	@Test
	public void test_remainderDivideNineSeries() {
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(1, 3));
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(6, 7));
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(2, 11));
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(16, 17));
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(15, 31));
		
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(486, 487));
		assertEquals( 0, NumberUtility.remainderDivideNineSeries(486, 487*487));
	}

}
