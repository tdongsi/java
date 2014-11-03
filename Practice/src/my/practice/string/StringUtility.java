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
	
	public static boolean hasSubstring( String str, String sub ) {
		
		if ( str.length() == 0 && sub.length() == 0 ) {
			return true;
		}
		
		for (int i = 0; i < str.length(); i++) {
			int j = 0;
			
			for ( j = 0; j < sub.length(); j++ ) {
				if ( str.charAt(i+j) != sub.charAt(j) ) {
					break;
				}
			}
			
			// j reaches full-length of sub
			if ( j == sub.length() ) {
				return true;
			}
		}
		
		return false;
	}

}
