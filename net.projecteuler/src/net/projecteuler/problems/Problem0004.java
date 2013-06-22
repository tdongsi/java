package net.projecteuler.problems;

import net.projecteuler.utility.StringUtility;

/**
 * Find the largest palindrome made from the product of two 3-digit numbers.
 * 
 * Mathematical analysis:
 * Product of two 3-digit numbers is in the range [1e4,1e6-1]. So, it either has 5 or 6 digits.
 * To find the largest product, it is safe to assume it has 6 digits (find an example if you want rigor). 
 * It can be shown that 6-digit numbers that read the same both way is divisible by 11.
 * 
 * @author tdongsi
 *
 */
public class Problem0004 {

	
	public static void main(String[] args) {
		
		int solution = -1;
		
		solution = solveBruteforce();
		
		System.out.println( "Largest: " + solution );
		
		// Faster solution
		final int maxNum = 999;
		final int minNum = 100;
		solution = Problem0004.solve(maxNum, minNum);
		System.out.println( "Largest: " + solution );
		
		return;

	}

	/**
	 * Find the largest palindrome made from the product of two numbers.
	 * For example: if the two numbers are three-digit, then minNum should be 100, maxNum should be 999
	 * 
	 * @param maxNum: upper limit of the factors.
	 * @param minNum: lower limit of the factors.
	 * @return the largest palindromic number, -1 if there is none.
	 */
	public static int solve(final int maxNum, final int minNum) {
		
		final int maxProduct = ((maxNum*maxNum)/11)*11;
		final int minProduct = ((minNum*minNum)/11 + 1)*11;
		
		// 997997 and 10010 is the nearest numbers to 999*999 and 100*100, respectively, that are divisible by 11
//		System.out.println(maxProduct + " " + minProduct);
		
		for ( int product = maxProduct; product >= minProduct; product -= 11 ) {
			// Find the lower bound
			int lowerBound = (int)Math.floor(Math.sqrt(product));
			
			for ( int number = maxNum; number >= lowerBound; number-- ) {
				
				// Check if it can be a product of two 3-digit numbers
				if ( product % number == 0) {

					// get the integer that is reverse string of the current number
					int reverseProduct = Integer.parseInt( StringUtility.reverseString(Integer.toString(product)));
					
					// if they are the same, then the current number is palindromic
					if ( reverseProduct == product ) {
						return product;
					}
				}
			}
		}
		
		// Return -1 if it cannot find one
		return -1;
		
	}
	

	/**
	 * Simple brute-force solution
	 */
	private static int solveBruteforce() {
		int maxProduct = -1;
		StringBuilder productString = new StringBuilder(10);
		
		int product;
		int numberOfDigits;
		int reverseProduct;
		
		// Simple brute-force solution
		outerloop:
		for ( int i = 999; i >= 100; i-- ) {
			for ( int j = i; j >= 100; j-- ) {
				product = i * j;
				numberOfDigits = ((product / 100000) == 0 )? 5 : 6;
				
				productString.append( Integer.toString(product), 0, numberOfDigits);
				productString.reverse();
				reverseProduct = Integer.parseInt(productString.toString());
				
				if ( reverseProduct == product ) {
//					System.out.println( productString );
					if ( product > maxProduct ) {
						maxProduct = product;
					}
					productString.delete(0, numberOfDigits);
				} else {
					productString.delete(0, numberOfDigits);
				}
			}
		}
		return maxProduct;
	}

}
