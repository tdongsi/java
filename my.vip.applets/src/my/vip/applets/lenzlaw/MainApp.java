/**
 * 
 */
package my.vip.applets.lenzlaw;

import javax.swing.JFrame;

/**
 * A TA project for the course Basic Electrical Enginering, under Vacation Internship Program (VIP).
 * An applet/application to illustrate the Lenz's Law. Programmed in Java 1.4. 
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class MainApp {

	public static void main( String args[] ) {
		JFrame window = new JFrame( "Lenz's Law Demonstration" );
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LenzLaw applet = new LenzLaw();
		applet.init();
		applet.start();
		
		window.getContentPane().add(applet);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}

}
