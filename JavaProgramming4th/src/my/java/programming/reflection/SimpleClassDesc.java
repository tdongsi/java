/**
 * 
 */
package my.java.programming.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author tdongsi
 *
 */
public class SimpleClassDesc {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input = "java.lang.String";
		Class type = null;
		
		try {
			type = Class.forName(input);
		}
		catch ( ClassNotFoundException e )
		{
			System.err.println(e);
			return;
		}
		
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			int value = method.getModifiers();
			if ( Modifier.isPublic(value) ) {
				System.out.println(method);
			}
		}

	}
	
	
	/**
	 * Demonstrate method calls in 16.1.1
	 */
	private void getClassObject()
	{
		// There are 4 ways to get a Class object
		String var = "Hello";
		
		// 1. Use a class literal
		Class<String> c1 = String.class;
		
		// 2. Ask an object for its Class object
		Class<? extends String> c2 = var.getClass();
		
		// 3. Look it up using the full qualified name
		try {
			// NOTE: Class<? extends String> will not work
			Class<?> c3 = Class.forName("java.lang.String");
			
			// To turn the above into a type token of a known type
			Class<? extends String> c4 = c3.asSubclass(String.class);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// 4. Calling a reflection methods that return Class objects 
		
	}

}
