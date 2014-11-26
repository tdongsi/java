package my.vip.applets.filters;

import javax.swing.JFrame;

/**
 * @author tdongsi
 *
 */
public class MainApp {

	public static void main(String args[]) {
		JFrame window = new JFrame("Filters demonstration");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Filters applet = new Filters();
		applet.init();
		applet.start();
		window.getContentPane().add(applet);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		// System.out.println( "width: " + window.getWidth() + " height: " +
		// window.getHeight() );
	}

}
