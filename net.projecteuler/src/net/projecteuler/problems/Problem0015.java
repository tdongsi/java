package net.projecteuler.problems;


/**
 * Starting in the top left corner of a 22 grid, and only being able to move to the right and down, 
 * there are exactly 6 routes to the bottom right corner.
 * How many such routes are there through a 2020 grid?
 * 
 * Mathematical analysis:
 * It can be proven that n(x,y) = 2*n(x-1,y-1) + n(x-2,y) + n(y,x-2);
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class Problem0015 {

	public static void main(String[] args) {
		int dist = 20;
		
		long routeNumber = getRouteNumber(dist,dist);
		
		System.out.println( "number of routes: " + routeNumber );
	}
	
	
	/**
	 * Recursive implementation using the closed-form formula.
	 * 
	 * @param xDistance: x coordinate of the destination point
	 * @param yDistance: y coordinate of the destination point
	 * @return number of routes
	 */
	private static long getRouteNumber( int xDistance, int yDistance ) {
		if ( xDistance == 0 && yDistance == 0 ) {
			return 0;
		} else if ( xDistance == 0 || yDistance == 0  ) {
			return 1;
		} else if ( xDistance == 1 ) {
			return yDistance+1;
		} else if ( yDistance == 1 ) {
			return xDistance+1;
		} else {
			return 2*getRouteNumber(xDistance-1,yDistance-1) + getRouteNumber(xDistance-2,yDistance) + getRouteNumber(xDistance,yDistance-2);
		}
	}

}

