package net.projecteuler.utility;

/**
 * Contains basic string utility functions.
 * 
 * @author tdongsi
 *
 */
public class StringUtility {
	
	/**
	 * Enforce that this class should not be instantiated.
	 * Suppress default constructor.
	 */
	private StringUtility()
	{
		throw new AssertionError();
	}

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
	
	
	/**
	 * Check if the input string is a palindrome
	 * A palindrome is a string which remains unchanged even after reversing
	 */
	public static boolean isPalindrome(String input)
	{
		char[] string = input.toCharArray();
		for (int i = 0; i < string.length/2; i++) {
			if ( string[i] != string[string.length-1-i]) {
				return false;
			}
		}
		return true;
	}

}
