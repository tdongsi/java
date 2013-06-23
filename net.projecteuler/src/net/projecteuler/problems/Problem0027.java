/**
 * 
 */
package net.projecteuler.problems;

import java.util.List;

import net.projecteuler.utility.BitSetPrimeSieve;
import net.projecteuler.utility.PrimeSieve;

/**
 * Considering quadratics of the form:
 * n^2 + an + b, where |a| < 1000 and |b| < 1000
 * Find the product of the coefficients, a and b, for the quadratic expression 
 * that produces the maximum number of primes for consecutive values of n, starting with n = 0.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0027 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		final int RANGE = 1000;
		
		Problem0027 solver = new Problem0027(RANGE);
		int[] answer = solver.findParameters();
		
		System.out.println( "Parameters: " + answer[0] + " " + answer[1] );
		System.out.println( "Answer: " + (answer[0]*answer[1]));
	}
	
	/**
	 * The constraint on parameters: |a| < range, |b| < range
	 */
	private int range;
	private PrimeSieve sieve;
	
	/**
	 * Construct the solver based on the given value range
	 * of the parameters a and b
	 * 
	 * @param range
	 */
	public Problem0027(int range) {
		this.range = range;
		this.sieve = new BitSetPrimeSieve(range*range);
	}
	
	public int[] findParameters()
	{
		int[] parameters = new int[2];
		
		List<Integer> primes = sieve.getList();
		int maxLength = -1;
		
		for (int b : primes) {
			if ( b > range )
			{
				break;
			} else {
				int aRange;		// lower bound of a
				if ( range % 2 == 0 )
				{
					aRange = -range+1;
				} else {
					aRange = -range+2;
				}
				
				for ( int a = aRange; a < range; a+= 2 )
				{
					int length = findSeriesLength(a, b);
					
					if ( length > maxLength )
					{
						maxLength = length;
						parameters[0] = a;
						parameters[1] = b;
					}
				}
			}
		}
		
		return parameters;
	}


	/**
	 * Find the number of primes for consecutive values of n, from n=0,
	 * that the formula in computeFormula can produce, parameterized by a and b.
	 * Example: for the formula (n*n + a*n + b), the returned integer should be less than b.
	 * 
	 * @param a formula parameter
	 * @param b formula parameter
	 * @return
	 */
	public int findSeriesLength(int a, int b)
	{
		int n = 0;
		
		while ( sieve.isPrime(computeFormula(a, b, n)))
		{
			n++;
		}
		
		return n;
	}
	
	private int computeFormula(int a, int b, int n)
	{
		return (n*n + a*n + b);
	}

}
