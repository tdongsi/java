package net.projecteuler.problems;

import java.io.*;
import java.util.*;

/**
 * What is the greatest product of four adjacent numbers in the same direction 
 * (up, down, left, right, or diagonally) in the 2020 grid?
 * 
 * NOTE: Old-style Java. Scanner should be used instead of StringTokenizer.
 * 
 * @author tdongsi
 *
 */
public class Problem0011 {
	
	private static final int SIZE = 20;
	private static final int GROUP_SIZE = 4;

	public static void main(String[] args) {
		
		int[][] table = new int[ SIZE][ SIZE];
		
		readTableFromFile( table, "data/Problem0011_input.txt" );
		
		int maxNumber = -1;
		int maxX = -1;
		int maxY = -1;
		int scanNumber = -1;
		
		// Scan the up-down groups. 3 is GROUP_SIZE-1. 
		for ( int i = 0; i + GROUP_SIZE-1 <  SIZE; i++ ) {
			for ( int j = 0; j <  SIZE; j++ ) {
				int product = 1;
				for (int k = 0; k < GROUP_SIZE; k++ )
				{
					product *= table[i+k][j];
				}
				
				if ( product > maxNumber ) {
					maxNumber = product;
					maxX = i;
					maxY = j;
					scanNumber = 1;
				}
			}
		}
		
		// Scan the left-right groups
		for ( int i = 0; i <  SIZE; i++ ) {
			for ( int j = 0; j + GROUP_SIZE-1 <  SIZE; j++ ) {
				int product = 1;
				for (int k = 0; k < GROUP_SIZE; k++ )
				{
					product *= table[i][j+k];
				}
				
				if ( product > maxNumber ) {
					maxNumber = product;
					maxX = i;
					maxY = j;
					scanNumber = 2;
				}
			}
		}
		
		// Scan the diagonal groups
		for ( int i = 0; i + GROUP_SIZE-1 <  SIZE; i++ ) {
			for ( int j = 0; j + GROUP_SIZE-1 <  SIZE; j++ ) {
				int product = 1;
				for (int k = 0; k < GROUP_SIZE; k++ )
				{
					product *= table[i+k][j+k];
				}
				
				if ( product > maxNumber ) {
					maxNumber = product;
					maxX = i;
					maxY = j;
					scanNumber = 3;
				}
			}
		}
		
		// Scan the anti-diagonal groups
		for ( int i = 0; i +  GROUP_SIZE-1 <  SIZE; i++ ) {
			for ( int j = GROUP_SIZE-1; j <  SIZE; j++ ) {
				int product = 1;
				for (int k = 0; k < GROUP_SIZE; k++ )
				{
					product *= table[i+k][j-k];
				}
				
				if ( product > maxNumber ) {
					maxNumber = product;
					maxX = i;
					maxY = j;
					scanNumber = 4;
				}
			}
		}
		
		System.out.println( "scanNumber " + scanNumber + " maxX " + maxX + " maxY " + maxY );
		// From scanNumber equals to 4, it is in an anti-diagonal group. Print out those numbers.
		for (int k = 0; k < GROUP_SIZE; k++ )
		{
			System.out.print( table[maxX+k][maxY-k] + " " );
		}
		System.out.println( "Max product: " + maxNumber );
		
		

	}
	
	private static void readTableFromFile( int[][] table, String filePath ) {
		try {
			BufferedReader reader = new BufferedReader( new FileReader(filePath));
			
			try {
				// Read the numbers from a text file and construct the table
				String line = null;
				
				for ( int count = 0; count <  SIZE && ( (line = reader.readLine()) != null ) ; count++) {
					StringTokenizer tokenizer = new StringTokenizer(line);
					
					for ( int i = 0; i <  SIZE && tokenizer.hasMoreTokens(); i++ ) {
						table[count][i] = Integer.parseInt( tokenizer.nextToken() );
					}
				}
				
//				// Print out the table
//				for (int i = 0; i < 3; i++ ) {
//					for ( int j = 0; j < 20; j++ ) {
//						System.out.print( " " + table[i][j] );
//					}
//					System.out.println();
//				}
				
			}
			finally {
				reader.close();
			}
		}
		catch (IOException ex) {
			System.out.println( ex );
		}
	}

}
