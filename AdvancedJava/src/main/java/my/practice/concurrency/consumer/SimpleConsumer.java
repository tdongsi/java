package my.practice.concurrency.consumer;

import java.util.Random;

import my.practice.concurrency.Consumer;
import my.practice.concurrency.Counter;
import my.practice.concurrency.ProducerConsumer;

public class SimpleConsumer implements Consumer, Runnable {

	private Counter queue;

	public SimpleConsumer(Counter queue) {
		useQueue(queue);
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

	@Override
	public void consume() {
		queue.decrement();
	}
	
	@Override
	public void useQueue(Counter queue) {
		this.queue = queue;
	}

}
