/**
 * 
 */
package net.projecteuler.utility;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A utility class for obtaining the prime factors and their exponents for any number.
 * The class must be initialized with a number as the limit. 
 * 
 * @author tdongsi
 *
 */
public class NumberFactorer {
	
	private static Logger logger = LoggerFactory.getLogger(NumberFactorer.class);
	
	// Version 2: Use an internal sieve for faster factoring.
	private PrimeSieve sieve;

	/**
	 * The internal table of prime numbers
	 * @return the primeList
	 */
	public List<Integer> getPrimeList() {
		return sieve.getList();
	}

	/**
	 * @param maxNumber: the bound of prime sieve
	 */
	public NumberFactorer(int maxNumber) {
		// Compute all the prime number less than maxNumber
		// Using Sieve of Eratosthenes.
		sieve = new BitSetPrimeSieve( maxNumber);
	}
	
	
	/**
	 * @param number the number to be factored
	 * @return a table with prime numbers as keys and theirs powers as their corresponding values
	 */
	public Map<Integer,Integer> getPrimeFactors( int number )
	{
		return sieve.getPrimeFactors(number);
	}
	
	
	/**
	 * Get the number of divisors of the given number.
	 * Use the table of primes initialized by the constructor.
	 * @param number
	 * @return The number of divisors
	 */
	public int getNumberOfDivisors(int number )
	{
		
		// Compute the prime factors
		Map<Integer,Integer> primeFactors = getPrimeFactors(number);

		// Compute the number of divisors from the exponents of prime factors
		// From: http://mathworld.wolfram.com/Divisor.html
		Collection<Integer> exponents = primeFactors.values();
		int product = 1;
		for (int exponent: exponents )
		{
			product *= (exponent+1);
		}
		return product;
	}

}
