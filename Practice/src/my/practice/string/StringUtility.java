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
	
	public static String encoding(String input) {
		
		if (input.length() <= 1) {
			return input;
		}
		
		char[] charIn = input.toCharArray();
		StringBuilder builder = new StringBuilder(input.length());
		
		char cur = charIn[0];
		int count = 1;
		
		for (int i = 1; i < charIn.length; i++) {
			if (charIn[i] == cur) {
				count++;
			} else {
				if (count == 1) {
					builder.append(cur);
				} else {
					builder.append(cur);
					builder.append(Integer.toString(count));
				}
				
				cur = charIn[i];
				count = 1;
			}
		}
		
		// Append the last character run
		if (count == 1) {
			builder.append(cur);
		} else {
			builder.append(cur);
			builder.append(Integer.toString(count));
		}
		
		return builder.toString();
	}

}
