package com.google.codejam.y2008;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http://community.topcoder.com/stat?c=problem_statement&pm=1918&rd=5006
 * 
 * You will be given a int[] height, a int[] bloom, and a int[] wilt. 
 * Each type of flower is represented by the element at the same index of height, bloom, and wilt. 
 * height represents how high each type of flower grows, 
 * bloom represents the morning that each type of flower springs from the ground, 
 * and wilt represents the evening that each type of flower shrivels up and dies. 
 * Each element in bloom and wilt will be a number between 1 and 365 inclusive, and wilt[i] will always be greater than bloom[i]. 
 * You must plant all of the flowers of the same type in a single row for appearance, and you also want to have the tallest flowers as far forward as possible. 
 * However, if a flower type is taller than another type, and both types can be out of the ground at the same time, 
 * the shorter flower must be planted in front of the taller flower to prevent blocking. 
 * A flower blooms in the morning, and wilts in the evening, so even if one flower is blooming on the same day another flower is wilting, one can block the other.
 *  
 * You should return a int[] which contains the elements of height in the order you should plant your flowers to acheive the above goals. 
 * The front of the garden is represented by the first element in your return value, and is where you view the garden from. 
 * The elements of height will all be unique, so there will always be a well-defined ordering.
 * 
 * @author tdongsi
 *
 */
public class FlowerGarden {
	
	private static Logger logger = LoggerFactory.getLogger(FlowerGarden.class);

	/**
	 * Example of usage
	 */
	public static void main(String[] args) {
		
		int[] height = new int[]{1,2,3,4,5,6};
		int[] bloom = new int[]{1,3,1,3,1,3};
		int[] wilt = new int[]{2,4,2,4,2,4};
		
		int[] order = FlowerGarden.getOrdering(height, bloom, wilt);
		System.out.println(Arrays.toString(order));
	}
	
	
	/**
	 * This approach uses dynamic programming with O(n^2) space required.
	 * TODO: Another approach is to sort similar to insertion sort (not tried)
	 * 
	 * @param height how high each type of flower grows
	 * @param bloom the morning that each type of flower springs from the ground
	 * @param wilt the evening that each type of flower shrivels up and dies
	 * @return the elements of height in the order you should plant your flowers to achieve no blocking
	 */
	public static int[] getOrdering(int[] height, int[] bloom, int[] wilt) {
		int len = height.length;
		int[] order = new int[len];
		boolean[] usedFlag = new boolean[len];
		int[][] blocking = new int[len][len];
		
		// Construct the table that represents who blocks who
		for (int i = 0; i < height.length; i++) {
			for (int j = 0; j < height.length; j++) {
				if (j !=i ) {
					blocking[i][j] = isBlocking(
							new int[]{height[i], bloom[i], wilt[i]}, 
							new int[]{height[j], bloom[j], wilt[j]});
				}
			}
		}
		
		for (int i = 0; i < height.length; i++) {
			logger.debug(Arrays.toString(blocking[i]));
		}
		
		// iterate over the length of the result
		for (int k = 0; k < order.length; k++) {
			int maxHeight = -1;
			int maxIndex = 0;
			
			// check if the tree is blocking the rest
			for (int i = 0; i < len; i++) {
				
				if (!usedFlag[i])
				{
					boolean blockFlag = false;
					
					// TODO: this loop can be avoided if we maintain an array 
					// containing sum of rows of blocking[i][j]
					for (int j = 0; j < len; j++) {
						// skipped if tree j has been used
						if ( blocking[i][j] != 0 ) {
							// tree i blocking some tree j
							blockFlag = true;
							break;
						}
					}
					
					// if the current tree is NOT blocking any tree
					if (!blockFlag && height[i] > maxHeight) {
						maxHeight = height[i];
						maxIndex = i;
					}
					
				}
				
			}
			
			logger.debug( Integer.toString(maxIndex) );
			// push into the order
			order[k] = maxHeight;
			// remove the tree type
			usedFlag[maxIndex] = true;
			// the rest of the trees should not block this tree
			for (int i = 0; i < len; i++) {
				blocking[i][maxIndex] = 0;
			}
			
			for (int i = 0; i < height.length; i++) {
				logger.debug(Arrays.toString(blocking[i]));
			}
		}
		
		
		return order;
	}


	/**
	 * Use integer instead of boolean in case of more than binary states needed
	 * 
	 * @param iTree array of numbers representing height, bloom time, and wilt time for tree i.
	 * @param jTree array of numbers representing height, bloom time, and wilt time for tree i.
	 * @return 1 if iTree is blocking jTree, 0 if iTree otherwise.
	 */
	private static int isBlocking(int[] iTree, int[] jTree) {
		
		if (iTree[0] < jTree[0]) {
			// if height is smaller, not blocking no matter what
			return 0;
		} else {
			// if height of i > height of j
			if ( (iTree[1] >= jTree[1] && iTree[1] <= jTree[2])
					|| (jTree[1] >= iTree[1] && jTree[1] <= iTree[2])) {
				// if two time intervals overlap
				return 1;
			} else {
				return 0;
			}
		}
	}

}
