package com.google.codejam.y2014;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.codejam.utility.Coordinate;

/**
 * Given the size of the board (R x C) and the number of hidden mines M, is it possible (however unlikely) to win in one click?
 * You may choose where you click. If it is possible, then print any valid mine configuration and the coordinates of your click, 
 * following the specifications in the Output section. Otherwise, print "Impossible".
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class MineSweeper {
	
	private static Logger logger = LoggerFactory.getLogger(MineSweeper.class);

	public static void main(String[] args) {
		MineSweeper solver = new MineSweeper();
		solver.solve("data/MineSweeperSample.txt");
//		solver.solve("data/C-small-practice.in");		
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
			PrintStream out = new PrintStream("output.txt");
			
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
	


	/**
	 * Naive strategy: put the mines at the edge of available grids. 
	 * There are several cases that this naive strategy does not work.
	 */
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
		
		
		
		// Naive strategy: put it one by one to the edges
		logger.debug( "Minefield:\n{}", field.toString() );
		if ( !field.isAnyBlank() )
		{
			return "Impossible\n";
		} else if ( mine == 0 )
		{
			field.setClick();
			return field.toAnswerString();
		} else {
			
			for (int i = 0; i < row && mine > 0; i++) {
				if ( mineSquares.add(new Coordinate(offset+i,offset)) )
				{
					mine--;
				}
			}
			for (int j=0; j < col && mine > 0; j++)
			{
				if ( mineSquares.add(new Coordinate(ROW-offset-1,offset+j)) )
				{
					mine--;
				}
			}
			for (int i = row-1; i >= 0 && mine > 0; i--) {
				if ( mineSquares.add(new Coordinate(offset+i,COL-offset-1)) )
				{
					mine--;
				}
			}
			for (int j=col-1; j >= 0 && mine > 0; j--)
			{
				if ( mineSquares.add(new Coordinate(offset,offset+j)) )
				{
					mine--;
				}
			}
			field.addMines(mineSquares);
			
			logger.debug( "Minefield:\n{}", field.toString() );
			
			if ( !field.isAnyBlank() )
			{
				return "Impossible\n";
			} else if ( mine == 0 )
			{
				field.setClick();
				return field.toAnswerString();
			}
			
		} // end if mine > 0
		
		return "Impossible\n";
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
		outStream.printf("Case #%d:\n%s", i+1, solution );
	}

}
