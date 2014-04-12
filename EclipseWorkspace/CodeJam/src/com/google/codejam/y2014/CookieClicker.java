/**
 * 
 */
package com.google.codejam.y2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Scanner;

import com.google.codejam.y2012.PasswordProblem;

/**
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class CookieClicker {

	public static void main(String[] args) {
		CookieClicker solver = new CookieClicker();
//		solver.solve("data/CookieClickerSample.txt");
//		solver.solve("data/B-small-attempt1.in");
		solver.solve("data/B-large.in");
		
	}
	
	private int testNum;
	
	
	/**
	 * Parsing the input data before calling the main method for solving
	 */
	public void solve(String fileName)
	{
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			Scanner lineScanner;
			PrintStream out = new PrintStream("output_unique.txt");
			
			// Processing input
			String line;
			line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			
			for (int i = 0; i < testNum; i++) {
				// Get the input
				line = fileScanner.nextLine();
				lineScanner = new Scanner(line);
				
				BigDecimal C = new BigDecimal(lineScanner.next(), MathContext.DECIMAL128);
				BigDecimal F = new BigDecimal(lineScanner.next(), MathContext.DECIMAL128);
				BigDecimal X = new BigDecimal(lineScanner.next(), MathContext.DECIMAL128);
				
				lineScanner.close();
				
				// Solve the problem
				BigDecimal solution = findMinimumTime(C, F, X);
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		return;
	}
	


	private BigDecimal findMinimumTime(BigDecimal c, BigDecimal f, BigDecimal x) {
		// Problem assumptions
		final BigDecimal startingCookie = BigDecimal.ZERO;
		MathContext mc = MathContext.DECIMAL128;
		final BigDecimal startingRate = new BigDecimal("2.0", mc);
		
		
		// find the optimal number of times buying farm
		BigDecimal startRate_div_f = startingRate.divide(f, mc);
		BigDecimal x_div_c = x.divide(c, mc);
		BigDecimal lhs = x_div_c.subtract(BigDecimal.ONE, mc);
		
		BigDecimal farmCount = lhs.subtract(startRate_div_f, mc);
		
		// Using round() is WRONG
		// int farmCountInt = farmCount.round(new MathContext(1,RoundingMode.CEILING)).intValue();
		int farmCountInt = farmCount.setScale(0, RoundingMode.CEILING).intValue();
//		System.out.println( "farmCount: " + farmCountInt);
		BigDecimal sum = BigDecimal.ZERO;
		BigDecimal currentRate = startingRate;
		BigDecimal temp;
		
		for (int i = 0; i < farmCountInt; i++) {
			temp = c.divide(currentRate, mc);
			sum = sum.add(temp, mc);
			currentRate = currentRate.add(f, mc);
		}
		
		temp = x.divide(currentRate, mc);
		sum = sum.add(temp, mc);
		
		return sum;
	}



	/**
	 * Print out the solution, either to console or to a file
	 * 
	 * @param i
	 * @param solution
	 * @param outStream
	 */
	public void printSolution(int i, BigDecimal solution, PrintStream outStream) {
//		outStream.println("Case #" + (i+1) + ": " + solution);
		outStream.printf("Case #%d: %s\n", i+1, solution.toString() );
	}

}
