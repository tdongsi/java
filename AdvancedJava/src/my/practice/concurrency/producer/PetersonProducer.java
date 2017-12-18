package my.practice.concurrency.producer;

import java.util.Random;

import my.practice.concurrency.Counter;
import my.practice.concurrency.Producer;
import my.practice.concurrency.ProducerConsumer;
import my.practice.concurrency.counter.PetersonCounter;

/**
 * Producer class that uses Peterson's algorithm.
 * 
 * @author cdongsi
 *
 */
public class PetersonProducer implements Producer, Runnable {
	
	// Using a counter to simulate a queue
	private PetersonCounter queue;
	
	public PetersonProducer(Counter queue) {
		useQueue(queue);
	}
	
	@Override
	public void run() {
		this.queue.getTicket();
		
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

	@Override
	public void produce() {
		queue.increment();
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
