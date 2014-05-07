/**
 * 
 */
package com.google.codejam.y2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author tdongsi
 */
public class ScalarProduct {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ScalarProduct solver = new ScalarProduct();
		solver.solve("data/ScalarProductSample.txt");
//		solver.solve("A-small-practice.in");
//		solver.solve("A-large-practice.in");
	}
	
	private int testNum;
	
	/**
	 * Parsing the input data before calling the main method for solving
	 * 
	 * @param fileName: path to the input data file
	 */
	public void solve(String fileName)
	{
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			PrintStream out = new PrintStream("output.txt");
			
			// Processing input
			String line;
			line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			
			for (int i = 0; i < testNum; i++) {
				// Get the input
				line = fileScanner.nextLine();
				int size = Integer.valueOf(line);
				int[] vector1 = new int[size];
				int[] vector2 = new int[size];
				
				line = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				for (int j = 0; j < vector1.length; j++) {
					vector1[j] = lineScanner.nextInt();
				}
				lineScanner.close();
				
				line = fileScanner.nextLine();
				lineScanner = new Scanner(line);
				for (int j = 0; j < vector2.length; j++) {
					vector2[j] = lineScanner.nextInt();
				}
				lineScanner.close();
				
				// Solve the problem
				String solution = computeMinScalarProduct(vector1, vector2);
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * To find the minimum scalar product, sort the elements in two vectors
	 * in two different ordering and perform the scalar product.
	 * There is a mathematical theorem for this.
	 */
	public String computeMinScalarProduct(int[] vector1, int[] vector2) {
		Arrays.sort(vector1);
		Arrays.sort(vector2);
		long sum = 0;
		for (int i = 0; i < vector1.length; i++) {
			long temp = vector1[i];
			long temp2 = vector2[vector2.length-i-1];
			sum += temp * temp2;
		}
		return Long.toString(sum);
	}

	/**
	 * Print out the solution, either to console or to a file
	 * 
	 * @param i
	 * @param solution
	 * @param outStream
	 */
	public void printSolution(int i, String solution, PrintStream outStream) {
		outStream.println("Case #" + (i+1) + ": " + solution);
	}

}
