/**
 * 
 */
package net.projecteuler.utility;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author EpipolarGineer
 *
 */
public class BitSetPrimeSieveTest {

	@Test
	public void test_getList() {
		BitSetPrimeSieve sieve = new BitSetPrimeSieve(20);
		List<Integer> primes = sieve.getList();
		
		Integer[] golden = {2, 3, 5, 7, 11, 13, 17, 19 };
		
		Assert.assertArrayEquals(golden, primes.toArray(new Integer[0]));
		
		
		Assert.assertTrue(sieve.isPrime(13));
		Assert.assertFalse(sieve.isPrime(12));
	}
	
	
	@Test
	public void test_primeReciprocalLength()
	{
		BitSetPrimeSieve sieve = new BitSetPrimeSieve(50);
		
		// Test prime numbers
		assertEquals( sieve.primeReciprocalLength(3), 1);
		assertEquals( sieve.primeReciprocalLength(7), 6);
		assertEquals( sieve.primeReciprocalLength(11), 2);
		assertEquals( sieve.primeReciprocalLength(17), 16);
		assertEquals( sieve.primeReciprocalLength(31), 15);
		
		// Test 2 and 5
		assertEquals( sieve.primeReciprocalLength(2), 0);
		assertEquals( sieve.primeReciprocalLength(5), 0);

	}

}
