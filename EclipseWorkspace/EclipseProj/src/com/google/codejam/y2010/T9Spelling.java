/**
 * 
 */
package com.google.codejam.y2010;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author tdongsi
 *
 */
public class T9Spelling {

	public static void main(String[] args) {
		T9Spelling solver = new T9Spelling("data/T9Dictionary.txt");
		solver.solve("data/T9SpellingSample.txt");
//		solver.solve("C-small-practice.in");
//		solver.solve("C-large-practice.in");
	}
	
	private int testNum;
	private Map<Character,String> mDictonary;
	
	
	public T9Spelling(String dictionaryFile) {
		super();

		try {
			Scanner fileScanner = new Scanner(new File(dictionaryFile));
			this.mDictonary = new HashMap<Character,String>(50);
			
			while (fileScanner.hasNextLine())
			{
				Scanner lineScanner = new Scanner(fileScanner.nextLine());
				this.mDictonary.put(lineScanner.next().charAt(0), lineScanner.next());
				lineScanner.close();
			}
			// Add the space as 0
			this.mDictonary.put(' ', "0");
			
//			// DEBUG: print out the dictionary
//			for (Map.Entry<Character, String> entry : this.mDictonary.entrySet()) {
//				char key = entry.getKey();
//				String value = entry.getValue();
//				System.out.println( key + " : " + value );
//			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("Dictionary file not found");
			e.printStackTrace();
		}
	}
	

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
				String solution = composeT9Spelling(line);
				
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

	public String composeT9Spelling(String line) {
		StringBuilder solution = new StringBuilder(3*line.length());
		char[] charLine = line.toCharArray();
		char lastChar = 'a'; // not a number
		
		for (int i = 0; i < charLine.length; i++) {
			String word = mDictonary.get(charLine[i]);
			if ( word.charAt(0) == lastChar )
			{
				solution.append(" ");
			}
			solution.append(word);
			
			lastChar = word.charAt(word.length()-1);
		}
		
		return solution.toString();
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
