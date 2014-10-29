package my.practice.string;

public class StringUtility {
	
	// Assume that the number string is in range and valid
	public static int atoi(String num) {
		int start = 0;
		int sum = 0;
		boolean neg = false;
		
		if ( num.charAt(0) == '-') {
			neg = true;
			start++;
		}
		
		for (int i = start; i < num.length(); i++) {
			sum *= 10;
			sum += num.charAt(i) - '0';
		}
		
		if ( neg )
			return -sum;
		else
			return sum;		
	}

}
