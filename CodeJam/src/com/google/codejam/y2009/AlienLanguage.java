package com.google.codejam.y2009;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.codejam.utility.Template;

public class AlienLanguage extends Template {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AlienLanguage solver = new AlienLanguage();
//		solver.solve("data/AlienLanguageSample.txt");
		solver.solve("data/A-small-practice.in");
	}
	
	@Override
	public void solve(String fileName) {
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			PrintStream out = new PrintStream("output.txt");
			
			String line = fileScanner.nextLine(); // first line: 3 5 4
			Scanner lineScanner = new Scanner(line);
			
			int length = lineScanner.nextInt(); // L
			int dictNum = lineScanner.nextInt(); // D: number of words
			int testNum = lineScanner.nextInt(); // N
			List<String> dictionary = new ArrayList<String>(dictNum);
			
			for (int i = 0; i < dictNum; i ++ ) {
				dictionary.add(fileScanner.nextLine());
			}
						
			for (int i = 0; i < testNum; i++) {
				String pattern = fileScanner.nextLine();
				
				int solution = countMatch( pattern, dictionary);
				
				// Print the output
				printSolution(i, Integer.toString(solution), out);
				printSolution(i, Integer.toString(solution), System.out);
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			logger.error("File(s) not found");
		}
	}

	/**
	 * Cheating solution, using regex
	 * 
	 * @param pattern
	 * @param dictionary
	 * @return
	 */
	private int countMatch(String pattern, List<String> dictionary) {
		
		char[] patternArr = pattern.toCharArray();
		
		// Replace ( with [ and ) with ]
		for (int i = 0; i < patternArr.length; i++) {
			if ( patternArr[i] == '(') {
				patternArr[i] = '[';
			} else if ( patternArr[i] == ')') {
				patternArr[i] = ']';
			}
		}
		
		String regex = new String(patternArr);
		int count = 0;
		logger.debug(regex);
		
		for (String word : dictionary) {
			if ( word.matches(regex) ) {
				count++;
			}
		}
		
		return count;
	}

}
