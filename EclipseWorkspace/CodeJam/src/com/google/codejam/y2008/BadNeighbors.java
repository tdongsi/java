package com.google.codejam.y2008;

import java.util.Arrays;
import java.util.Collections;

/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=2402&rd=5009
 * 
 * Every resident hates his next-door neighbors on both sides. 
 * the town has been arranged in a big circle around the well.
 * 
 * Each of the town's residents is willing to donate a certain amount, as specified in the int[] donations, which is listed in clockwise order around the well. 
 * However, nobody is willing to contribute to a fund to which his neighbor has also contributed. 
 * Next-door neighbors are always listed consecutively in donations, except that the first and last entries in donations are also for next-door neighbors. 
 * You must calculate and return the maximum amount of donations that can be collected.
 *  
Constraints
-	donations contains between 2 and 40 elements, inclusive.
-	Each element in donations is between 1 and 1000, inclusive.
 * 
 * @author cuongd
 *
 */
public class BadNeighbors {

	/**
	 * Example of usage
	 */
	public static void main(String[] args) {
		
		int amount = BadNeighbors.maxDonations(new int[]{ 10, 3, 2, 5, 7, 8 });
		System.out.println(amount);

	}
	
	
	/**
	 * Each of the town's residents is willing to donate a certain amount, as specified in the int[] donations, which is listed in clockwise order around the well. 
	 * However, nobody is willing to contribute to a fund to which his neighbor has also contributed. 
	 * Next-door neighbors are always listed consecutively in donations, except that the first and last entries in donations are also for next-door neighbors. 
	 * You must calculate and return the maximum amount of donations that can be collected.
	 * 
	 * @param donations: list of donate amount by residents
	 * @return Maximum amount of donations that can be collected
	 */
	public static int maxDonations(int[] donations)
	{
		if (donations.length == 0)
			return 0;
		if (donations.length == 1)
			return donations[0];
		
		int[] maxAmount = donations.clone();
		boolean[] firstIncluded = new boolean[donations.length];
		firstIncluded[0] = true;
		
		for (int i = 2; i < maxAmount.length; i++) {
			for (int j = 0; j < i-1; j++) {
				int temp = donations[i] + maxAmount[j];
				
				// The last element is neighbor to the first element
				if ( i == maxAmount.length-1 && firstIncluded[j]) {
					temp = donations[i];
				}
				
				if ( temp > maxAmount[i]) {
					maxAmount[i] = temp;
					firstIncluded[i] = firstIncluded[j];
				}
			}
		}
		System.out.println(Arrays.toString(firstIncluded));
		System.out.println(Arrays.toString(maxAmount));
		
		// The maximum may be not the last one maxAmount
		Arrays.sort(maxAmount);
		return maxAmount[maxAmount.length-1];
	}

}
