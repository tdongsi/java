package net.projecteuler.problems;

import java.math.BigInteger;
import java.util.List;

import net.projecteuler.utility.BitSetPrimeSieve;
import net.projecteuler.utility.NumberUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its decimal fraction part.
 * @author EpipolarGineer
 *
 */
public class Problem0026 {

	private static Logger logger = LoggerFactory.getLogger( Problem0026.class );

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Problem0026 solver = new Problem0026();
		solver.solve();
	}

	// Trick 1: Only compute the cycle length of primes, 
	// since the cycle length of a composite number is Least common multiple of cycle lengths of its factors.

	// Trick 2: Finding cycle length of primes turns out to be easier.

	// Trick 3: The following solution skips checking composite number.
	// Robust solution may require checking composite number based on prime.

	private void solve() {

		logger.debug("Solving Problem 26" );
		final int maxNumber = 1000;

		BitSetPrimeSieve sieve = new BitSetPrimeSieve(maxNumber);
		List<Integer> primes = sieve.getList();
		
		long maxLength = -1;
		long theNumber = -1;

		for (long prime : primes) {
			if ( prime == 2 || prime == 5 )
			{
				continue;
			}

			long digitCount = NumberUtility.computeReciprocalLength(prime);

			logger.debug( "Prime: {} , cycle length: {}", prime, digitCount );

			if ( digitCount > maxLength )
			{
				maxLength = digitCount;
				theNumber = prime;
			}
		}

		logger.info("Number: {} , cycle length: {}", theNumber, maxLength );

	}
	
}
