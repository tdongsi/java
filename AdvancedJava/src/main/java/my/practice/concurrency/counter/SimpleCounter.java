package my.practice.concurrency.counter;

import my.practice.concurrency.Counter;

/**
 * Simulate a queue in Producer-Consumer problem.
 * Simple implementation with no thread-safety measure for the common variable.
 * 
 * @author cdongsi
 *
 */
public class SimpleCounter implements Counter {
	
	private int count = 0;

	@Override
	public void increment() {
		count++;
//		System.out.println("increment: Thread: " + Thread.currentThread().getId() );
	}

	@Override
	public void decrement() {
		count--;
//		System.out.println("decrement: Thread: " + Thread.currentThread().getId() );
	}

	@Override
	public int current() {
		return count;
	}

	@Override
	public void reset() {
		count = 0;
	}

}
