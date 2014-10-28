package my.practice.math;

public class MathUtility {
	
	private MathUtility() {
		throw new AssertionError();
	}
	
	public static long gcd(long a, long b) {
		if ( a < b ) {
			long temp = b;
			b = a;
			a = temp;
		}
		
		if ( b == 0 ) {
			return a;
		} else {
			return gcd(b, a%b);
		}
	}
	
	public static long lcm(long a, long b) {
		return (a/gcd(a,b))*b;
	}
}
