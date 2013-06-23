package net.projecteuler.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;

/**
 * Work out the first ten digits of the sum of the following one-hundred 50-digit numbers.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0013 {

	public static void main(String[] args) {
		BigInteger[] numbers = new BigInteger[100];
		BigInteger sum = BigInteger.ZERO;
		
		// Smarter solution: you only care about the first 11 digits
		long[] numbersFirstDigits = new long[100];
		long sumFirstDigits = 0;
		
		try {
			BufferedReader in = new BufferedReader( 
					new FileReader( "data/Problem0013_input.txt" ) );
			
			try {
				String line = null;
				
				for ( int count = 0; (line = in.readLine()) != null; count++ ) {
//					System.out.println( line );
					
					numbers[count] = new BigInteger( line );
					numbersFirstDigits[count] = new Long( line.substring(0, 11) );
					
					sum = sum.add( numbers[count]);
					sumFirstDigits += numbersFirstDigits[count];
				}
				
			}
			finally {
				in.close();
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		System.out.println( sum );
		System.out.println( sumFirstDigits );

	}

}
