package my.practice.collection;

public class SortUtility {
	
	/**
	 * Simple binary search of integer from an array of integers.
	 * 
	 * @param items
	 * @param item
	 * @return the index of item in array "items". -1 if item not found.
	 */
	public static int binarySearch(int[] items, int item) {
		return _binarySearch(items, item, 0, items.length);
	}
	
	/**
	 * @param items
	 * @param item
	 * @param start
	 * @param end
	 * @return
	 */
	private static int _binarySearch(int[] items, int item, int start, int end) {
		// Empty list
		if (start == end) {
			return -1;
		}
		
		// Singleton list
		if (start == end-1) {
			if (items[start] == item) {
				return start;
			} else {
				// not found
				return -1;
			}
		}
		
		int med = (start + end)/2;
		if (items[med] == item) {
			return med;
		} else if (items[med] < item) {
			return _binarySearch(items, item, med+1, end);
		} else {
			return _binarySearch(items, item, start, med);
		}
		
	}

}
