package my.practice.concurrency;

import my.practice.concurrency.consumer.SimpleConsumer;
import my.practice.concurrency.counter.SimpleCounter;
import my.practice.concurrency.producer.SimpleProducer;

/**
 * Main class for checking race condition
 * 
 * @author cdongsi
 *
 */
public class ProducerConsumer {
	
	final public static int ENTRY_NUM = 100;

	public static void main(String[] args) {
		
		Counter queue = new SimpleCounter();
		Producer producer = new SimpleProducer(queue);
		Consumer consumer = new SimpleConsumer(queue);
		produceAndConsume(queue, producer, consumer);
		System.out.println("Queue at the end: " + queue.current());
	}
	
	/**
	 * Run the simulation of producer-consumer problem
	 * with the given queue.
	 * 
	 * At the end of simulation run, the queue should have 0 item.
	 */
	public static void produceAndConsume(Counter queue, Producer producer, Consumer consumer) {
		
		Thread t1 = new Thread(producer);
		Thread t2 = new Thread(consumer);
		
		t1.start();
		t2.start();
		
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			// Nothing
		}
		
	}

}
