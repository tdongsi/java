package my.practice.collection;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackQueueTest {

	@Test
	public void test() {
		Queue<Integer> queue = new StackQueue<Integer>();
		
		assertTrue( queue.isEmpty() );
		
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		
		assertFalse( queue.isEmpty() );
		
		assertEquals(1, queue.dequeue().intValue() );
		assertEquals(2, queue.dequeue().intValue() );
		assertEquals(3, queue.dequeue().intValue() );
	}

}
