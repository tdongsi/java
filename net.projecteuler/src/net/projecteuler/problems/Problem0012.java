package net.projecteuler.problems;

import net.projecteuler.utility.*;
import java.util.*;

/**
 * What is the value of the first triangle number to have over five hundred divisors?
 * 
 * Mathematical analysis:
 * 1) The n-th triangle numbers is n(n+1)/2.
 * 2) If a number can be factored as num = f1^p1 * f2^p2 * ... * fm^pm, then it has \Prod_1^m (p_i+1) divisors
 * 3) Then we can show that if n = 2*3*5*7*11*13*17*19*23 (product of the first 9 primes), then n+1 contributes at least another prime. 
 * And the corresponding n-th triangle number has more than k = 500 divivors 
 * (because n/2 is factored as product of 8 primes and n+1 is another prime). This serves as an upperbound to intialize the factorer.
 * 
 * @author tdongsi
 *
 */
public class Problem0012 {

	public static void main(String[] args) {
		long startTime = System.nanoTime();
//		solveBruteforce();
		long endTime = System.nanoTime();;
		System.out.println("Took "+(endTime - startTime)/1000 + " us"); 
		
		long startTime2 = System.nanoTime();
		solve();
		long endTime2 = System.nanoTime();;
		System.out.println("Took "+(endTime2 - startTime2)/1000 + " us"); 
	}

	/**
	 * Find the first triangle number that has more than 500 divisors
	 * This requires to initialize the sieve to a much larger number O(N^2).
	 * 
	 * TRICKY: Find maxN, depending on the problem, to initialize the prime sieve
	 */
	private static void solveBruteforce() {
		int maxN = 2*3*5*7*11*13*17;
		NumberFactorer factorer = new NumberFactorer(maxN);
		
		long requiredDivsorNum = 50;
		
		for ( int n = 1; n < maxN; n++ )
		{
			int triangleNumber = n*(n+1)/2;
			
			int product = factorer.getNumberOfDivisors(triangleNumber);
			
//			System.out.println( triangleNumber + " has " + product + " divisors.");
			
			if ( product > requiredDivsorNum )
			{
				System.out.println("The triangle number is " + triangleNumber);
				break;
			}
		}
	}
	
	/**
	 * Find the first triangle number that has more than 500 divisors
	 * This second method utilize the fact that the number is triangle number.
	 * It is faster than using the general method getNumberOfDivisors directly (brute force).
	 * The primve sieve is initalized to a much smaller number.
	 * 
	 * TRICKY: Find maxN, depending on the problem, to initialize the prime sieve
	 */
	private static void solve() {
		int maxN = 2*3*5*7*11*13;
		NumberFactorer factorer = new NumberFactorer(maxN);
		
		long requiredDivsorNum = 500;
		
		for ( int n = 1; n < maxN; n++ )
		{
			Map<Integer,Integer> primeFactors = new HashMap<Integer,Integer>();
			if ( n % 2 == 0 )
			{
				// Compute the prime factors
				primeFactors.putAll( factorer.getPrimeFactors(n/2) );
				primeFactors.putAll( factorer.getPrimeFactors(n+1) );
			} else
			{
				// Compute the prime factors
				primeFactors.putAll( factorer.getPrimeFactors(n) );
				primeFactors.putAll( factorer.getPrimeFactors((n+1)/2) );
			}
			
//			for( Map.Entry<Long, Integer> entry: primeFactors.entrySet() )
//			{
//				System.out.println( entry.getKey() + " : " + entry.getValue() );
//			}
			
			// Compute the number of divisors from the exponents of prime factors
			Collection<Integer> exponents = primeFactors.values();
			int product = 1;
			for (int exponent: exponents )
			{
				product *= (exponent+1);
			}
			
			if ( product > requiredDivsorNum )
			{
				long triangleNumber = n*(n+1)/2;
				System.out.println("The triangle number is " + triangleNumber);
				break;
			}
		}
	}
	
}
