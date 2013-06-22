/**
 * 
 */
package my.java.programming.reflection;

import java.lang.reflect.Member;

/**
 * A simple class that display all members inside a class.
 * 
 * @author tdongsi
 *
 */
public class ClassContents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ClassContents test = new ClassContents();
		test.displayClassMembers("java.lang.String");
	}

	/**
	 * Display all the members in a class
	 * 
	 * @param qualifiedClassName: fully qualified class name
	 */
	public void displayClassMembers(String qualifiedClassName ) {
		try {
			Class<?> c = Class.forName(qualifiedClassName);
			System.out.println(c);
			printMembers( c.getFields() );
			printMembers( c.getMethods() );
			printMembers( c.getConstructors() );
		} catch (ClassNotFoundException e) {
			System.out.println("Unknown class");
			e.printStackTrace();
		}
	}

	private static void printMembers(Member[] members) {
		String temp;
		for (Member member : members) {
			if ( member.getDeclaringClass() == Object.class )
			{
				continue;
			}
			// Strip "java.lang." from the string 
			temp = member.toString().replaceAll("java\\.lang\\.", "");
			
			System.out.println("     " + temp);
		}
	}

}
