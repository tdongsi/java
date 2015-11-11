package my.java.programming.dynamic;

public class MinimalSubArray {

	public static void main(String[] args) {
		MinimalSubArray solver = new MinimalSubArray();
		
		int[] intArray = {1, -3, 5, -2, 9, -8, -6, 4};
		int[] subArray;
		
		subArray = solver.minimalSubArray( intArray );
		
		for (int i=0; i < subArray.length; i++ ) {
			System.out.print( " " + subArray[i] );
		}
		

	}
	
	
	/**
	 * Find the sub array from an input integer array with maximum sum.
	 * This method takes O(n) times and O(1) space. 
	 * @param inputArray: the integer array
	 * @return
	 */
	public int[] minimalSubArray( int[] inputArray ) {
		int currSum = 0;
		int minSum = inputArray[0];
		int jMin = -1;
		
		int jMax = -1; // starting index of the maximal subarray 
		int maxSub = inputArray[0]; // sum of the maxial subarray
		int iMax = -1; // ending index of the maximal subarray
		
		for ( int i=0; i < inputArray.length; i++ ) {
			currSum += inputArray[i];
			
			if ( currSum < minSum ) {
				minSum = currSum;
				jMin = i;
			}
			
			if ( currSum - minSum > maxSub ) {
				maxSub = currSum - minSum;
				jMax = jMin;
				iMax = i;
			}
		}
		
//		System.out.println( "maxSub " + maxSub + " " + iMax + " " + jMax);
		
		int[] subArray = new int[iMax - jMax];
		for (int subIndex = 0, inputIndex = jMax+1; subIndex < subArray.length && inputIndex <= iMax; subIndex++, inputIndex++ ) {
			subArray[subIndex] = inputArray[inputIndex];
		}
		return subArray;
	}

}
