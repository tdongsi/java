package com.google.codejam.y2010;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tdongsi
 *
 */
public class StoreCredit {
	
	private static Logger logger = LoggerFactory.getLogger(StoreCredit.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StoreCredit solver = new StoreCredit();
		solver.solve("data/StoreCreditSample.txt");
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
			PrintStream out = new PrintStream(new File("output.txt"));
			
			String line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			logger.debug("Test case number: " + testNum);
			
			for ( int i=0; i < testNum; i++ )
			{
				line = fileScanner.nextLine();
				int creditNum = Integer.valueOf(line);
				
				line = fileScanner.nextLine();
				int itemNum = Integer.valueOf(line);
				
				line = fileScanner.nextLine();
				int[] itemPrices = new int[itemNum];
				Scanner lineScanner = new Scanner(line);
				for (int j = 0; j < itemPrices.length; j++) {
					itemPrices[j] = lineScanner.nextInt();
				}
				lineScanner.close();
				
				// Check them out
				logger.debug("Credit: {}", creditNum);
				logger.debug("Item: {}", itemNum);
				logger.debug("Prices: {}", Arrays.toString(itemPrices) );
				
				
				// Solve it
				int[] itemIndices = findItems( creditNum, itemNum, itemPrices );
				
				
				// Print it
				printSolution(i, itemIndices, out);
				printSolution(i, itemIndices, System.out);
			}
			
			fileScanner.close();
			
			
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
		}
		
	}

	/**
	 * @param i
	 * @param itemIndices
	 */
	private void printSolution(int i, int[] itemIndices, PrintStream outputStream) {
		outputStream.println( "Case #" + (i+1) + ": " + itemIndices[0] + " " + itemIndices[1] );
	}

	/**
	 * Return the indices of the pair of items that their prices make up the store credit
	 * Return (0,0) if there is none found.
	 * 
	 * @param creditNum
	 * @param itemNum
	 * @param itemPrices
	 * @return
	 */
	public int[] findItems(int creditNum, int itemNum, int[] itemPrices) {
		int[] index = new int[2];
		
		// Map of missing credit (key) to the index (value)
		Map<Integer,Integer> indexToRequiredCredit = new HashMap<Integer,Integer>(itemNum*2);
		
		for (int i = 0; i < itemPrices.length; i++) {
			
			// credit to look for
			int desiredCredit = creditNum - itemPrices[i];
			
			if ( indexToRequiredCredit.containsKey(itemPrices[i]))
			{
				index[0] = indexToRequiredCredit.get(itemPrices[i]);
				index[1] = i+1;
				return index;
			} else {
				indexToRequiredCredit.put(desiredCredit, i+1);
			}
		}
		
		return index;
	}

}
