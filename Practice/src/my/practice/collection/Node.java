package my.practice.collection;

/**
 * Class for linked-list node.
 * 
 * @author cdongsi
 *
 */
public class Node {
	
	// NOTE: Not OOP, but help simplifying code.
	public int value;
	public Node next;
	
	public Node(int value, Node next) {
		this.value = value;
		this.next = next;
	}
	
	/**
	 * Merge two sorted linked-list in place.
	 * Useful for merge sort.
	 * 
	 * @param first: head of first linked-list
	 * @param second: head of second linked-list
	 * @return head of merged linked-list
	 */
	public static Node merge(Node first, Node second) {
		return null;
	}
	
	public static void printList(Node head) {
		if (head == null) {
			System.out.println("[]");
		} else {
			Node cur = head;
			System.out.print("[");
			while (cur != null) {
				System.out.print(Integer.toString(cur.value) + " -> ");
				cur = cur.next;
			}
			System.out.print("null]\n");
		}
	}

}
