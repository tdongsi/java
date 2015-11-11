package my.practice.concurrency;

import static org.junit.Assert.*;

import org.junit.Test;

public class SimpleCounterTest {

	@Test
	public void testThreadSafety() {
		final int TRIAL_NUM = 10;
		Counter queue = new SimpleCounter();
		
		for (int i = 0; i < TRIAL_NUM; i++) {
			System.out.println( "Trial number: " + i);
			queue.reset();
			
			ProducerConsumer.produceAndConsume(queue);
			assertEquals(0, queue.current());
		}
	}

}
