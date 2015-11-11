package my.practice.concurrency;

import java.util.Random;

public class Consumer implements Runnable {

	private Counter queue;

	public Consumer(Counter queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		
		// Consume some pre-defined number of entries
		for (int i = 0; i < ProducerConsumer.ENTRY_NUM; i++) {
			queue.decrement();

			// wait randomly
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				// nothing
			}
		}

	}

}
