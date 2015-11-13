package my.practice.concurrency.counter;

import static org.junit.Assert.*;
import my.practice.concurrency.Counter;
import my.practice.concurrency.ProducerConsumer;

import org.junit.Test;

public class AtomicCounterTest {

	@Test
	public void testThreadSafety() {
		final int TRIAL_NUM = 5;
		Counter queue = new AtomicCounter();
		
		for (int i = 0; i < TRIAL_NUM; i++) {
			System.out.println( "Trial number: " + i);
			queue.reset();
			
			ProducerConsumer.produceAndConsume(queue);
			assertEquals(0, queue.current());
		}
	}

}
