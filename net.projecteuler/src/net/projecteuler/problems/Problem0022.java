package net.projecteuler.problems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**For example, when the list is sorted into alphabetical order, COLIN, 
 * which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list. 
 * So, COLIN would obtain a score of 938  53 = 49714.
 * What is the total of all the name scores in the file?
 * 
 * @author tdongsi
 *
 */
public class Problem0022 {
	
	private static Logger logger = LoggerFactory.getLogger(Problem0022.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Problem0022 solver = new Problem0022();
		Scanner input;
		
		try {
			input = new Scanner( new File("data/names.txt") );
			solver.solveProblem( input, logger );
		} catch (FileNotFoundException e) {
			logger.error("Text file not found");
			e.printStackTrace();
		} catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	private void solveProblem(Scanner input, Logger logger2) {
		
		TreeSet<String> nameList = new TreeSet<String>();
		
		logger.info("Processing input data");
		input.useDelimiter(",");
		
		// Read the names from file and add them into an ordered list
		while ( input.hasNext() )
		{
			String name = input.next();
			String quoteRemoved = name.substring(1, name.length()-1);
			logger.trace(quoteRemoved);
			
			nameList.add(quoteRemoved);
		}
		
		int nameOrder = 0;
		long sum = 0;
		for (String name : nameList) {
			nameOrder++;
			
			int nameValue = computeNameValue(name);
			logger.debug("Name: {}, nameValue: {}", name, nameValue);
			
			sum += (nameValue*nameOrder);
		}
		
		// Test
		int nameValue = computeNameValue("COLIN");
		logger.debug("Name: COLIN, nameValue: {}", nameValue);
		
		logger.info("The sum is {}", sum);
		
	}

	public int computeNameValue(String name) {
		int sum = 0;
		
		char[] charName = name.toCharArray();
		
		for (int i = 0; i < charName.length; i++) {
			sum += charName[i] - 'A' + 1;
		}
		
		return sum;
	}

}
