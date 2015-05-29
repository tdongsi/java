package my.java.programming.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tdongsi
 *
 */
public class SystemUtility {

	/**
	 * Method to perform system calls
	 * 
	 * @param command: string of command such as "ls -l"
	 * @param isVerbose: flag indicating whether to display output (true) or not (false).
	 * @param isEcho: flag indicating whether to display the command (true) or not (false).
	 * @return
	 */
	public int runCommand(String command, boolean isVerbose, boolean isEcho) {
		if (isEcho) {
			System.out.println("> " + command);
		}

		int errorLevel;

		try {

			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec( command );
			
			InputStreamDrainer outputDrainer;
			InputStreamDrainer errorDrainer;

			if (isVerbose) {
				outputDrainer = new InputStreamDrainer(p.getInputStream());
				// TRICKY: Just outputDrainer is not enough
				errorDrainer = new InputStreamDrainer(p.getErrorStream());
			} else 
			{
				PrintStream dummyStream = new PrintStream( new NullOutputStream() );
				outputDrainer = new InputStreamDrainer(p.getInputStream(), dummyStream);
				// TRICKY: Just outputDrainer is not enough
				errorDrainer = new InputStreamDrainer(p.getErrorStream(), dummyStream);
			}
			
			// Draining the output stream
			// NOTE: Sequential BufferedReader will eventually fail.
			outputDrainer.start();
			errorDrainer.start();

			outputDrainer.join();
			errorDrainer.join();

			errorLevel = p.waitFor();

		} catch (IOException e) {
			System.out.println("Exception happened: ");
			e.printStackTrace();
			errorLevel = -1;
		} catch( InterruptedException e ) {
			System.out.println( "Command interrupted");
			errorLevel = -1;
		}

		return errorLevel;
	}
	
	
	/**
	 * Overloaded runCommand to redirect the output from the command into a String
	 * 
	 * Example usage:
	 * ByteArrayOutputStream os = new ByteArrayOutputStream();
	 * system.runCommand("cmd /c igupgrade", false, true, os);
	 * String output = os.toString();
	 * 
	 * @param command
	 * @param isVerbose
	 * @param isEcho
	 * @param os the output sink that pass into the method for storing the output
	 * @return
	 */
	public int runCommand(String command, boolean isVerbose, boolean isEcho, OutputStream os ) {
		if (isEcho) {
			System.out.println("> " + command);
		}

		int errorLevel;

		try {

			// using the Runtime exec method:
			Process p = Runtime.getRuntime().exec( command );
			
			PrintStream ps = new PrintStream( os );
			
			InputStreamDrainer outputDrainer = new InputStreamDrainer(p.getInputStream(), ps);
			// TRICKY: Just outputDrainer is not enough, e.g. igupgrade
			InputStreamDrainer errorDrainer = new InputStreamDrainer(p.getErrorStream(), ps);
			
			// Draining the output stream
			// NOTE: Sequential BufferedReader will eventually fail.
			outputDrainer.start();
			errorDrainer.start();

			outputDrainer.join();
			errorDrainer.join();

			errorLevel = p.waitFor();
			
			if (isVerbose) {
				System.out.println( os.toString() );
			}

		} catch (IOException e) {
			System.out.println("Exception happened: ");
			e.printStackTrace();
			errorLevel = -1;
		} catch( InterruptedException e ) {
			System.out.println( "Command interrupted");
			errorLevel = -1;
		}

		return errorLevel;
	}
	
}
