package my.java.programming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import my.java.programming.tools.StreamManipulator;
import my.java.programming.tools.SystemUtility;

public class MainStreamApp {
	
	public static void main(String[] args) {
		
		InputStream in;
		PrintStream out;
		
		if ( args.length == 0 ) {
			// Use System.in and System.out
			in = System.in;
			out = System.out;
		} else {
			// Use the arguments as file path/name input file and output file
			try {
				in = new FileInputStream(args[0]);
				out = new PrintStream(args[1]);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.err.println("Cannot find file: " + args.toString() );
				e.printStackTrace();
				return; // stop processing
			}
		}
		
		// For simple processing, use anonymous class
		// For more complex processing, implement StreamManipulator interface
		// in a separate class
		SystemUtility.processStream(in, out, new StreamManipulator() {
			
			/* 
			 * This simple processing is equivalent to Python idiom:
			 * out = ",".join(in)
			 * 
			 * (non-Javadoc)
			 * @see my.java.programming.tools.StreamManipulator#process(java.io.InputStream, java.io.PrintStream)
			 */
			@Override
			public void process(InputStream in, PrintStream out) {
				try( Scanner lineScanner = new Scanner(in) ) {
					StringBuilder sb = new StringBuilder();
					
					while (lineScanner.hasNext()) {
						sb.append(lineScanner.nextLine());
						sb.append(',');
					}
					
					sb.deleteCharAt(sb.length()-1);
					System.out.println(sb.toString());
					
					out.append(sb.toString());
				}
			}
		});
	}
	
}
