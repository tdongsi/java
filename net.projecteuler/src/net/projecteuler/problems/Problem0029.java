/**
 * 
 */
package net.projecteuler.problems;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * How many distinct terms are in the sequence generated by a^b for 2 <= a <= 100 and 2 <= b <= 100?
 * 
 * @author tdongsi
 *
 */
public class Problem0029 {

	public static void main(String[] args) {
		final int RANGE = 100;
		
		Set<BigInteger> set = new HashSet<BigInteger>(RANGE*RANGE);
		
		for (int a = 2; a <= RANGE; a++ )
		{
			for (int b = 2; b <= RANGE; b++) {
				BigInteger bigA = BigInteger.valueOf(a);
				set.add(bigA.pow(b));
			}
		}
		
		System.out.println("Answer: " + set.size());
	}

}
