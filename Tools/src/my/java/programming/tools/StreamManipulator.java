package my.java.programming.tools;

import java.io.InputStream;
import java.io.PrintStream;

/**
 * You can implement StreamManipulator in a separate class or implement it
 * on-the-fly with anonymous class.
 * Check SystemUtility.processStream()
 * 
 * @author cdongsi
 *
 */
public interface StreamManipulator {

	public void process(InputStream in, PrintStream out);

}
