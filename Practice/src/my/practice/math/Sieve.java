package my.practice.math;

import java.util.List;

/**
 * Basic behavior of a prime sieve, e.g. Erastosthenes
 * 
 * @author tdongsi
 *
 */
public interface Sieve {
	
	/**
	 * Check if the given number is prime
	 * 
	 * @param number
	 * @return
	 */
	public boolean isPrime( int number );
	
	
	/**
	 * Return the list of prime numbers in the sieve
	 * @return
	 */
	public List<Integer> listOfPrimes();

}
