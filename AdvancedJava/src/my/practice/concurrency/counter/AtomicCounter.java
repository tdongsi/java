package my.practice.concurrency.counter;

import java.util.concurrent.atomic.AtomicInteger;

import my.practice.concurrency.Counter;

/**
 * Simulate a queue in Producer-Consumer problem.
 * Achieve thread-safety by using atomic operations for the common variable.
 * 
 * @author cdongsi
 *
 */
public class AtomicCounter implements Counter {
	
	private AtomicInteger count = new AtomicInteger(0);

	@Override
	public void increment() {
		count.incrementAndGet();
//		System.out.println("increment: Thread: " + Thread.currentThread().getId() );
	}

	@Override
	public void decrement() {
		count.decrementAndGet();
//		System.out.println("decrement: Thread: " + Thread.currentThread().getId() );
	}

	@Override
	public int current() {
		return count.get();
	}

	@Override
	public void reset() {
		count.set(0);;
	}

}
