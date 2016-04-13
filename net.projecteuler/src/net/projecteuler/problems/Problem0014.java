package net.projecteuler.problems;

/**
 * The following iterative sequence is defined for the set of positive integers:
 * n -> n/2 (n is even)
 * n -> 3n + 1 (n is odd)
 * Which starting number, under one million, produces the longest chain?
 * 
 * @author tdongsi
 *
 */
public class Problem0014 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long maxNumber = 1;
		long maxStep = 1;
		long stepCount;
		long range = 1000000;
		
		long timeIn;
		long timeOut;
		
		// A more efficient solution
		// Build the chain size array for entries < range: smaller time complexity, bounded memory
		timeIn = System.nanoTime();
		ChainSizeArray myList = new ChainSizeArray(range);

		for ( long i = 2; i < range; i++ )
		{
			stepCount = myList.getChainSize(i);

			if ( stepCount > maxStep ) {
				maxStep = stepCount;
				maxNumber = i;
			}

		}
		timeOut = System.nanoTime();
				
		System.out.println( "Running time " + (timeOut - timeIn)/1e9 );
		System.out.println( "Build a map for all: maxNumber " + maxNumber + " maxStep " + maxStep );
	}

}


class ChainSizeArray {
	private int[] list;
	private long range;
	
	public ChainSizeArray(long inputRange) {
		this.range = inputRange;
		list = new int[(int)inputRange];
		
		// Initialize the chain sizes for 2^k
		list[1] = 1;
		// set all list[i*2^k] = step + k
		int temp_num = 1;
		int temp_count = 1;
		while ( temp_num*2 < range ) {
			temp_num *= 2;
			temp_count++;
			list[temp_num] = temp_count;
		}
	}
	
	public long getChainSize( long number ) {
		long currNum = number;
		int stepCount = 0;

		while ( currNum > 1 ) {
			if ( currNum < range && list[(int)currNum] > 0 ) {
				stepCount += list[(int)currNum];
				break;
			}

			if ( currNum % 2 == 1 ) {
				// n -> 3n + 1
				currNum *= 3;
				currNum++;
			} else {
				currNum /= 2;
			}

			stepCount++;				
		}

		list[(int)number] = stepCount;
		// set list[number*2^k] = step + k
		long temp_num = number;
		int temp_count = stepCount;
		while ( temp_num*2 < range ) {
			temp_num *= 2;
			temp_count++;
			list[(int)temp_num] = temp_count;
		}
		
		return stepCount;
		
	}
}
