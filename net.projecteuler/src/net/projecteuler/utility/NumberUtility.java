package net.projecteuler.utility;

import java.util.*;

/**
 * Containing some BASIC number utility functions.
 * As of 2013 March 1st, more complex number utility functions should be added to NumberUtils class instead.
 * 
 * @author tdongsi
 *
 */
public class NumberUtility {
	
	/**
	 * Enforce that this class should not be instantiated.
	 * Suppress default constructor.
	 */
	private NumberUtility()
	{
		throw new AssertionError();
	}

	/**
	 * Check if the number is a prime number
	 * @param number
	 * @return
	 */
	public static boolean isPrimeNumber( long number ) {
		if ( number < 2 )
			return false;

		if ( number == 2 || number == 3 )
			return true;

		if ( number % 2 == 0 )
			return false;

		for ( long i=3; i*i <= number; i+=2 ) {
			if ( number % i == 0 ) {
				return false;
			}
		}
		// if there is no break in for loop, then it is a prime number
		return true;
	}


	/**
	 * A number n is called abundant if the sum of its proper divisors is more than n
	 * 
	 * @param number: the given number
	 * @return
	 */
	public static boolean isAbundantNumber( long number )
	{
		long sum = computeProperDivsiorSum(number);

		if ( sum > number )
			return true;
		else
			return false;
	}

	/**
	 * Find the greatest common divisor
	 * @param number1: the larger number
	 * @param number2: the smaller number
	 * @return the greatest common divisor
	 */
	public static long gcd( long number1, long number2 ) {
		long temp;

		while ( number2 != 0 ) {
			temp = number1 % number2;

			number1 = number2;
			number2 = temp;
		}

		return number1;
	}


	/**
	 * Find the least common multiple
	 * @param number1: the larger number
	 * @param number2: the smaller number
	 * @return the greatest common divisor
	 */
	public static long lcm( long number1, long number2 ) {
		if ( number1 == 0 || number2 == 0 )
			return 0;

		long temp = gcd(number1,number2);
		return number1*number2/temp;
	}


	/**
	 * Find all the prime numbers less than or equal to maxNumber, using sieve of Eratosthenes
	 * @param maxNumber: the upper bound
	 * @return a array list of prime numbers
	 */
	public static List<Integer> getAllPrimes( long maxNumber ) {
		BitSetPrimeSieve sieve = new BitSetPrimeSieve((int) maxNumber);
		return sieve.getList();
	}

	public static long computeProperDivsiorSum(long number) {
		long sum = 0;

		for ( int j=1; j*j <= number; j++)
		{
			if ( number % j == 0 )
			{
				sum += j;

				// Find the correspondent divisor
				long div = number/j;
				if ( div != j && div != number )
				{
					sum += div;
				}
			}
		}

		return sum;
	}


	/**
	 * Compute the length of reciprocal cycle of the number 1/number for the given number
	 * @param number: the input number
	 * @return the reciprocal length
	 */
	public static long computeReciprocalLength(long number) {
		long digitCount = 1;
		long current = 9;

		// Remove 2 factors
		while (number % 2 == 0 )
		{
			number /= 2;
		}

		// Remove 5 factors
		while (number % 5 == 0 )
		{
			number /= 5;
		}

		if ( number == 1 )
		{
			return 0;
		}

		// Find the length of the current number's reciprocal cycles
		while ( true )
		{
			long remainder = current % number;

			if ( remainder == 0 )
			{
				break;
			}

			// add one more 9 to the right of remainder
			current = remainder*10 + 9;

			digitCount++;
		}

		return digitCount;
	}
	
	/**
 	 * Compute remainder = (10^power - 1) % number;
 	 */
 	public static int remainderDivideNineSeries(int power, int number) {
 		// This is to speed up the computation.
 		// Too large POWER_STEP may be inaccurate due to Math.pow.
 		// Naive implementation is equivalent to 10 and 1, respectively.
 	 	final int NUMBER_STEP = 1000;
 	 	final int POWER_STEP = 3;
 	 	
 		int remainder = 0;

 		for (int i = power; i > 0; i -= POWER_STEP) {
 			if (i > POWER_STEP) {
 				remainder++;
 				remainder *= NUMBER_STEP;
 				remainder--;
 				remainder %= number;
 			} else {
 				int number_step = (int) Math.pow(10, i);

 				remainder++;
 				remainder *= number_step;
 				remainder--;
 				remainder %= number;

 				break;
 			}
 		}

 		return remainder;
 	}

}
