/**
 * 
 */
package com.google.codejam.y2008;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Arrays;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tdongsi
 *
 */
public class ThreeDigits {

	private static Logger logger = LoggerFactory.getLogger(ThreeDigits.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ThreeDigits solver = new ThreeDigits();
		solver.solve("data/ThreeDigitsSample.txt");
//		solver.solve("C-small-practice.in");
//		solver.solve("C-large-practice.in");
		
		String solution = solver.findDigits(10000);
		System.out.println(solution);
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
				int exponent = fileScanner.nextInt();
				
				// Solve the problem
				String solution = findDigits(exponent);
				
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
	 * TODO: This currently works on C-small-practice.in only
	 */
	private String findDigits(int exponent) {
		if (exponent > 2) {
			
			BigDecimal three = new BigDecimal(3);
			BigDecimal five = new BigDecimal(5);
			BigDecimal bigNumber = three.add(sqrt(five,exponent));
			
//			// double lacks precision to represent this number
//			double number = 3.0 + Math.sqrt(5);
//			BigDecimal bigNumber = new BigDecimal(number, MathContext.UNLIMITED);
			
			BigDecimal exp = bigNumber.pow(exponent);
			String numberString = exp.toPlainString();
			logger.debug(numberString);
			int indexOfDot = numberString.indexOf('.');
			return numberString.substring(indexOfDot - 3, indexOfDot);
		} else if (exponent == 2) {
			return "027";
		} else if (exponent == 1 ) {
			return "005";
		} else
			return "001";
	}
	
	
	/**
     * Compute the square root of x to a given scale, x >= 0.
     * Use Newton's algorithm.
     * @param x the value of x
     * @param scale the desired scale of the result
     * @return the result value
     */
    public static BigDecimal sqrt(BigDecimal x, int scale)
    {
        // Check that x >= 0.
        if (x.signum() < 0) {
            throw new IllegalArgumentException("x < 0");
        }
 
        // n = x*(10^(2*scale))
        BigInteger n = x.movePointRight(scale << 1).toBigInteger();
 
        // The first approximation is the upper half of n.
        int bits = (n.bitLength() + 1) >> 1;
        BigInteger ix = n.shiftRight(bits);
        BigInteger ixPrev;
 
        // Loop until the approximations converge
        // (two successive approximations are equal after rounding).
        do {
            ixPrev = ix;
 
            // x = (x + n/x)/2
            ix = ix.add(n.divide(ix)).shiftRight(1);
 
            Thread.yield();
        } while (ix.compareTo(ixPrev) != 0);
 
        return new BigDecimal(ix, scale);
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
