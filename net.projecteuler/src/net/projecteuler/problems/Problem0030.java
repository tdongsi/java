/**
 * 
 */
package net.projecteuler.problems;

/**
 * Find the sum of all the numbers that can be written as the sum of fifth powers of their digits.
 * 
 * Mathematical analysis:
 * The numbers cannot have >= 7 digits since if they do, 
 * the largest possible power sum of their digits is 7*9^5 < 1000000
 * 
 * NOTE: This solution may be not robust for larger problems.
 * 
 * @author tdongsi
 *
 */
public class Problem0030 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		final int RANGE = 999999;
		final int POWER = 5;
		
//		// Test
//		final int RANGE = 9999;
//		final int POWER = 4;
		
		Problem0030 solver = new Problem0030(POWER);
		int sum = 0;
		
		for ( int number = 10; number <= RANGE; number++ )
		{
			int digitSum = solver.computePowerSumOfDigits(number);
			if ( digitSum == number )
			{
				sum += number;
				System.out.println( "Found the number: " + number );
			}
		}
		
		System.out.println("Answer: " + sum );
	}
	
	
	/**
	 * Pre-computed power of digits: 0^power ... 9^power.
	 */
	private int[] powerOfDigits;
	
	/**
	 * @param power
	 */
	public Problem0030(int power) {
		super();
		
		powerOfDigits = new int[10];
		for (int i = 0; i < powerOfDigits.length; i++ )
		{
			powerOfDigits[i] = (int) Math.pow(i, power);
		}
	}
	
	public int computePowerSumOfDigits(int number)
	{
		int sum = 0;
		final int DECIMAL = 10;
		
		while ( number > 0 )
		{
			sum += powerOfDigits[(number % DECIMAL)];
			number /= DECIMAL;
		}
		
		return sum;
	}
	
	 

}
