/**
 * 
 */
package com.google.codejam.y2012;

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
public class KingdomRush {

	private static Logger logger = LoggerFactory.getLogger(KingdomRush.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		KingdomRush solver = new KingdomRush();
		solver.solve("data/KingdomRushSample.txt");
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
			// number of test cases
			testNum = Integer.valueOf(line);
			
			for (int i = 0; i < testNum; i++) {
				// Get the input
				line = fileScanner.nextLine();
				int size = Integer.valueOf(line);
				int[] level1 = new int[size];
				int[] level2 = new int[size];
				
				for (int j = 0; j < level1.length; j++) {
					line = fileScanner.nextLine();
					Scanner lineScanner = new Scanner(line);
					
					level1[j] = lineScanner.nextInt();
					level2[j] = lineScanner.nextInt();
					
					lineScanner.close();
				}
				
				
				// Solve the problem
				String solution = findMinimumSteps(level1, level2);
				
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
	 * Main method for solving the problem
	 */
	private String findMinimumSteps(int[] level1, int[] level2) {
		int gameCounter = 0;
		int currPoint = 0;
		
		int[] bonusPoints = new int[level1.length];
		
		// Bonus points for completing that game
		// Bonus point = 0: game completed
		// Bonus point = 1
		for (int i = 0; i < bonusPoints.length; i++) {
			bonusPoints[i] = 2;
		}
		
		while (true)
		{
			// check if bonusPoints are all zeros, game over
			boolean isGameOver = true;
			for (int i = 0; i < bonusPoints.length; i++) {
				if (bonusPoints[i] > 0 )
				{
					isGameOver = false;
					break;
				}
			}
			
			if (isGameOver)
			{
				// return the current game number
				return Integer.toString(gameCounter);
			}
			
			
			// Find the possible level 2
			boolean level2Found = false;
			
			for (int i = 0; i < level2.length; i++) {
				// Complete all the possible level 2 for max bonus
				if ( level2[i] <= currPoint && bonusPoints[i] == 2 )
				{
					bonusPoints[i] = 0;
					currPoint += 2;
					gameCounter++;
					level2Found = true;
					logger.debug("Played level 2, game {}", i);
				}
			}
			if (level2Found)
				continue;
			
			for (int i = 0; i < level2.length; i++) {
				if ( level2[i] <= currPoint && bonusPoints[i] == 1 )
				{
					bonusPoints[i] = 0;
					currPoint++;
					gameCounter++;
					level2Found = true;
					logger.debug("Played level 2, game {}", i);
				}
			}
			if (level2Found)
				continue;
			
			// Find the possible level 1
			boolean level1Found = false;
			int level2Max = -1;
			int indexMax = -1;
			
			for (int i = 0; i < level1.length; i++) {
				if ( level1[i] <= currPoint && bonusPoints[i] == 2 )
				{
					level1Found = true;
					
					if ( level2[i] > level2Max)
					{
						level2Max = level2[i];
						indexMax = i;
					}
				}
			}
			
			if ( level1Found )
			{
				bonusPoints[indexMax] = 1;
				gameCounter++;
				currPoint++;
				logger.debug("Played level 1, game {}", indexMax);
				continue;
			}
			else
			{
				return "Too Bad";
			}
		}
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
