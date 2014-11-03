/**
 * 
 */
package my.practice.collection;

/**
 * General contract of a stack
 * 
 * @author tdongsi
 *
 */
public interface Stack<E> {
	
	/**
	 * Add an element into the stack
	 * 
	 * @param element
	 */
	public void push(E element);
	
	/**
	 * Return the top element of the stack
	 * 
	 * @return
	 */
	public E peek();
	
	/**
	 * Remove the top element of the stack and return that element
	 * 
	 * @return
	 */
	public E pop();
	
	/**
	 * Return true if the stack is empty
	 * 
	 * @return
	 */
	public boolean isEmpty();

}
