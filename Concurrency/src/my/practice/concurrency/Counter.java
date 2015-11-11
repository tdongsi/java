package my.practice.concurrency;

public interface Counter {
	/**
	 * Increment counter value by one.
	 */
	public void increment();
	
	/**
	 * Decrement counter value by one.
	 */
	public void decrement();
	
	/**
	 * Return the current counter value.
	 * 
	 * @return
	 */
	public int current();
	
	/**
	 * Reset the counter value.
	 */
	public void reset();
}
