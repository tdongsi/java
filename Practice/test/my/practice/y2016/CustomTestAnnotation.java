package my.practice.y2016;

public class CustomTestAnnotation {
	
	@CustomTest(author="me", jira="HDP-123", steps={}, version=@Version)
	public void test1() {
		doStuff();
		System.out.println("Run test 1");
	}
	
	@CustomTest(author="you", jira="HDP-321", steps={}, version=@Version(major=2))
	public void test2() {
		doStuff();
		System.out.println("Run test 2");
	}
	
	private void doStuff() {
		System.out.println("Do stuffs");
	}
	
	public void notTest() {
		System.out.println("Not a test");
	}

}
