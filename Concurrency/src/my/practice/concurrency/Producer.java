package my.practice.concurrency;

import java.util.Random;

public class Producer implements Runnable {
	
	// Using a counter to simulate a queue
	private Counter queue;
	
	public Producer(Counter queue) {
		this.queue = queue;
	}
	
	@Override
	public void run() {
		
		
		// Produce some pre-defined number of entries
		for (int i = 0; i < ProducerConsumer.ENTRY_NUM; i++) {
			queue.increment();
			
			// wait randomly
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch(InterruptedException e) {
				// nothing
			}
		}
	}
}
