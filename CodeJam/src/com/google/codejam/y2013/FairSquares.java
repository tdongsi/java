package com.google.codejam.y2013;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: this currently only solves the small and medium (large-1) problems
 * @author tdongsi
 *
 */
public class FairSquares {

	private static Logger logger = LoggerFactory.getLogger(FairSquares.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		FairSquares solver = new FairSquares();
//		solver.solve("data/FairSquaresSample.txt");
//		solver.solve("C-small-attempt0.in");
		
		FairSquares solver = new FairSquares(1000000000000000L);
		solver.solveQuick("data/FairSquaresSample.txt");
//		solver.solveQuick("C-small-attempt0.in");
//		solver.solveQuick("C-large-practice-1.in");
	}
	
	/**
	 * Empty constructor for solving small problem
	 */
	public FairSquares() {
	}
	
	
	/**
	 * List of fair squares
	 */
	private List<Long> fairSquares;
	
	/**
	 * Find all the fair squares during the interval 1 <= n <= upperBound
	 * For solving medium problems
	 * 
	 * @param upperBound
	 */
	public FairSquares(long upperBound) {
		fairSquares = new ArrayList<Long>(500);
		
		long i = 1;
		long square = 1;
		
		while (square <= upperBound)
		{
			if ( isPalindrome(i) && isPalindrome(square) )
			{
				fairSquares.add(square);
			}
			
			i++;
			square = i*i;
		}
	}

	private int testNum;
	
	/**
	 * Find all the fair squares in the known interval and solve the test cases quickly
	 * @param fileName
	 */
	public void solveQuick(String fileName)
	{
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			PrintStream out = new PrintStream("outputQuick.txt");
			
			// Processing input
			String line;
			line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			
			logger.debug("Case number: {}", testNum );
			
			for (int i = 0; i < testNum; i++) {
				
				Scanner lineScanner = new Scanner(fileScanner.nextLine());
				// Get the input
				long lowerBound = lineScanner.nextLong();
				long upperBound = lineScanner.nextLong();
				
				logger.debug("Inputs: {} {}", lowerBound, upperBound);
				
				// Solve the problem
				int count = 0;
				for (int j = 0; j < fairSquares.size() && fairSquares.get(j) <= upperBound; j++ )
				{
					if ( fairSquares.get(j)  >= lowerBound )
					{
						count++;
					}
				}
				String solution = Integer.toString(count);
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Solve the test cases one-by-one
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
			
			logger.debug("Case number: {}", testNum );
			
			for (int i = 0; i < testNum; i++) {
				
				Scanner lineScanner = new Scanner(fileScanner.nextLine());
				// Get the input
				long lowerBound = lineScanner.nextLong();
				long upperBound = lineScanner.nextLong();
				
				logger.debug("Inputs: {} {}", lowerBound, upperBound);
				
				// Solve the problem
				String solution = countFairSquares(lowerBound, upperBound);
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Count the number of palidromic squares between the lower bound and upper bound
	 */
	public String countFairSquares(long lowerBound, long upperBound) {
		long startNum = (long) Math.ceil(Math.sqrt(lowerBound));
		long i = startNum;
		long square = i*i;
		
		int counter = 0;
		
		while (square <= upperBound)
		{
			if ( isPalindrome(i) && isPalindrome(square) )
			{
				logger.debug("Number: {}", square);
				counter++;
			}
			
			i++;
			square = i*i;
		}
		
		return Integer.toString(counter);
	}

	/**
	 * Check if the given number is palidromic
	 */
	public boolean isPalindrome(long number) {
		char[] string = Long.toString(number).toCharArray();
		for (int i = 0; i < string.length/2; i++) {
			if (string[i] != string[string.length-i-1])
			{
				return false;
			}
		}
		return true;
	}

	/**
	 * Print out the solution, either to console or to a file
	 */
	public void printSolution(int i, String solution, PrintStream outStream) {
		outStream.println("Case #" + (i+1) + ": " + solution);
	}

}
