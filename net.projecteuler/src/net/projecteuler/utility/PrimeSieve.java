/**
 * 
 */
package net.projecteuler.utility;

import java.util.List;
import java.util.Map;

/**
 * The basic interface that specifies operations of a prime sieve
 * 
 * @author tdongsi
 *
 */
public interface PrimeSieve {
	
	/**
	 * Get the list of primes from the sieve
	 * 
	 * @return
	 */
	public List<Integer> getList();
	
	/**
	 * Check if the given number is prime
	 * 
	 * @param n
	 * @return
	 */
	public boolean isPrime(int n);
	
	/**
	 * Get prime factorization of the given number
	 * 
	 * @param number
	 * @return map of prime numbers as keys and their exponents as values 
	 */
	public Map<Integer,Integer> getPrimeFactors( int number );
	
	/**
	 * Compute the integer from the given factorization.
	 * The factorization that represent the integer is a table 
	 * that map the primes and their corresponding exponents 
	 * 
	 * @param factorization the map that represents the prime factorization.
	 * @return the integer
	 */
	public int valueFromPrimeFactors(Map<Integer,Integer> factorization);

}
