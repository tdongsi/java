package com.google.codejam.y2013;

/**
 * Simple class for a pair of numbers: (x,y)
 * @author tdongsi
 *
 */
public class Coordinate
{
	private final int x;
	private final int y;
	
	// Cache hashCode
	private volatile int hashCode;
	
	@Override
	public int hashCode() {
		// Use the cached hashCode
		int result = hashCode;
		final int prime = 31;
		
		// Compute the cached hashCode only once
		if (result == 0) {
			result = 1;
			result = prime * result + x;
			result = prime * result + y;
			hashCode = result;
		}
		
		return result;
	}
	
	public int getY() {
		return x;
	}

	public int getX() {
		return y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		Coordinate other = (Coordinate) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		
		return true;
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public Coordinate(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
}
