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

			long digitCount = findReciprocalLength(prime);

					logger.debug( "Prime: {} , cycle length: {}", prime, digitCount );

			if ( digitCount > maxLength )
			{
				maxLength = digitCount;
				theNumber = prime;
			}
		}

		logger.info("Number: {} , cycle length: {}", theNumber, maxLength );

	}
	
	/**
	 * The dirty version for finding the length of reciprocal cycle.
	 * Check NumberUtility for faster version.
	 * @param prime: a prime number
	 * @return the length of reciprocal cycle.
	 */
	private long findReciprocalLength(long prime)
	{
		BigInteger current = new BigInteger("9");
		BigInteger bigPrime = new BigInteger( String.valueOf(prime));
		BigInteger nine = new BigInteger("9");
		int digitCount = 1;
		while ( true )
		{
			BigInteger remainder = current.remainder(bigPrime);
			
			if ( remainder.equals(BigInteger.ZERO) )
			{
				break;
			}
			
			// add one more 9
			current = current.multiply( BigInteger.TEN );
			current = current.add(nine);
			
			digitCount++;
		}
		
		return digitCount;
	}

}
