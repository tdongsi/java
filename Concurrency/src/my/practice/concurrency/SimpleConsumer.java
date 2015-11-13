package my.practice.concurrency;

import java.util.Random;

public class SimpleConsumer implements Runnable {

	private Counter queue;

	public SimpleConsumer(Counter queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		
		// Consume some pre-defined number of entries
		for (int i = 0; i < ProducerConsumer.ENTRY_NUM; i++) {
			consume();

			// wait randomly
			try {
				Thread.sleep(new Random().nextInt(100));
			} catch (InterruptedException e) {
				// nothing
			}
		}

	}

	public void consume() {
		queue.decrement();
	}

}
