package my.practice.math;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class BitSetSieveTest {

	@Test
	public void test_isPrime() {
		
		Sieve sieve = new BitSetSieve(100);
		
		assertTrue( sieve.isPrime(2) );
		assertTrue( sieve.isPrime(3) );
		assertTrue( sieve.isPrime(5) );
		assertTrue( sieve.isPrime(7) );
		assertTrue( sieve.isPrime(11) );
		
		assertFalse( sieve.isPrime(1) );
		assertFalse( sieve.isPrime(4) );
		assertFalse( sieve.isPrime(6) );
		
	}

}
