package my.practice.y2016;

import java.lang.reflect.Method;

public class TestRunner {
	
	public static void main(String[] args) {
		// Pretend that one of the argument is test class
		final String ARG = "my.practice.y2016.CustomTestAnnotation";
		
		try {
			Class<?> c = Class.forName(ARG);
			System.out.println("Running test class: "+ c.getName());
			
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				if (method.getDeclaringClass() == c) {
					System.out.println("Running: " + method.getName());
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Test Runner could not find class");
		}
		
		
	}

}
