/**
 * 
 */
package my.practice.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * @author tdongsi
 *
 */
public class TypeDesc {
	
	private static String[] basic = { "class", "interface", "enum", "annotation" };
	private static String[] supercl = { "extends", "implements" };
	private static String[] iFace = { null, "extends" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TypeDesc desc = new TypeDesc();
		String name = "java.util.HashMap";
//		String name = "java.lang.annotation.Target";
		
		try {
			Class<?> startClass = Class.forName(name);
			desc.printType(startClass, 0, basic );
		}
		catch ( ClassNotFoundException e )
		{
			System.err.println(e);
		}
	}

	private void printType(Type type, int depth, String[] labels) {
		if ( type == null )
			return;
//		else if ( type instanceof Class<?> )
//		{
//			Class<?> temp = (Class<?>) type;
//			if ( temp.getSuperclass() == null )
//				return;
//		}
		
		// turn the Type into a Class object
		Class<?> cls = null;
		
		if ( type instanceof Class<?> )
		{
			// standard class
			cls = (Class<?>) type;
		} else if (type instanceof ParameterizedType )
		{
			// generic class type
			cls = (Class<?>) ((ParameterizedType)type).getRawType();
		} else {
			throw new Error( "Unexpected type");
		}
		
		
		// print this type
		for (int i=0; i < depth; i++ )
		{
			System.out.print("    ");
		}
		int kind = cls.isAnnotation()? 3 :
			cls.isEnum()? 2 :
				cls.isInterface()? 1: 0;
		
		if ( cls != Object.class )
		{
			System.out.print(labels[kind] + " ");
			System.out.print(cls.getCanonicalName());
		}
		
		
		// print generic type parameters if present
		TypeVariable<?>[] params = cls.getTypeParameters();
		if ( params.length > 0 )
		{
			System.out.print('<');
			for (TypeVariable<?> param : params) {
				System.out.print(param.getName());
				System.out.print(", ");
			}
			System.out.println("\b\b>");
		} else
		{
			// Not a generic type
			System.out.println();
		}
		
		
		// print out interfaces
		Type[] intefaces = cls.getGenericInterfaces();
		for (Type iface : intefaces) {
			printType( iface, depth+1, cls.isInterface()? iFace : supercl );
		}
		
		
		// print out superclasses
		printType( cls.getGenericSuperclass(), depth + 1, supercl );
		
	}

}
