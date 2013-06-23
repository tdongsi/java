/**
 * 
 */
package net.projecteuler.problems;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Find the maximum total from top to bottom of the triangle
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0018 {
	
	private static Logger logger = LoggerFactory.getLogger( Problem0018.class );

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Problem0018 solution = new Problem0018();
		
		try {
			// Define input data file
			// Problem 18
//			Scanner input = new Scanner( new File("data/prob0018_in.txt") ) ;
			// Problem 67
			Scanner input = new Scanner( new File("data/triangle.txt") ) ;
			// Define solver
			WeightedPathFinder solver = new WeightedPathFinder();
			
			// Define output data file
			// No
			PrintStream output = new PrintStream("prob0018_log.txt");
			
			solution.run( input, solver, System.out );
			solution.run( input, solver, output );
			
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find the input file");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void run(Scanner input, WeightedPathFinder solver, PrintStream output )
	{
		logger.info("Processing input data");
		List<int []> data = processInputData(input);
		
		if ( logger.isDebugEnabled()) {
			for (int[] is : data) {
				logger.debug(Arrays.toString(is));
			}
		}
		logger.info("Solving for the maximum sum");
		int maxSum = solver.findMaxCost(data);
		logger.info("The max sum is: " + maxSum );
		output.println("The max sum is: " + maxSum);
	}


	/**
	 * Get the number pyramid from a text data file
	 * @param input
	 */
	private List<int []> processInputData(Scanner input) {
		List<int []> data = new ArrayList<int []>();
		int count = 0;
		while ( input.hasNextLine() )
		{
			count++;
			int[] row = new int[count];
			int idx = 0;
			String line = input.nextLine();
			logger.debug(line);
			Scanner lineScanner = new Scanner( line );
			while ( lineScanner.hasNextInt() )
			{
				row[idx] = lineScanner.nextInt();
				idx++;
			}
			
			data.add(row);
		}
		
		return data;
	}

}
