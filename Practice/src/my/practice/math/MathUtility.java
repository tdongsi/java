package my.practice.math;

public class MathUtility {
	
	private MathUtility() {
		throw new AssertionError();
	}
	
	public static long gcd(long a, long b) {
		while (a % b != 0 ) {
			long temp = a;
			a = b;
			b = temp % b;
		}
		return b;
	}
	
	/**
	 * Recursive version: easier to remember
	 */
	public static long gcd_recursive(long a, long b) {
		// NOTE: a does not have to be larger than b
		if ( b == 0 ) {
			return a;
		} else {
			return gcd_recursive(b, a%b);
		}
	}
	
	public static long lcm(long a, long b) {
		return (a/gcd(a,b))*b;
	}
}
