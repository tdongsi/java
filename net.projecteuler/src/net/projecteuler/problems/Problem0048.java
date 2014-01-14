package net.projecteuler.problems;

import java.math.BigInteger;

/**
 * The series, 1^1 + 2^2 + 3^3 + ... + 10^10 = 10405071317.
 * Find the last ten digits of the series, 1^1 + 2^2 + 3^3 + ... + 1000^1000
 * 
 * @author cuongd
 *
 */
public class Problem0048 {
	
	public static void main(String[] args) {
		long answer =  Problem0048.lastDigitsOfSelfPowerSeries(1000, 10);
		
		System.out.println("Answer: " + String.format("%010d", answer));
	}
	
	
	/**
	 * The last digitCount digits of the series sum(k^k) for k from 1 to number.
	 * 
	 * @param number: the length of the self power series
	 * @param digitCount: the number of digits
	 * @return the integer that represents the digits (no leading zeros)
	 */
	public static long lastDigitsOfSelfPowerSeries( int number, int digitCount )
	{
		BigInteger mod = BigInteger.TEN.pow(digitCount);
		
		long sum = 1;
		BigInteger count = BigInteger.ONE;
		BigInteger temp;
		
		for (int i=2; i <= number; i++ )
		{
			count = count.add(BigInteger.ONE);
			
			temp = count.modPow(count, mod);
			
			sum += temp.longValue();
			sum %= mod.longValue();
		}
		
		return sum;
	}

}
