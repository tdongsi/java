package my.practice.collection;

import static org.junit.Assert.*;

import org.junit.Test;

public class NodeTest {

	@Test
	public void test_merge1() {
		Node leftEnd = new Node(3, null);
		Node leftMid = new Node(2, leftEnd);
		Node leftHead = new Node(1, leftMid);
		Node.printList(leftHead);
		
		Node rEnd = new Node(6, null);
		Node rMid = new Node(5, rEnd);
		Node rHead = new Node(4, rMid);
		Node.printList(rHead);
	}

}
