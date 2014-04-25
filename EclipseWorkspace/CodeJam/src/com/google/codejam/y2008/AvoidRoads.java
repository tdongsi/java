package com.google.codejam.y2008;

import java.util.Arrays;
import java.util.List;

/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=1889&rd=4709
 * 
 * You are standing at the corner with coordinates 0,0. 
 * Your destination is at corner width,height. 
 * You will return the number of distinct paths that lead to your destination. 
 * Each path must use exactly width+height blocks. 
 * In addition, the city has declared certain street blocks untraversable. 
 * These blocks may not be a part of any path. 
 * You will be given a String[] bad describing which blocks are bad. 
 * If (quotes for clarity) "a b c d" is an element of bad, it means the block from corner a,b to corner c,d is untraversable.
 * 
 * Each element of the bad will be in the format "a b c d" where,
 * a,b,c,d are integers with no extra leading zeros,
 * a and c are between 0 and width inclusive,
 * b and d are between 0 and height inclusive,
 * and a,b is one block away from c,d. 
 * 
 * @author tdongsi
 *
 */
public class AvoidRoads {

	/**
	 * Example of usage
	 */
	public static void main(String[] args) {
		
		long ways = AvoidRoads.numWays(6,6,new String[]{"0 0 0 1","5 6 6 6"});
		System.out.println(ways);
	}

	public static long numWays(int width, int height, String[] bad) {
		// TODO: Modify String[] bad such that c, d is top-right from a,b
		return numWaysStandard(width,height,Arrays.asList(bad));
	}

	/**
	 * Naive implementation of 2D dynamic programming.
	 * Additional constraint for List<String> bad: c, d is top-right from a,b. "6 6 5 6" is illegal, while "5 6 6 6" is legal.
	 * 
	 * @param width
	 * @param height
	 * @param bad Each element of the bad will be in the format "a b c d" where c, d is top-right from a,b
	 * @return
	 */
	private static long numWaysStandard(int width, int height, List<String> bad) {
		long[][] maxPath = new long[width+1][height+1];
		maxPath[0][0] = 1;
		
		for (int i = 0; i <= width; i++) {
			for (int j = 0; j <= height; j++) {
				
				String route1 = String.format("%d %d %d %d", i-1, j, i, j);
				if ( i > 0 && !bad.contains(route1)) {
					maxPath[i][j] += maxPath[i-1][j];
				}
				
				String route2 = String.format("%d %d %d %d", i, j-1, i, j);
				if ( j > 0 && !bad.contains(route2)) {
					maxPath[i][j] += maxPath[i][j-1];
				}
				
			}
			
		}
		
		return maxPath[width][height];
	}
	
	/**
	 * @param width
	 * @param height
	 * @param bad Each element of the bad will be in the format "a b c d" where c, d is top-right from a,b
	 * @return
	 */
	private static long numWaysFast(int width, int height, String[] bad) {
		long[][] maxPath = new long[2][height+1];
		
		return 0;
	}

}
