package my.java.programming.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * For draining the output stream, especially from a running Process.
 * From Javadoc,  failure to promptly read the output stream of the subprocess
 * may cause the subprocess to block.
 * 
 * @author tdongsi
 *
 */
public class InputStreamDrainer extends Thread {
	private InputStream is;
	private PrintStream os;
	
	/**
	 * Construct an instance with the specified input stream and output stream
	 * 
	 * @param is
	 * @param os
	 */
	public InputStreamDrainer(InputStream is, PrintStream os) {
		this.is = is;
		this.os = os;
	}

	/**
	 * Construct an instance with the specified input stream and System.out as output stream
	 * 
	 * @param is
	 */
	public InputStreamDrainer(InputStream is) {
		this.is = is;
		this.os = System.out;
	}
	
	public void run()
    {
        try
        {
        	InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line=null;
            while ( (line = br.readLine()) != null)
                os.println(line);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
    }
	
}
