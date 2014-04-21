package com.google.codejam.y2008;

/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=1259&rd=4493
 * 
 * A sequence of numbers is called a zig-zag sequence if the differences between 
 * successive numbers strictly alternate between positive and negative. 
 * The first difference (if one exists) may be either positive or negative. 
 * A sequence with fewer than two elements is trivially a zig-zag sequence.
 * 
 * Given a sequence of integers, sequence, return the length of the longest subsequence of sequence that is a zig-zag sequence.
 * 
 * Problem constraints:
-	sequence contains between 1 and 50 elements, inclusive.
-	Each element of sequence is between 1 and 1000, inclusive
 *
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class ZigZag {

	/**
	 * Example of usage
	 */
	public static void main(String[] args) {
		
		int length = ZigZag.longestZigZag(new int[]{ 1, 7, 4, 9, 2, 5 });
		// Expect 6
		System.out.println(length);
		
		length = ZigZag.longestZigZag(new int[]{ 1, 17, 5, 10, 13, 15, 10, 5, 16, 8  });
		// Expect 7
		System.out.println(length);
		
		length = ZigZag.longestZigZag(new int[]{ 70, 55, 13, 2, 99, 2, 80, 80, 80, 80, 100, 19, 7, 5, 5, 5, 1000, 32, 32 });
		// Expect 8
		System.out.println(length);

	}

	public static int longestZigZag(int[] sequence) {
		// TODO Auto-generated method stub
		return 0;
	}

}
