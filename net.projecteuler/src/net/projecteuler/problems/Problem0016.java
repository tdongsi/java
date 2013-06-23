package net.projecteuler.problems;

import java.math.BigInteger;


/**
 * Problem 16: What is the sum of the digits of the number 2^1000?
 * Problem 20: Find the sum of the digits in the number 100!.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0016 {

	/**
	 * Let the computer do the computation.
	 */
	public static void main(String[] args) {
		// Problem 16
		BigInteger number = BigInteger.ONE.shiftLeft(1000);
		String numberString = number.toString();
		long sum = 0;
		int digit;
		
		for ( int i=0; i < numberString.length(); i++ ) {
			digit = Character.digit(numberString.charAt(i), 10);
			sum += digit;
		}
		
		System.out.println( "sum " + sum );
		
		// Problem 20
		BigInteger factorial = BigInteger.ONE;
		BigInteger curr = BigInteger.ONE;
		for (int i=2; i <= 100; i++ ) {
			curr = curr.add(BigInteger.ONE);
			factorial = factorial.multiply(curr);
		}
		
		char[] digits = factorial.toString().toCharArray();
		
		sum = 0;
		for ( int i=0; i < digits.length; i++ ) {
			sum += (digits[i] - '0'); 
		}
		
		System.out.println( "sum " + sum );
	}

}
