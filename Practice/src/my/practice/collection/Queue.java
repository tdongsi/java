package my.practice.collection;

/**
 * General contract of a simple queue.
 * For serious application, use Deque<E> in Java Collections Framework.
 * 
 * @author tdongsi
 *
 */
public interface Queue<E> {
	
	/**
	 * Add an element into the queue
	 * 
	 * @param element
	 */
	public void enqueue(E element);
	
	
	/**
	 * Remove the first element of the queue and return that element
	 * 
	 * @return
	 */
	public E dequeue();
	
	/**
	 * Return true if the queue is empty
	 * 
	 * @return
	 */
	public boolean isEmpty();

}
