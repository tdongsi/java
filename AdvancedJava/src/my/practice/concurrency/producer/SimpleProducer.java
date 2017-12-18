package my.practice.concurrency.producer;

import java.util.Random;

import my.practice.concurrency.Counter;
import my.practice.concurrency.Producer;
import my.practice.concurrency.ProducerConsumer;

public class SimpleProducer implements Producer, Runnable {
	
	// Using a counter to simulate a queue
	private Counter queue;
	
	public SimpleProducer(Counter queue) {
		useQueue(queue);
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

	@Override
	public void produce() {
		queue.increment();
	}
	
	@Override
	public void useQueue(Counter queue) {
		this.queue = queue;
	}
}
