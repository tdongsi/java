package my.practice.collection;

import java.util.ArrayList;

/**
 * Just a toy implementation of a stack. For practice purpose.
 * For serious application, use Deque interface in Java Collections Framework.
 * 
 * @author tdongsi
 */
public class Stack<E> {
	private ArrayList<E> array;
	
	public Stack() {
		array = new ArrayList<E>();
	}
	
	public void push(E object) {
		array.add(object);
	}
	
	public E pop() {
		if ( array.isEmpty() ) {
			throw new IllegalStateException("Stack is empty");
		} else {
			return array.remove(array.size()-1);
		}
	}
	
	public E peek() {
		if ( array.isEmpty() ) {
			throw new IllegalStateException("Stack is empty");
		} else {
			return array.get(array.size()-1);
		}
	}
	
	public boolean isEmpty() {
		return array.isEmpty();
	}
}
