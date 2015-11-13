package my.practice.concurrency;

import java.util.Random;

public class SimpleProducer implements Runnable {
	
	// Using a counter to simulate a queue
	private Counter queue;
	
	public SimpleProducer(Counter queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		
		// Produce some pre-defined number of entries
		for (int i = 0; i < ProducerConsumer.ENTRY_NUM; i++) {
			produce();
			
			// wait randomly
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch(InterruptedException e) {
				// nothing
			}
		}
	}

	public void produce() {
		queue.increment();
	}
}
