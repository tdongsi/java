package my.practice.concurrency.consumer;

import java.util.Random;

import my.practice.concurrency.Consumer;
import my.practice.concurrency.Counter;
import my.practice.concurrency.ProducerConsumer;
import my.practice.concurrency.counter.PetersonCounter;

/**
 * Consumer class that uses Peterson's algorithm.
 * 
 * @author cdongsi
 *
 */
public class PetersonConsumer implements Consumer, Runnable {

	private PetersonCounter queue;

	public PetersonConsumer(Counter queue) {
		useQueue(queue);
	}

	@Override
	public void run() {
		this.queue.getTicket();
		
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
		if (queue instanceof PetersonCounter) {
			this.queue = (PetersonCounter) queue;
		} else {
			throw new IllegalArgumentException("PetersonCounter expected.");
		}
	}

}
