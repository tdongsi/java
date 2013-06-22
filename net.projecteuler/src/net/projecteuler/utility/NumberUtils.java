/**
 * 
 */
package net.projecteuler.utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Experimental and complex utility functions for numbers.
 * 
 * @author tdongsi
 *
 */
public class NumberUtils {

	/**
	 * Find all the prime numbers less than or equal to maxNumber, using sieve of Eratosthenes
	 * @param maxNumber: the upper bound
	 * @return a linked list of prime numbers
	 */
	public List<Long> getAllPrimesLong( long maxNumber ) {
		List<Long> list = new ArrayList<Long>( (int)maxNumber/2 );

		// Add the prime number 2 at the head of the list
		list.add( 2L );

		// Create a list of odd numbers from 3 to maxNumber
		for ( long number = 3; number <= maxNumber; number += 2 ) {
			list.add( number );
		}

		// From the beginning of the list, find and remove the composite number of the current prime pNum
		for ( int i = 1; i < list.size(); i++ ) {
			long pNum = list.get(i);

			if ( pNum*pNum > maxNumber )
				break;

			for ( long number = pNum*pNum; number <= maxNumber; number += pNum ) {
				list.remove( new Long(number));
			}
		}

		return list;
	}
	
	/** This main program is for testing the above utility functions
	 * @param args
	 */
	public static void main( String[] args ) {
		long maxNum = 100;
		NumberUtils util = new NumberUtils();

		long timeIn = System.nanoTime();
		List<Long> primeList2 = util.getAllPrimesLong( maxNum );
		long timeOut = System.nanoTime();
		System.out.println( "Running time " + (timeOut - timeIn) );

		timeIn = System.nanoTime();
//		List<Integer> primeList2 = getAllPrimes( maxNum );
		timeOut = System.nanoTime();
		System.out.println( "Running time " + (timeOut - timeIn) );

		for ( long number : primeList2 ) {
			System.out.print( " " + number );
		}

	}

}
