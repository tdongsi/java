package com.google.codejam.y2008;

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
		
		long ways = AvoidRoads.numWays(6,6,new String[]{"0 0 0 1","6 6 5 6"});
		System.out.println(ways);
	}

	public static long numWays(int width, int height, String[] bad) {
		return numWaysStandard(width,height,bad);
	}

	private static long numWaysStandard(int width, int height, String[] bad) {
		return 0;
	}
	
	private static long numWaysFast(int width, int height, String[] bad) {
		// TODO Auto-generated method stub
		return 0;
	}

}
