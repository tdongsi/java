package my.practice.string;

public class StringUtility {
	
	// Assume that the number string is in range and valid
	public static int atoi(String num) {
		char[] numChar = num.toCharArray();
		boolean negative = false;
		int start = 0;
		
		if (numChar[0] == '-') {
			negative = true;
			start = 1;
		}
		
		int sum = 0;
		for ( int idx = start; idx < numChar.length; idx++ ) {
			sum *= 10;
			sum += numChar[idx] - '0';
		}
		if (negative) {
			sum = -sum;
		}
		
		return sum;
	}
	
	public static boolean hasSubstring( String str, String sub ) {
		
		if ( sub == null || sub.equals("") ) {
			return true;
		}
		
		if ( str == null ) {
			return false;
		}
		
		for (int i = 0; i < str.length()-sub.length()+1; i++) {
			boolean flag = true;
			for (int j = 0; j < sub.length(); j++) {
				if ( str.charAt(i+j) != sub.charAt(j) ) {
					flag = false;
					break;
				}
			}
			
			if (flag) {
				return true;
			}
		}
		
		return false;
	}

}
