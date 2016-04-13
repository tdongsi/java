package my.java.programming;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import my.java.programming.tools.StreamManipulator;
import my.java.programming.tools.SystemUtility;

/**
 * This Java program allows doing ad-hoc stream processing quickly.
 * You can accept zero argument or two arguments for input and output filenames.
 * 
 * In case of zero argument, the command can be something like this:
 * > java MainStreamApp >fileOut <fileIn
 * 
 * where > and < are input and output redirection. This will make testing easier.
 * 
 * Example: In Eclipse, from the Project folder:
 * MyMac:Tools cdongsi$ java -cp bin/ my.java.programming.MainStreamApp <in.stream >out.stream
 * 
 * @author cdongsi
 *
 */
public class MainStreamApp {
	
	public static void main(String[] args) {
		
		InputStream in;
		PrintStream out;
		
		if ( args.length == 0 ) {
			// Use System.in and System.out
			in = System.in;
			out = System.out;
		} else if (args.length == 2) {
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
		} else {
			System.err.println("Unexpected number of arguments");
			System.err.println("Use 0 argument to input/output to/from command-line.");
			System.err.println("Use 2 arguments to input/output to/from files.");
			return;
		}
		
		// For simple processing, use anonymous class
		// For more complex processing, implement StreamManipulator interface
		// in a separate class
		SystemUtility.processStream(in, out, new StreamManipulator() {
			
//			@Override
//			public void process(InputStream in, PrintStream out) {
//				
//				// Add column pymt_txn_application_source_type_key
//				final String oldColumns = "(pymt_merchant_key,txn_date_key,txn_type_key,card_type_key,is_swiped,region_key,currency_key,txn_amount,is_rejected,pymt_src_system_key,batch_date_key,pymt_plan_type_key,ihub_txn_id,is_ach)";
//				final String newColumns = "(pymt_merchant_key,txn_date_key,txn_type_key,card_type_key,is_swiped,region_key,currency_key,txn_amount,is_rejected,pymt_src_system_key,batch_date_key,pymt_plan_type_key,ihub_txn_id,is_ach,pymt_txn_application_source_type_key)";
//				
//				// Add value 1 for column pymt_txn_application_source_type_key
//				final String oldEnd = ");";
//				final String newEnd = ",1);";
//				
//				try( Scanner lineScanner = new Scanner(in) ) {
//					StringBuilder sb = new StringBuilder();
//					
//					while (lineScanner.hasNext()) {
//						String line = lineScanner.nextLine();
//						
//						String newColLine = line
//								.replace(oldColumns, newColumns)
//								.replace(oldEnd, newEnd);
//						
////						System.out.println(newColLine);
//						sb.append(newColLine);
//						sb.append("\n");
//					}
//										
//					out.append(sb.toString());
//				}
//			}
			
			// This simple processing is equivalent to Python idiom:
			//  out = ",".join(in)
			@Override
			public void process(InputStream in, PrintStream out) {
				try( Scanner lineScanner = new Scanner(in) ) {
					StringBuilder sb = new StringBuilder();
					
					while (lineScanner.hasNext()) {
						sb.append(lineScanner.nextLine());
						sb.append(',');
					}
					
					sb.deleteCharAt(sb.length()-1);
//					System.out.println(sb.toString());
					
					out.append(sb.toString());
				}
			}
		});
	}
	
}
