package com.google.codejam.y2014;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.codejam.utility.Coordinate;

public class MineField {
	private char[][] field;
	private final int row;
	private final int col;
	private Set<Coordinate> mines;
	private Set<Coordinate> numbers;
	
	public static final char MINE = '*';
	public static final char BLANK = '.';
	public static final char NUMBER = '1';
	public static final char CLICK = 'c';
	
	private MineField(int row, int col)
	{
		this.row = row;
		this.col = col;
		field = new char[row][col];
		mines = new HashSet<Coordinate>(2*row*col);
		numbers = new HashSet<Coordinate>(2*row*col);
		
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				field[i][j] = BLANK;
			}
		}
		
	}
	
	/**
	 * Initialize the field with the input height and width
	 * @param row: height of the field
	 * @param col: width of the field
	 * @return
	 */
	public static MineField initField( int row, int col )
	{
		return new MineField(row, col);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(2*row*col);
		
		for (int i = 0; i < row; i++) {
			builder.append(field[i]);
			builder.append('\n');
		}
		
		return builder.toString();
	}
	
	/**
	 * Similar to toString(), except that number grids is represented as '.'
	 */
	public String toAnswerString() {
		// Change number grids to blank grids
		for (Coordinate coordinate : numbers) {
			field[coordinate.getX()][coordinate.getY()] = BLANK;
		}
		
		StringBuilder builder = new StringBuilder(2*row*col);
		
		for (int i = 0; i < row; i++) {
			builder.append(field[i]);
			builder.append('\n');
		}
		
		// Set it back
		for (Coordinate coordinate : numbers) {
			field[coordinate.getX()][coordinate.getY()] = NUMBER;
		}
		
		return builder.toString();
	}
	
	/**
	 * Set mines at the coordinates given by the input collection
	 * Update the grids from the input as mine grids.
	 * Update the adjacent grids as number grids.
	 * 
	 * @param mineSet
	 */
	public void addMines(Set<Coordinate> mineSet)
	{
		for (Coordinate coordinate : mineSet) {
			field[coordinate.getX()][coordinate.getY()] = MINE;
		}
		mines.addAll(mineSet);
		numbers.removeAll(mineSet);
		
		// update numbers
		for (Coordinate coordinate : mineSet) {
			int x = coordinate.getX();
			int y = coordinate.getY();
			
			if ( y-1 >= 0 && field[x][y-1] == BLANK )
			{
				numbers.add( new Coordinate(x, y-1));
			}
			if ( y+1 < col && field[x][y+1] == BLANK )
			{
				numbers.add( new Coordinate(x,y+1));
			}
			
			if ( x - 1 >= 0 )
			{
				if ( field[x-1][y] == BLANK )
				{
					numbers.add(new Coordinate(x-1, y));
				}
				if ( y-1 >= 0 && field[x-1][y-1] == BLANK )
				{
					numbers.add( new Coordinate(x-1, y-1));
				}
				if ( y+1 < col && field[x-1][y+1] == BLANK )
				{
					numbers.add( new Coordinate(x-1,y+1));
				}
			}
			if ( x + 1 < row )
			{
				if ( field[x+1][y] == BLANK )
				{
					numbers.add(new Coordinate(x+1, y));
				}
				if ( y-1 >= 0 && field[x+1][y-1] == BLANK )
				{
					numbers.add( new Coordinate(x+1, y-1));
				}
				if ( y+1 < col && field[x+1][y+1] == BLANK )
				{
					numbers.add( new Coordinate(x+1,y+1));
				}
			}
		}
		
		boolean change = numbers.removeAll(mineSet);
		if (change)
		{
			System.err.println("Assertion failed: It should not be!!!");
		}
		
		this.addNumbers(numbers);
	}

	private void addNumbers(Set<Coordinate> numberSet) {
		for (Coordinate coordinate : numberSet) {
			field[coordinate.getX()][coordinate.getY()] = NUMBER;
		}
	}
	
	public boolean isAnyBlank()
	{
		if ( numbers.size() + mines.size() >= row * col )
			return false;
		return true;
	}
	
	public void setClick()
	{
		if (!isAnyBlank())
		{
			throw new IllegalStateException("No legal grid to click");
		} else {
			// find the first blank grid and set it to CLICK
			// Simple one: do not use knowledge of mine and number locations
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < col; j++) {
					if ( field[i][j] == BLANK )
					{
						field[i][j] = CLICK;
						return;
					}
				}
			}
		} // end if isAnyBlank()
	}
}
