package com.google.codejam.y2009;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import com.google.codejam.utility.Template;

public class AlienLanguage extends Template {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AlienLanguage solver = new AlienLanguage();
		solver.solve("data/AlienLanguageSample.txt");
	}
	
	@Override
	public void solve(String fileName) {
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			PrintStream out = new PrintStream("output.txt");
			int testNum = 4;
						
			for (int i = 0; i < testNum; i++) {
				String solution = "0";
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			logger.error("File(s) not found");
		}
	}

}
