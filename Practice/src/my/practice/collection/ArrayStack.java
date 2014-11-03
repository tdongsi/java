package my.practice.collection;

import java.util.ArrayList;

/**
 * Just a toy implementation of a stack, using an ArrayList. For practice purpose.
 * For serious application, use Deque interface in Java Collections Framework.
 * 
 * @author tdongsi
 */
public class ArrayStack<E> implements Stack<E> {
	private ArrayList<E> array;
	
	public ArrayStack() {
		array = new ArrayList<E>();
	}
	
	@Override
	public void push(E object) {
		array.add(object);
	}
	
	@Override
	public E pop() {
		if ( array.isEmpty() ) {
			throw new IllegalStateException("Stack is empty");
		} else {
			return array.remove(array.size()-1);
		}
	}
	
	@Override
	public E peek() {
		if ( array.isEmpty() ) {
			throw new IllegalStateException("Stack is empty");
		} else {
			return array.get(array.size()-1);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
}
