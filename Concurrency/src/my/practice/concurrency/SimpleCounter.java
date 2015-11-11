package my.practice.concurrency;

public class SimpleCounter implements Counter {
	
	private int count = 0;

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
