package net.projecteuler.problems;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Problem0017 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<Integer,Integer> charLength = new HashMap<Integer,Integer>();
		
		// Read string length from file
		try {
			BufferedReader in = new BufferedReader( 
					new FileReader( "data/Problem0017_numbers.txt" ) );
			
			try {
				String line = null;
				
				// read from 1-20
				for ( int count = 1; count <=20 && (line = in.readLine()) != null; count++ ) {
					// System.out.println( line + " " + count );
					charLength.put(count, line.length());
				}
				
				// read from 30 - 90
				for ( int count = 30; count <= 90 && (line = in.readLine()) != null; count += 10) {
					// System.out.println( line + " " + count);
					charLength.put(count, line.length());
				}
				
			}
			finally {
				in.close();
			}
		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//////////////////////////////////////////////////////////////////
		/////////////////////////////////////////////////////////////////
		
		long countOneToNine = 0;
		long countOneTo99 = 0;
		long count_20_to_90_by_10 = 0;
		long count = 0;
		
		for ( int i=1; i <= 9; i++ ) {
			countOneToNine += charLength.get(i);
		}
		
		for ( int i=20; i <= 90; i += 10 ) {
			count_20_to_90_by_10 += charLength.get(i);
		}
		
		// count characters from 1-99
		// start by counting characters from 10-20
		for ( int i = 10; i <= 19; i++ ) {
			countOneTo99 += charLength.get(i);
		}
		
		countOneTo99 += count_20_to_90_by_10*10;
		countOneTo99 += countOneToNine*9;
		
		// count characters for 1000
		count += 3; // for 'one'
		count += 8; // for 'thousand'
		// count characters from 1-999
		// 7 for 'hundred'
		count += (countOneToNine*100 + 7*900 + countOneTo99*10);
		// count characters from 'and's
		count += 3*99*9;
		
		System.out.println( "count 1-9: " + countOneToNine);
		System.out.println( "count 1-99: " + countOneTo99 );
		System.out.println( "count " + count);
	}

}
