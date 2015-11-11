package my.practice.concurrency;

/**
 * Main class for checking race condition
 * 
 * @author cdongsi
 *
 */
public class ProducerConsumer {
	
	final public static int ENTRY_NUM = 10;

	public static void main(String[] args) {
		
		Counter queue = new SimpleCounter();
		
		Producer producer = new Producer(queue);
		Consumer consumer = new Consumer(queue);
		
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
		
		System.out.println("Queue at the end: " + queue.current());
	}

}
