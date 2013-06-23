/**
 * 
 */
package net.projecteuler.problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Solver class for the problem 18 and 67 in Project Euler.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class WeightedPathFinder {
	
	private static Logger logger = LoggerFactory.getLogger(WeightedPathFinder.class);

	/**
	 * Find the path in depth first search
	 * Brute-force search: scan for all of them.
	 * @param data
	 */
	public int findMaxCost(List<int[]> data) {
		List<int[]> duplicate = new ArrayList<int[]>(data);
		int[] prevRow = new int[duplicate.size()];
		int max = -1000;
		
		for (int i=0; i < duplicate.size(); i++ )
		{
			int[] row = duplicate.get(i);
			for (int j = 0; j < row.length; j++ )
			{
				if ( j == 0 )
				{
					row[j] += prevRow[0];
				} else {
					row[j] += Math.max(prevRow[j-1],prevRow[j]);
				}
				
				// Update max
				if ( row[j] >= max )
				{
					max = row[j];
				}
			}
			
			// Copy row to prevRow
			for (int j = 0; j < row.length; j++) {
				prevRow[j] = row[j];
			}
			
			if ( logger.isDebugEnabled() )
			{
				logger.debug(Arrays.toString(prevRow));
			}
		}

		for (int j = 0; j < prevRow.length; j++) {
			if (max <= prevRow[j] )
			{
				max = prevRow[j];
			}
		}

		return max;
	}
	
}
