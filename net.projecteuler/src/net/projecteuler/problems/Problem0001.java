package net.projecteuler.problems;

/**
 * Find the sum of all the multiples of 3 or 5 below 1000.
 * 
 * @author tdongsi
 *
 */
public class Problem0001 {

	/**
	 * Solve the problem by using the closed-form solution in sumSeries() method
	 */
	public static void main(String[] args) {
		long max = 999; 
		
		long sumOfThree = sumSeries( 3, (max/3)*3, 3 );
		long sumOfFive = sumSeries( 5, (max/5)*5, 5 );
		long sumOfFifteen = sumSeries( 15, (max/15)*15, 15);
		long total = sumOfThree + sumOfFive - sumOfFifteen;
		
		System.out.println( "The sum is " + total);
	}
	
	
	/**
	 * @param start: the starting term of the sum series
	 * @param end: the ending term of the sum series
	 * @param step: the step, i.e. the difference between consecutive terms
	 * @return the sum of all the terms in the series
	 */
	private static long sumSeries( long start, long end, long step )
	{
		// Using the closed form sum = step*n(n+1)/2 where n is the number of terms in the series
		
		// start is the first valid number
		long numberCount = (end-start)/step + 1;
		long sum = (step*numberCount*(numberCount+1))/2;
		
		return sum;
	}

}
