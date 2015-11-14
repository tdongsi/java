package my.practice.concurrency.counter;

import my.practice.concurrency.Counter;

/**
 * The Java's synchronized construct is very much like monitor.
 * Only one thread can be active any time in synchronized method, like monitor.
 * 
 * @author cdongsi
 *
 */
public class MonitorCounter implements Counter {
	private int count = 0;

	@Override
	public synchronized void increment() {
		count++;
//		System.out.println("Queue: " + count);
	}

	@Override
	public synchronized void decrement() {
		count--;
//		System.out.println("Queue: " + count);
	}

	@Override
	public synchronized int current() {
		return count;
	}

	@Override
	public synchronized void reset() {
		count = 0;
	}
}
