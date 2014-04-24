package com.google.codejam.y2008;

import java.util.Arrays;

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
	 * @param height how high each type of flower grows
	 * @param bloom the morning that each type of flower springs from the ground
	 * @param wilt the evening that each type of flower shrivels up and dies
	 * @return the elements of height in the order you should plant your flowers to achieve no blocking
	 */
	public static int[] getOrdering(int[] height, int[] bloom, int[] wilt) {
		int[] order = new int[height.length];
		
		return order;
	}

}
