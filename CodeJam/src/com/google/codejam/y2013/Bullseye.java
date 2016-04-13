package com.google.codejam.y2013;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://code.google.com/codejam/contest/dashboard?c=2418487
 * 
 * @author tdongsi
 *
 */
public class Bullseye {

	private static Logger logger = LoggerFactory.getLogger(Bullseye.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bullseye solver = new Bullseye();
		solver.solve("data/BullseyeSample.txt");
//		solver.solve("data/A-small-practice.in");
//		solver.solve("data/A-large-practice.in");
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
				Scanner lineScanner = new Scanner(line);
				long radius = lineScanner.nextLong();
				long tank = lineScanner.nextLong();
				
				// Solve the problem
				String solution = findRingNumberBig(radius, tank);
				
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
	 * Construct the quadratic equation based on r and t.
	 * 
	 * @param r radius
	 * @param t starting paints
	 */
	public String findRingNumber(long r, long t) {
		// Construct the quadratic equation a*x^2 + bx + c
		
//		long a = 2;
//		long b = 2*r - 1;
//		long c = -t;
		long solution = (long)quadraticSolve(2,2*r - 1,-t) ;
		
		
		return Long.toString(solution);
	}


	/**
	 * Solving the quadratic equation for the positive root.
	 * i.e solve for k in (2r + 2k - 1)k <= t
	 * Using double and it won't pass on the large data set
	 */
	private double quadraticSolve(long a, long b, long c) {
		double bDouble = (double) b;
		double cDouble = (double) c;
		double delta = bDouble*bDouble - 4*a * cDouble;
		
		double x1 = (-bDouble - Math.sqrt(delta))/2/a;
		double x2 = cDouble/x1/a;
		
		return x2;
	}
	
	/**
	 * Instead of forming and solving a quadratic equation for the positive root
	 * utilize the fact that we're finding an integer close to the root.
	 * 
	 * @param r radius
	 * @param t starting paint
	 */
	public String findRingNumberBig(long r, long t) {
		logger.debug( "Problem: {} {}", r, t);
		
		long left = 0;
		long right = 1;
		long res = 0;
		while (checkSolved(right, r, t))
		{
			left = right;
			right *= 2;
		}
		
		// Using the (almost) bisection method to find the integer (almost) root
		while (left <= right)
		{
			logger.debug("left {} : right {}", left, right);
			long med = (left + right)/2;
			if ( (2*r+2*med-1) * med == t )
			{
				return Long.toString(med);
			}
			
			if ( checkSolved(med, r, t) )
			{
				left = med+1;
				res = med;
			} else {
				right = med-1;
			}
			
		}
		
		return Long.toString(res);
	}
	
	private boolean checkSolved(long k, long r, long t)
	{
		return ( (2*r+2*k-1) * k <= t );
	}


	/**
	 * Print out the solution, either to console or to a file
	 */
	public void printSolution(int i, String solution, PrintStream outStream) {
		outStream.println("Case #" + (i+1) + ": " + solution);
	}

}

