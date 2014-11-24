package my.vip.applets.transformer;

import javax.swing.JFrame;

/**
 * A project when I was working as a Teaching Assistant (TA) for the course Basic Electrical Enginering, 
 * under Vacation Internship Program (VIP).
 * An applet to illustrate how a transformer works. Programmed in Java 1.4. 
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class MainApp {

	/**
	 * Run this applet as an application
	 */
	public static void main(String args[]) {
		// Size of the application window
		int width = 420, height = 422;

		// Create the window container
		JFrame window = new JFrame("Transformer demonstration");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create and start the main applet
		Transformer applet = new Transformer();
		applet.init();
		applet.start();
		
		// Add the applet into the window frame container
		window.getContentPane().add(applet);
		window.setResizable(false);
		window.setSize(width, height);
		window.setVisible(true);
	}

}
