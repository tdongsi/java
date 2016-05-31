package my.practice.y2016;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {
	
	public static void main(String[] args) {
		// Pretend that one of the argument is test class
		final String ARG = "my.practice.y2016.CustomTestAnnotation";
		
		try {
			Class<?> c = Class.forName(ARG);
			Object t = c.newInstance();
			System.out.println("Running test class: "+ c.getName());
			
			Method[] methods = c.getMethods();
			for (Method method : methods) {
				if (method.getDeclaringClass() == c) {
					
					// Only run methods with @CustomTest annotation
					CustomTest ann = method.getAnnotation(CustomTest.class);
					if ( ann != null ) {
						System.out.println("Running: " + method.getName());
						System.out.println("Author: " + ann.author() + " Version: " + ann.version().toString() );
						method.invoke(t);
					}
					
				}
			}
			
		} catch (ClassNotFoundException e) {
			System.out.println("Test Runner could not find class");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
