/**
 * 
 */
package net.projecteuler.problems;

import java.math.BigInteger;

/**
 * What is the first term in the Fibonacci sequence to contain 1000 digits?
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0025 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Problem0025 solver = new Problem0025();
		
		int answer = solver.findFibonacci(1000);
		System.out.println("Answer is: " + answer );
	}
	
	/**
	 * Find the order number of the first Fibonacci number
	 * that has the length of the given length limit.
	 * 
	 * @param lengthLimit
	 * @return
	 */
	public int findFibonacci( int lengthLimit )
	{
		BigInteger f1 = BigInteger.ONE;
		BigInteger f2 = BigInteger.ONE;
		int termCounter = 2;
		
		while ( f2.toString().length() < lengthLimit )
		{
			f2 = f2.add(f1);
			f1 = f2.subtract(f1);
			termCounter++;
		}
		
//		System.out.println("Answer is: " + f2 );
		return termCounter;
	}

}
