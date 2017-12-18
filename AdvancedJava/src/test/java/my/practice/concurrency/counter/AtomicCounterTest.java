package my.practice.concurrency.counter;

import static org.junit.Assert.*;
import my.practice.concurrency.Consumer;
import my.practice.concurrency.Counter;
import my.practice.concurrency.Producer;
import my.practice.concurrency.ProducerConsumer;
import my.practice.concurrency.consumer.SimpleConsumer;
import my.practice.concurrency.producer.SimpleProducer;

import org.junit.Test;

public class AtomicCounterTest {

	@Test
	public void testThreadSafety() {
		final int TRIAL_NUM = 5;
		Counter queue = new AtomicCounter();
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
