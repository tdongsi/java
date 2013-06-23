/**
 * 
 */
package net.projecteuler.problems;

/**For all the odd integer n:
 * From the center 1
 * 
 * To the lower left corner, the pattern is:
 * 1, 2^2 +1, 4^2+1, ..., (n-1)^2 +1
 * 
 * To the upper right corner, the pattern is:
 * 1, 3^2, 5^2, ..., n^2
 * 
 * The closed-form solution of the main diagonal:
 * n(n+1)(2n+1)/6 + n - 1 - (n^2+1)/2
 * 
 * To the upper left corner, the pattern is:
 * 1, 3^2-3+1, 5^2-5+1, ..., n^2 -n +1
 * Similarly, from the center to lower right corner
 * 
 * The closed-form solution of the minor diagonal:
 * n(n+1)(2n+1)/6 + - n(n+1)/2 + n
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0028 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int number = 1001;
		
		long temp = number*(number+1)*(2*number+1)/3 - (number*number+1)/2 + number - 1;
		System.out.println("Answer: " + temp);
	}

}
