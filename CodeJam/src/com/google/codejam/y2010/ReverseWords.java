/**
 * 
 */
package com.google.codejam.y2010;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tdongsi
 *
 */
public class ReverseWords {
	
	private static Logger logger = LoggerFactory.getLogger(ReverseWords.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReverseWords solver = new ReverseWords();
		solver.solve("data/ReverseWordsSample.txt");
//		solver.solve("B-small-practice.in");
//		solver.solve("B-large-practice.in");
	}
	
	private int testNum;
	
	/**
	 * Parsing the input data before calling the main method for solving
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
				
				// Solve the problem
				String solution = reverseWords(line);
				
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
	 * Reverse all characters of the given string
	 * @param input
	 * @return
	 */
	public String reverseString(String input)
	{
		char[] string = input.toCharArray();
		char[] reverse = new char[string.length];
		for (int i = 0; i < reverse.length; i++) {
			reverse[i] = string[string.length-i-1];
		}
		return new String(reverse);
	}

	/**
	 * Reverse word order of the given string
	 * @param line
	 * @return
	 */
	public String reverseWords(String line) {
		StringBuilder reverseLine = new StringBuilder(line.length());
		
		Scanner lineScanner = new Scanner(reverseString(line));
		while ( lineScanner.hasNext() )
		{
			String token = lineScanner.next();
			logger.debug("Token: {}", token);
			reverseLine.append(reverseString(token) + " ");
		}
		// Remove the extra space at the end
		reverseLine.deleteCharAt(reverseLine.length()-1);
		lineScanner.close();
		
		logger.debug("Line: [{}]", reverseLine.toString());
		
		return reverseLine.toString();
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
