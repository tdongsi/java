/**
 * 
 */
package net.projecteuler.problems;

import java.util.Arrays;

/**
 * How many different ways can 2 pounds be made using any number of coins?
 * 
 * Mathematical analysis:
 * It can be proven that number of combinations for A with that list == 
 * SUM of (number of combinations for A-D with that list up to D) 
 * for D is the values from first to last of that list
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0031 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Problem0031 solver = new Problem0031();
		
		int[] currencyCoins = {1, 2, 5, 10, 20, 50, 100, 200};
		
		int numberOfCombinations = solver.numberOfCombinations(200, currencyCoins);
		
		System.out.println("Answer: " + numberOfCombinations );

	}
	
	/**
	 * Find the number of combinations that make the given number
	 * from the given coins in the currencyCoins list.
	 * 
	 * By recursion: number of combinations for A with that list == 
	 * SUM of (number of combinations for A-D with that list up to D) 
	 * for D is the values from first to last of that list
	 * 
	 * @param number
	 * @return
	 */
	public int numberOfCombinations(int number, int[] arrayValues)
	{
		if ( number == 0 )
		{
			return 1;
		} else if ( number == arrayValues[0] )
		{
			return 1;
		} else if ( arrayValues.length == 1 )
		{
			if ( number % arrayValues[0] != 0 )
			{
				throw new IllegalArgumentException("Unable to use the given nomination to compose the value");
			} else
			{
				return 1;
			}
		} else 
		{
			int count = 0;
			for ( int i=0; i < arrayValues.length && arrayValues[i] <= number; i++ )
			{
				count += numberOfCombinations(number - arrayValues[i], Arrays.copyOfRange(arrayValues, 0, i+1));
			}
			
			return count;
		}
	}

}
