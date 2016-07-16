package my.practice.collection;

import static org.junit.Assert.*;
//Replace this with another class/package for practice: e.g. my.practice.y2016.Practice.*.
import static my.practice.collection.SortUtility.*;

import org.junit.Test;

public class SortUtilityTest {

	@Test
	public void test_binarySearch() {
		int[] numbers = {1, 2, 3};
		
		assertEquals(-1, binarySearch(numbers, 4));
		assertEquals(2, binarySearch(numbers, 3));
		assertEquals(1, binarySearch(numbers, 2));
		assertEquals(0, binarySearch(numbers, 1));
		
		// Empty array
		numbers = new int[0];
		assertEquals(-1,  binarySearch(numbers, 2));
		
		// Singleton array
		int[] numbers2 = {1};
		assertEquals(-1, binarySearch(numbers2, 2));
		assertEquals(-1, binarySearch(numbers2, -2));
		assertEquals(0, binarySearch(numbers2, 1));
		
		int[] numbers3 = {1, 3, 4, 6, 7};
		assertEquals(1, binarySearch(numbers3, 3));
		assertEquals(-1, binarySearch(numbers3, 2));
		assertEquals(0, binarySearch(numbers3, 1));
	}
	
	@Test
	public void test_getLoopEnd() {
		// Loop: head -> node1 -> node2 -> node3 -> head
		Node node3 = new Node(3, null);
		Node node2 = new Node(2, node3);
		Node node1 = new Node(1, node2);
		Node head = new Node(1, node1);
		node3.next = head;
		
		Node end = SortUtility.getLoopEnd(head);
		assertEquals(node3, end);
		
		SortUtility.printLoop(head);
		
		// Loop: head -> node1 -> head
		node1.next = head;
		end = SortUtility.getLoopEnd(head);
		assertEquals(node1, end);
		SortUtility.printLoop(head);
		
		// Loop: head -> head
		head.next = head;
		end = SortUtility.getLoopEnd(head);
		assertEquals(head, end);
		SortUtility.printLoop(head);
	}

}
