package my.practice.collection;

import static org.junit.Assert.*;

import org.junit.Test;

public class StackTest {

	@Test
	public void test_Stack() {
		ArrayStack<Integer> stack = new ArrayStack<Integer>();
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		assertEquals(3, stack.peek().intValue() );
		
		assertEquals(3, stack.pop().intValue() );
		assertEquals(2, stack.pop().intValue() );
		assertEquals(1, stack.pop().intValue() );
	}

}
