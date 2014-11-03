package my.practice.collection;

/**
 * Implement a queue by using two stacks.
 * This is a toy implementation for a practice problem.
 * For serious application, use Deque in Java Collections Framework.
 * 
 * @author tdongsi
 *
 */
public class StackQueue<E> implements Queue<E> {
	private Stack<E> in;
	private Stack<E> out;
	
	public StackQueue() {
		in = new ArrayStack<E>();
		out = new ArrayStack<E>();
	}
	
	@Override
	public boolean isEmpty() {
		return (in.isEmpty() && out.isEmpty() );
	}
	
	@Override
	public void enqueue(E element) {
		while ( !out.isEmpty() ) {
			in.push(out.pop());
		}
		
		in.push(element);
	}
	
	@Override
	public E dequeue() {
		while ( !in.isEmpty() ) {
			out.push(in.pop());
		}
		
		return out.pop();
	}

}
