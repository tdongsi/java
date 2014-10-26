package com.google.codejam.utility;

import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Template for Google Code Jam's problems.
 * 
 * The following will be provided:
 * 
 * A logger for debugging.
 * A printSolution method for printing out to screen AND file for submission.
 * 
 * @author tdongsi
 *
 */
public abstract class Template {
	
	public static Logger logger = LoggerFactory.getLogger(Template.class);
	
	/**
	 * This abstract method should parse the file based on problem's instruction.
	 * Then, another method (with relevant name) should be called to solve the problem.
	 */
	public abstract void solve(String filename);
	
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
