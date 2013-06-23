package net.projecteuler.problems;

/**
 * The following iterative sequence is defined for the set of positive integers:
 * n -> n/2 (n is even)
 * n -> 3n + 1 (n is odd)
 * Which starting number, under one million, produces the longest chain?
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0014 {

	public static void main(String[] args) {
		long maxNumber = 1;
		long maxStep = 1;
		long currNum;
		int stepCount;
		long range = 1000000;
		
		long timeIn;
		long timeOut;
		
		// Brute force method:
		// For each number, carry out the whole process and count the chain numbers
		timeIn = System.nanoTime();
		for ( long number = 3; number < range; number += 2) {
			currNum = number;
			stepCount = 0;
			
			while ( currNum > 1 ) {
				if ( currNum % 2 == 1 ) {
					// n -> 3n + 1
					currNum *= 3;
					currNum++;
				} else {
					currNum /= 2;
				}
				
				stepCount++;
				
			}
			
			 // System.out.println( "number " + number + " stepCount " + stepCount );
			
			// Update the maximum if needed
			if ( stepCount > maxStep ) {
				maxStep = stepCount;
				maxNumber = number;
			}
		}
		timeOut = System.nanoTime();
		System.out.println( "Running time " + (timeOut - timeIn)/1e9 );
		System.out.println( "Brute force: maxNumber " + maxNumber + " maxStep " + maxStep );

	}

}
