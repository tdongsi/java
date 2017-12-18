package my.practice.concurrency.counter;

import static org.junit.Assert.*;
import my.practice.concurrency.Consumer;
import my.practice.concurrency.Counter;
import my.practice.concurrency.Producer;
import my.practice.concurrency.ProducerConsumer;
import my.practice.concurrency.consumer.SimpleConsumer;
import my.practice.concurrency.counter.SimpleCounter;
import my.practice.concurrency.producer.SimpleProducer;

import org.junit.Ignore;
import org.junit.Test;

/**
 * This test might pass, but it is expected to fail.
 * SimpleCounter is not thread-safe.
 * 
 * @author cdongsi
 *
 */
public class SimpleCounterTest {

	/**
	 * This test will likely fail due to race condition.
     * Remove @Ignore to demonstrate thread-safety.
	 */
	@Test
	@Ignore
	public void testThreadSafety() {
		final int TRIAL_NUM = 10;
		Counter queue = new SimpleCounter();
		Producer producer = new SimpleProducer(queue);
		Consumer consumer = new SimpleConsumer(queue);
		
		for (int i = 0; i < TRIAL_NUM; i++) {
			System.out.println( "Trial number: " + i);
			queue.reset();
			
			ProducerConsumer.produceAndConsume(queue, producer, consumer);
			assertEquals(0, queue.current());
		}
	}

}
