/**
 * 
 */
package com.google.codejam.y2012;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * @author cuongd
 *
 */
public class PasswordProblem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PasswordProblem solver = new PasswordProblem();
		solver.solve("data/PasswordSample.txt");
//		solver.solve("A-small-practice.in");
//		solver.solve("A-large-practice.in");
		
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
			PrintStream out = new PrintStream("output.txt");
			
			// Processing input
			String line;
			line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			
			for (int i = 0; i < testNum; i++) {
				// Get the input
				line = fileScanner.nextLine();
				lineScanner = new Scanner(line);
				int typedNumber = lineScanner.nextInt();
				int pwdLength = lineScanner.nextInt();
				lineScanner.close();
				
				line = fileScanner.nextLine();
				lineScanner = new Scanner(line);
				double[] probs = new double[typedNumber];
				for (int j = 0; j < probs.length; j++) {
					probs[j] = lineScanner.nextDouble();
				}
				lineScanner.close();
				
				// Solve the problem
				double solution = findExpectedLength(typedNumber, pwdLength, probs);
				
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
	

	public double findExpectedLength(int A, int B,
			double[] probs) {
		// the number of stroke for one password entry
		// including enter keystroke
		int Bp = B++;
		
		// Strategy 1: pressing Enter and retry
		double min = Bp+1;
		
		// Strategy 2: press Backspace all the way (A times), 
		// always worse than above since A >= 1
		double temp;
		int common = 2*Bp - A;
		double prod = 1.0;
		
		for (int i = 0; i < probs.length; i++) {
			prod *= probs[i];
			
			if (i == probs.length - 1)
			{
				// Strategy: Keep typing
				temp = common - Bp * prod;
			} else {
				// Strategy: Pressing backspace for n times and continue
				int n = A - i - 1;
				temp = (common + 2*n) - Bp * prod;
			}
			
			if ( temp < min )
			{
				min = temp;
			}
		}
		
		return min;
	}


	/**
	 * Print out the solution, either to console or to a file
	 * 
	 * @param i
	 * @param solution
	 * @param outStream
	 */
	public void printSolution(int i, double solution, PrintStream outStream) {
//		outStream.println("Case #" + (i+1) + ": " + solution);
		outStream.printf("Case #%d: %.6f\n", i+1, solution);
	}

}
