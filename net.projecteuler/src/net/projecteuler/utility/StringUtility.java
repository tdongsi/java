package net.projecteuler.utility;

/**
 * Contains basic string utility functions.
 * 
 * @author tdongsi
 *
 */
public class StringUtility {

	public static void main(String[] args) {

	}
	
	/**
	 * Reverse all characters of the given string
	 * @param input
	 * @return
	 */
	public static String reverseString(String input)
	{
		char[] string = input.toCharArray();
		char[] reverse = new char[string.length];
		for (int i = 0; i < reverse.length; i++) {
			reverse[i] = string[string.length-i-1];
		}
		return new String(reverse);
	}

}
