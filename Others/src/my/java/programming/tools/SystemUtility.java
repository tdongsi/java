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
	 * Enforce that this class should not be instantiated. Suppress default
	 * constructor.
	 */
	private SystemUtility() {
		throw new AssertionError("Do not instantiate.");
	}

	/**
	 * Method to perform system calls
	 * 
	 * @param command: string of command such as "ls -l"
	 * @param isVerbose: flag indicating whether to display output (true) or not (false).
	 * @param isEcho: flag indicating whether to display the command (true) or not (false).
	 * @return
	 */
	public static int runCommand(String command, boolean isVerbose, boolean isEcho) {
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
	public static int runCommand(String command, boolean isVerbose, boolean isEcho, OutputStream os ) {
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
	
	
	/**
	 * Equivalent to "cp src dest".
	 * @param src: path to the source file
	 * @param dest: path to the destination file
	 * @return true if the copying is success, false otherwise.
	 */
	public static boolean copyFile(String src, String dest)
	{
		InputStream inStream = null;
		OutputStream outStream = null;
		boolean success = true;
		
		try {
			File inFile = new File(src);
			File outFile = new File(dest);
			
			inStream = new FileInputStream( inFile );
			outStream = new FileOutputStream( outFile );
			
			byte[] buffer = new byte[1024];
			 
            int length;
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
 
            if (inStream != null)
            	inStream.close();
            if (outStream != null)
            	outStream.close();
 
            System.out.println("Done copying file.");
            
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
			success = false;
		}
		catch (IOException e) {
            e.printStackTrace();
            success = false;
        }
		
		return success;
	}
	
	
	/**
     * A lightweight, recursive replacement of Apache commons-io FileUtils.deleteDirectory()
     * Equivalent to "rm -r path".
     * @param path Root File Path
     * @return true iff the file and all sub files/directories have been removed
     * @throws FileNotFoundException
     */
    public static boolean deleteRecursive(File path) throws FileNotFoundException
    {
        if (!path.exists()) throw new FileNotFoundException(path.getAbsolutePath());
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles())
            {
//        		System.out.println( f.toString() );
                ret = ret && deleteRecursive(f);
            }
        }
        return ret && path.delete();
    }
    
    /**
     * Find all the files with some defined extension.
     * Equivalent to "ls path/*.ext".
     * @param path Path to the directory
     * @param extension String of format ".ext" to indicate the desired "extension". Use empty string "" for all types.
     * @return A set of strings for all file names.
     */
    public static Set<String> findAllFilesOfType( String path, final String extension )
    {
    	Set<String> fileList = new HashSet<String>();
    	
    	File dir = new File( path );
    	File[] files;
    	
    	if ( extension.equals("") )
    	{
    		files = dir.listFiles();
    	} else
    	{
    		files = dir.listFiles(new FilenameFilter() {
    			public boolean accept( File dir, String filename )
    			{
    				return filename.endsWith(extension);
    			}
    		});
    	}
    	
    	for( int i = 0; i < files.length; i++ )
    	{
    		fileList.add( files[i].getName() );
    	}
    	
    	return fileList;
    }
}
