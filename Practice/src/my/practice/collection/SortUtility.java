package my.practice.collection;

public class SortUtility {
	
	/**
	 * Interview question: Insert a node into a looped, sorted linked list.
	 * 
	 * @param head
	 * @param item
	 */
	public static Node insertIntoLoop(Node head, int value) {
		if (head == null) {
			// empty loop
			head = new Node(value, null);
			head.next = head;
			return head;
		}
		
		// Case 1: insert value at the beginning of the list
		if (value <= head.value) {
			Node temp = new Node(value, head);
			Node end = getLoopEnd(head);
			end.next = temp;
			return temp;
		}
		
		// Case 2: insert value in the middle of the list
		Node cur = head;
		while (cur.next != head) {
			if (cur.value < value && value <= cur.next.value) {
				Node temp = new Node(value, cur.next);
				cur.next = temp;
				return head;
			} else {
				cur = cur.next;
			}
		}
		
		// Case 3: now we reach the end of the list
		Node node = new Node(value, head);
		cur.next = node;
		return head;
	}
	
	/**
	 * Find the end of a looped linked list.
	 * 
	 * @param head: start of the looped linked list.
	 * @return
	 */
	public static Node getLoopEnd(Node head) {
		if (head == null) {
			throw new IllegalArgumentException("Empty list");
		}
		Node cur = head;
		while (cur.next != head) {
			cur = cur.next;
		}
		return cur;
	}
	
	/**
	 * Print the looped linked list.
	 * 
	 * @param head: start of the looped linked list.
	 */
	public static void printLoop(Node head) {
		if (head == null) {
			throw new IllegalArgumentException("Empty list");
		}
		
		Node end = getLoopEnd(head);
		Node cur = head;
		while (cur != end) {
			System.out.print(cur.value);
			System.out.print("->");
			cur = cur.next;
		}
		System.out.println(cur.value);
	}
	
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
