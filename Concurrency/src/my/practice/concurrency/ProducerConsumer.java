package my.practice.concurrency;

import my.practice.concurrency.counter.SimpleCounter;

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
		produceAndConsume(queue);
		System.out.println("Queue at the end: " + queue.current());
	}
	
	/**
	 * Run the simulation of producer-consumer problem
	 * with the given queue.
	 * 
	 * At the end of simulation run, the queue should have 0 item.
	 * 
	 * @param queue
	 */
	public static void produceAndConsume(Counter queue) {
		
		SimpleProducer producer = new SimpleProducer(queue);
		SimpleConsumer consumer = new SimpleConsumer(queue);
		
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
