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

	/**
	 * A sequence of numbers is called a zig-zag sequence if the differences between successive numbers strictly alternate between positive and negative.
	 * Given a sequence of integers, return the length of the longest subsequence that is a zig-zag sequence. 
	 * A subsequence is obtained by deleting some number of elements (possibly zero) from the original sequence, leaving the remaining elements in their original order.
	 * 
	 * @param sequence
	 * @return
	 */
	public static int longestZigZag(int[] sequence)
	{
		if ( sequence.length == 1 )
			return 1;
		
		int[] diff = new int[sequence.length-1];
		
		for (int i = 0; i < diff.length; i++) {
			diff[i] = (int)Math.signum(sequence[i+1] - sequence[i]);
		}
		
//		System.out.println(Arrays.toString(diff));
		
		// Solve the problem using dynamic programming
		int[] maxLength = new int[diff.length];
		maxLength[0] = 1;
		
		for (int i = 1; i < maxLength.length; i++) {
			// initialize maxLength
			maxLength[i] = maxLength[i-1];
			
			for (int j = 0; j < i; j++) {
				int temp = 0;
				
				if ( diff[i] != 0 && diff[j] == -diff[i] )
				{
					temp = maxLength[j] + 1;
				}
				
				if ( temp > maxLength[i])
				{
					maxLength[i] = temp;
				}
			}
		}
		
		return  maxLength[maxLength.length-1]+1;
	}

	/**
	 * Given a sequence of integers, return the length of the longest subsequence that is of same signum (all 1 or -1). 
	 * 
	 * @param sequence: a sequence of integers either 1, -1, or 0.
	 * @return
	 */
	public static int longestSameSignum(int[] sequence) {
		
		int[] signumSequence = new int[sequence.length];
		for (int i = 0; i < signumSequence.length; i++) {
			signumSequence[i] = (int) Math.signum(sequence[i]);
		}
		
		int[] maxLength = new int[signumSequence.length];
		maxLength[0] = 1;
		
		for (int i = 1; i < maxLength.length; i++) {
			// initialize maxLength
			maxLength[i] = maxLength[i-1];
			
			for (int j = 0; j < i; j++) {
				int temp = 0;
				
				if ( signumSequence[i] != 0 && signumSequence[j] == signumSequence[i] )
				{
					temp = maxLength[j] + 1;
				}
				
				if ( temp > maxLength[i])
				{
					maxLength[i] = temp;
				}
			}
		}
		
//		System.out.println(Arrays.toString(sequence));
//		System.out.println(Arrays.toString(maxLength));
		
		return maxLength[maxLength.length-1];
	}

}
