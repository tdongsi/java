package net.projecteuler.problems;

import java.io.*;

/**
 * Find the greatest product of five consecutive digits in the 1000-digit number.
 * 
 * @author tdongsi
 *
 */
public class Problem0008 {

	public static void main(String[] args) throws IOException {
		FileReader in = new FileReader( "data/Problem0008_in.txt" );
		
		final int range = 5;
		int ch;
		int count;
		long product = 1;
		long maxProduct;
		int[] currentGroup = new int[range];
		
		// Find the first five digits
		for ( count=0; count < range && (ch=in.read()) != -1; count++  ) {			
			currentGroup[count%range] = Character.digit( (char) ch, 10 );
		}
		
		// Initialize the current max
		product = productOfArray( currentGroup );
		maxProduct = product;
		System.out.println( "product: " + product );
		
		for ( count=range; (ch=in.read()) != -1; count++ ) {
			if ( Character.isDigit( ch ) ) {
				currentGroup[count%range] = Character.digit( (char) ch, 10 );
				
				// Dividing the current 'product' by the first in the previous group
				// and multiplying it with the latest digit maybe faster
				// Only worthwhile if 'range' is 50 instead of 5 
				
				// Use the simple way for clarity
				product = productOfArray( currentGroup );
				if ( product > maxProduct ) {
					maxProduct = product;
				}
			}
		}
		
		System.out.println( "The greatest product is " + maxProduct );
		in.close();
		
	}
	
	private static long productOfArray( int[] group ){
		long product = 1;
		for ( int i=0; i < group.length; i++ ) {
			product *= group[i];
		}
		return product;
	}

}
