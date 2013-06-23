/**
 * 
 */
package net.projecteuler.problems;

import java.util.HashSet;
import java.util.LinkedList;

import net.projecteuler.utility.NumberUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find the sum of all the positive integers which cannot be written as the sum of two abundant numbers.
 *  
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0023 {
	
	private static Logger logger = LoggerFactory.getLogger(Problem0023.class);

	public static void main(String[] args) {
		
		Problem0023 solver = new Problem0023();
		solver.solve();

	}

	private void solve() {
		
		logger.debug("Solving problem 23");
		
		// all integers greater than 28123 can be written as the sum of two abundant numbers
		int maxNumber = 28123;
		LinkedList<Integer> abundantNumList = new LinkedList<Integer>(); 
		
		// Find all the abundant numbers
		abundantNumList.add(12);
		for (int i = 13; i <= maxNumber; i++ )
		{
			if ( NumberUtility.isAbundantNumber(i) )
			{
				logger.trace("Abundant number: {}", i);
				abundantNumList.add(i);
			}
		}
		
		// initialize the sum to the sum of all numbers from 1 to 28123
		long sum = (maxNumber+1) * maxNumber / 2;
		
		Integer[] abundantNumArray = abundantNumList.toArray( new Integer[0] );
		HashSet<Integer> wrongNumSet = new HashSet<Integer>();
		long sumWrongNum = 0;
		
		for (int i = 0; i < abundantNumArray.length; i++) {
			for (int j = 0; j < abundantNumArray.length; j++) {
				int check = abundantNumArray[i].intValue() + abundantNumArray[j].intValue();
				
				if ( check <= maxNumber && !wrongNumSet.contains(check) )
				{
					sumWrongNum += check;
					wrongNumSet.add(check);
					
					logger.trace("Remove the number: {}", check);
				}
				
				if ( check > maxNumber )
				{
					break;
				}
			}
		}

		logger.debug("Before removing wrong numbers: {}", sum);
		sum -= sumWrongNum;
		logger.info("The sum is: {}", sum);

	}

}
