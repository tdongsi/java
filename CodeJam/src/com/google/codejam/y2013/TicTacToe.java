package com.google.codejam.y2013;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.codejam.utility.Coordinate;

/**
 * @author tdongsi
 *
 */
public class TicTacToe {

	private static Logger logger = LoggerFactory.getLogger(TicTacToe.class);

	public static void main(String[] args) {
		TicTacToe solver = new TicTacToe();
		solver.solve("data/TicTacToeSample.txt");
//		solver.solve("A-small-attempt0.in");
//		solver.solve("A-large.in");
	}
	
	private int testNum;
	private static final int SIZE = 4;
	
	private boolean isTableFull;

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
				// Solve the problem
				String solution = checkState(fileScanner);
				// Skip the next empty line
				line = fileScanner.nextLine();
				
				
				// Print the output
				printSolution(i, solution, out);
				printSolution(i, solution, System.out);
			}
			
			out.close();
			
		} catch (FileNotFoundException e) {
			System.err.println("File(s) not found");
			e.printStackTrace();
		}
		
		
	}

	/**
	 * Overload checkState to process input directly from file for speed
	 * 
	 * @param fileScanner
	 * @return
	 */
	public String checkState(Scanner fileScanner) {
		String statusMessage;
		isTableFull = true;
		List<Coordinate> xPos = new ArrayList<Coordinate>(SIZE*SIZE);
		List<Coordinate> oPos = new ArrayList<Coordinate>(SIZE*SIZE);
		
		// process the input table
		for (int i = 0; i < SIZE; i++) {
			String line = fileScanner.nextLine();
			
			for (int j = 0; j < SIZE; j++) {
				char currentChar = line.charAt(j);
				
				// Check if the table is full
				if ( isTableFull && currentChar == '.')
				{
					isTableFull = false;
				}
				
				if ( currentChar == 'X' || currentChar == 'T')
				{
					// position data for player X
					Coordinate coord = new Coordinate(i, j);
					xPos.add(coord);
				}
				
				if ( currentChar == 'O' || currentChar == 'T')
				{
					// position data for player O
					Coordinate coord = new Coordinate(i, j);
					oPos.add(coord);
				}
			}
		}
		
		// check the state
		boolean isWinnerX = checkWinner(xPos);
		boolean isWinnerO = checkWinner(oPos);
		
		if ( isWinnerX )
		{
			statusMessage = "X won";
		} else if ( isWinnerO )
		{
			statusMessage = "O won";
		} else if ( isTableFull )
		{
			statusMessage = "Draw";
		} else
		{
			statusMessage = "Game has not completed";
		}
		
		return statusMessage;
	}

	/**
	 * Check if the player is winner, given the positions of his symbols
	 * 
	 * @param listPos
	 * @return
	 */
	private boolean checkWinner(List<Coordinate> listPos) {
		boolean checkColumn = checkWinnerColumn(listPos);
		boolean checkRow = checkWinnerRow(listPos);
		boolean checkDiagonal = checkWinnerDiagonal(listPos);
		return (checkColumn || checkRow || checkDiagonal);
	}

	/**
	 * Check if the player is winner by diagonal, given the positions of his symbols.
	 * 
	 * @param listPos
	 * @return
	 */
	private boolean checkWinnerDiagonal(List<Coordinate> listPos) {
		boolean checkDiagonal = true;
		for (int i = 0; i < SIZE; i++) {
			if ( !listPos.contains(new Coordinate(i,i)))
			{
				checkDiagonal = false;
				break;
			}
		}
		
		boolean checkAntiDiagonal = true;
		for (int i = 0; i < SIZE; i++) {
			if ( !listPos.contains(new Coordinate(i,SIZE-i-1)))
			{
				checkAntiDiagonal = false;
				break;
			}
		}
		
		return (checkDiagonal || checkAntiDiagonal);
	}

	/**
	 * Check if the player is winner by row, given the positions of his symbols.
	 * 
	 * @param listPos
	 * @return
	 */
	private boolean checkWinnerRow(List<Coordinate> listPos) {
		int[] count = new int[SIZE];

		for (Coordinate coordinate : listPos) {
			count[coordinate.getX()]++;
		}

		for (int i = 0; i < count.length; i++) {
			if ( count[i] == SIZE )
				return true;
		}

		return false;
	}

	/**
	 * Check if the player is winner by column, given the positions of his symbols.
	 * 
	 * @param listPos
	 * @return
	 */
	private boolean checkWinnerColumn(List<Coordinate> listPos) {
		int[] count = new int[SIZE];
		
		for (Coordinate coordinate : listPos) {
			count[coordinate.getY()]++;
		}
		
		for (int i = 0; i < count.length; i++) {
			if ( count[i] == SIZE )
				return true;
		}
		
		return false;
	}
	
	/**
	 * Check the status of the game, given the board description as 2D char array
	 * 
	 * @param table
	 * @return
	 */
	private String checkState(char[][] table) {
		String statusMessage;
		isTableFull = true;
		List<Coordinate> xPos = new ArrayList<Coordinate>(SIZE*SIZE);
		List<Coordinate> oPos = new ArrayList<Coordinate>(SIZE*SIZE);
		
		// process the input table
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				// Check if the table is full
				if ( isTableFull && table[i][j] == '.')
				{
					isTableFull = false;
				}
				
				if ( table[i][j] == 'X' || table[i][j] == 'T')
				{
					// position data for player X
					Coordinate coord = new Coordinate(i, j);
					xPos.add(coord);
				}
				
				if ( table[i][j] == 'O' || table[i][j] == 'T')
				{
					// position data for player O
					Coordinate coord = new Coordinate(i, j);
					oPos.add(coord);
				}
			}
		}
		
		// check the state
		boolean isWinnerX = checkWinner(xPos);
		boolean isWinnerO = checkWinner(oPos);
		
		if ( isWinnerX )
		{
			statusMessage = "X won";
		} else if ( isWinnerO )
		{
			statusMessage = "O won";
		} else if ( isTableFull )
		{
			statusMessage = "Draw";
		} else
		{
			statusMessage = "Game has not completed";
		}
		
		return statusMessage;
	}

	/**
	 * Construct the board description as 2D char array from the input file
	 * 
	 * @param fileScanner
	 * @return
	 */
	private char[][] getTableFromFile(Scanner fileScanner) {
		char[][] table = new char[SIZE][SIZE];
		
		for (int i = 0; i < SIZE; i++) {
			String line = fileScanner.nextLine();
			
			for (int j = 0; j < SIZE; j++) {
				table[i][j] = line.charAt(j);
			}
		}
		
		return table;
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

