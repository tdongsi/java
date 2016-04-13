/**
 * 
 */
package net.projecteuler.problems;

import java.util.List;

import net.projecteuler.utility.BitSetPrimeSieve;
import net.projecteuler.utility.NumberUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
 * If d(a) = b and d(b) = a, where a is not b, then a and b are an amicable pair and each of a and b are called amicable numbers.
 * Evaluate the sum of all the amicable numbers under 10000.
 * 
 * @author tdongsi
 *
 */
public class Problem0021 {
	
	private static Logger logger = LoggerFactory.getLogger(Problem0021.class);

	public static void main(String[] args) {
		
		Problem0021 solver = new Problem0021();
		
		solver.run(logger);
	}

	public void run(Logger logger2) {
		
		int maxNumber = 10000;
		
		int[] divisorSums = new int[maxNumber];
		
		BitSetPrimeSieve sieve = new BitSetPrimeSieve(maxNumber);
		List<Integer> primes = sieve.getList();
		
		for (int i = 4; i < divisorSums.length; i++) {
			// Primes cannot be amicable numbers
			if ( !primes.contains(i))
			{		
				divisorSums[i] = (int)NumberUtility.computeProperDivsiorSum(i);
				logger.debug("Number: {}", divisorSums[i] );
			}
		}
		
		int sum = 0;
		for (int i = 4; i < divisorSums.length; i++) {
			if ( !primes.contains(i))
			{
				int j = divisorSums[i];
				
				if ( j < divisorSums.length && divisorSums[j] == i && i != j )
				{
					logger.debug( "Pair of amicable numbers: {} {}", i, j );
					sum += i;
				}
			}
		}
		
		logger.info("Sum of amicable numbers is {}", sum );
		
	}

}
