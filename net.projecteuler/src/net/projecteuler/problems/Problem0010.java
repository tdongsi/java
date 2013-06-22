package net.projecteuler.problems;

import net.projecteuler.utility.NumberUtility;

/**
 * Find the sum of all the primes below two million.
 * 
 * Mathematical analysis: it is easy to show that for prime numbers greater than 2 and 3,
 * it has the form 6k+1 or 6k-1.
 * 
 * @author tdongsi
 *
 */
public class Problem0010 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// The prime numbers 2 and 3 are accounted here
		long sum = 2 + 3;
		
		for ( long i=6; i+1 < 2000000; i += 6 ) {
			if ( NumberUtility.isPrimeNumber(i-1) ) {
				sum += (i-1);
			}
			if ( NumberUtility.isPrimeNumber(i+1) ) {
				sum += (i+1);
			}
		}
		
		System.out.println( "The sum is " + sum );

	}

}
