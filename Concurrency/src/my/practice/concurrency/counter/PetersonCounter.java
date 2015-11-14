package my.practice.concurrency.counter;

import java.util.HashMap;
import java.util.Map;

import my.practice.concurrency.Counter;

/**
 * Implement mutual exclusion for this counter using Peterson's algorithm.
 * There is no lock, no atomic operations. Completely software solution.
 * The limit is only two processes (or threads in this example).
 * 
 * When the producer and consumer acquire this queue implementation,
 * they have to call getTicket to register threadId with ticket number.
 * 
 * The idea of Peterson's algorithm:
 * 
 * @author cdongsi
 *
 */
public class PetersonCounter implements Counter {
	
	private int count = 0;
	
	// Shared variables for Peterson's algorithm
	int turn;
	boolean[] flags = new boolean[2];
	
	// Mapping thread ID to ticket
	private int ticket = 0;
	Map<Long, Integer> threadIdToTicket = new HashMap<>();
	
	/**
	 * Get a ticket number for the queue, either 0 or 1.
	 * 
	 * @return
	 */
	public void getTicket() {
		final int PETERSON_ALGO_LIMIT = 2;
		if (ticket >= PETERSON_ALGO_LIMIT) {
			throw new IllegalStateException("Only two threads/processes in Peterson's algorithm");
		} else {
			long id = Thread.currentThread().getId();
			threadIdToTicket.put(id, ticket);
			ticket++;
		}
	}
	
	/**
	 * Acquire mutual exclusion before entering critical section
	 */
	private void acquire() {
		final long id = Thread.currentThread().getId();
		
		if ( threadIdToTicket.containsKey(id) ) {
			final int key = threadIdToTicket.get(id);
			
			// I want to enter
			flags[key] = true;
			// If the other wants to enter, it's his turn
			turn = 1-key;
			
			// While I want to enter, but not my turn
			// I will wait
			while (flags[key] && turn == 1-key ) {
				// waiting
				// TODO: wait() and notifyAll()
			}
			
		} else {
			throw new IllegalStateException("Queue is not initialized correctly");
		}
	}
	
	/**
	 * Relase mutual exclusion after leaving critical section
	 */
	private void release() {
		final long id = Thread.currentThread().getId();
		
		if ( threadIdToTicket.containsKey(id) ) {
			final int key = threadIdToTicket.get(id);
			flags[key] = false;
		} else {
			throw new IllegalStateException("Queue is not initialized correctly");
		}
	}

	@Override
	public void increment() {
		count++;
//		System.out.println("Queue: " + count);
	}

	@Override
	public void decrement() {
		count--;
//		System.out.println("Queue: " + count);

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
