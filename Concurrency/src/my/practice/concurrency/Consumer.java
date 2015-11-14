package my.practice.concurrency;

public interface Consumer {
	
	/**
	 * Remove some item from a common item queue
	 */
	public void consume();
	
	/**
	 * Use Counter as an item queue
	 * 
	 * @param queue
	 */
	public void useQueue(Counter queue);

}
