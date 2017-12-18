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
	 * In Peterson's algorithm, each thread has a ticket (0 and 1)
	 * when interacting with the queue/counter.
	 * 
	 * To avoid changing Counter interface to get ticket from each thread, 
	 * this method is used to map thread ID to ticket. Therefore, synchronized block
	 * is used to create that mapping.
	 * This is the only place where intrinsic lock is used.
	 * 
	 * @return
	 */
	public void getTicket() {
		final int PETERSON_ALGO_LIMIT = 2;
		if (ticket >= PETERSON_ALGO_LIMIT) {
			throw new IllegalStateException("Only two threads/processes in Peterson's algorithm");
		} else {
			final long id = Thread.currentThread().getId();
			synchronized(this) {
				threadIdToTicket.put(id, ticket);
				ticket++;
//				System.out.println(threadIdToTicket.toString());
			}
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
			// If the other wants to enter, it's his turn, aka "after you"
			turn = 1-key;
			
			// While the other wants to enter, but not my turn
			// I will wait
			while (flags[1-key] && turn == 1-key ) {
				// waiting
				/*
				NOTE: not using Java's wait() because it requires
				monitor lock (synchronized method) and defeat the purpose
				of demonstration.
				Moreover, the original Peterson's algorithm does busy waiting.
				*/
			}
			
		} else {
			throw new IllegalStateException("Queue is not initialized correctly");
		}
	}
	
	/**
	 * Release mutual exclusion after leaving critical section
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
		acquire();
		count++;
		release();
//		System.out.println("Queue: " + count);
	}

	@Override
	public void decrement() {
		acquire();
		count--;
		release();
//		System.out.println("Queue: " + count);

	}

	@Override
	public int current() {
		return count;
	}

	/* 
	 * This method is called after simulation run is done.
	 * Therefore, it is not a critical section.
	 */
	@Override
	public void reset() {
		count = 0;
		ticket = 0;
		threadIdToTicket.clear();
		flags[0] = false;
		flags[1] = false;
		turn = 0;
	}

}
