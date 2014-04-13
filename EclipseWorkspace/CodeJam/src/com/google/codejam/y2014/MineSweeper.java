package com.google.codejam.y2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.codejam.y2013.Coordinate; 

public class MineSweeper {
	
	private static Logger logger = LoggerFactory.getLogger(MineSweeper.class);

	public static void main(String[] args) {
		MineSweeper solver = new MineSweeper();
		solver.solve("data/MineSweeperSample.txt");
//		solver.solve("data/B-small-attempt1.in");
//		solver.solve("data/B-large.in");
		
	}
	
	private int testNum;
	
	
	/**
	 * Parsing the input data before calling the main method for solving
	 */
	public void solve(String fileName)
	{
		try {
			Scanner fileScanner = new Scanner(new File(fileName));
			Scanner lineScanner;
			PrintStream out = new PrintStream("output_unique.txt");
			
			// Processing input
			String line;
			line = fileScanner.nextLine();
			testNum = Integer.valueOf(line);
			
			for (int i = 0; i < testNum; i++) {
				// Get the input
				line = fileScanner.nextLine();
				lineScanner = new Scanner(line);
				
				int R = lineScanner.nextInt();
				int C = lineScanner.nextInt();
				int M = lineScanner.nextInt();
				
				lineScanner.close();
				
				// Solve the problem
				String solution = findMinimumMinefield(R, C, M);
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
			fileScanner.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		return;
	}
	


	private String findMinimumMinefield(int ROW, int COL, int MINE) {
		
		logger.trace("Size: {} {}",  ROW, COL);
		logger.trace( "Mines: {}", MINE);
		
		Set<Coordinate> mineSquares = new HashSet<Coordinate>(2 * MINE);
		
		int row = ROW;
		int col = COL;
		int mine = MINE;
		int offset = 0;
		
		MineField field = MineField.initField(ROW, COL);
		
		while ( mine >= 2*(row+col-2) & row >=2 & col >= 2 )
		{
			// put the mines at the edges of the field
			for (int i = 0; i < row; i++) {
				mineSquares.add(new Coordinate(offset+i,offset));
				mineSquares.add(new Coordinate(offset+i,COL-offset-1));
			}
			for (int j=0; j < col; j++)
			{
				mineSquares.add(new Coordinate(offset,offset+j));
				mineSquares.add(new Coordinate(ROW-offset-1,offset+j));
			}
			
			mine -= 2*(row+col-2);
			row -= 2;
			col -= 2;
			offset++;
			
			field.addMines(mineSquares);
			logger.trace( "Minefield:\n{}", field.toString() );
			logger.trace( "Row {}, col {}", row, col);
			logger.trace( "Mine: {} vs {}", mine, 2*(row+col-2));
		}
		
		logger.debug( "Minefield:\n{}", field.toString() );
		if ( !field.isAnyBlank() )
		{
			return "Impossible";
		} else if ( mine == 0 )
		{
			field.setClick();
			return field.toString();
		} else {
			
			for (int i = 0; i < row && mine > 0; i++, mine--) {
				logger.trace( "Row {}, col {}", offset+i, offset);
				mineSquares.add(new Coordinate(offset+i,offset));
			}
			for (int j=0; j < col && mine > 0; j++, mine--)
			{
				mineSquares.add(new Coordinate(ROW-offset-1,offset+j));
			}
			for (int i = 0; i < row && mine > 0; i++, mine--) {
				mineSquares.add(new Coordinate(offset+i,COL-offset-1));
			}
			for (int j=0; j < col && mine > 0; j++, mine--)
			{
				mineSquares.add(new Coordinate(offset,offset+j));
			}
			field.addMines(mineSquares);
			
			logger.debug( "Minefield:\n{}", field.toString() );
			
			if ( !field.isAnyBlank() )
			{
				return "Impossible";
			} else if ( mine == 0 )
			{
				field.setClick();
				return field.toString();
			}
			
		} // end if mine > 0
		
		return "solution";
	}



	/**
	 * Print out the solution, either to console or to a file
	 * 
	 * @param i
	 * @param solution
	 * @param outStream
	 */
	public void printSolution(int i, String solution, PrintStream outStream) {
//		outStream.println("Case #" + (i+1) + ": " + solution);
		outStream.printf("Case #%d: %s\n", i+1, solution );
	}

}
