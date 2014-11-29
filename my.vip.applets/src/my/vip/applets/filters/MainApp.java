package my.vip.applets.filters;

import javax.swing.JFrame;

/**
 * In this application, the true effect of the passive analog filters in practical applications will be demonstrated. 
 * In practice, most analog signals will come as arbitrary (periodic) waves, instead of sinusoidal waves. 
 * We demonstrate that by examining the effect of different passive analog filters on different periodic waves, such as square and sawtooth waves. 
 * Additionally, we examine the additive synthesis of periodic waves with a number of sinusoidal harmonics. 
 * We also demonstrate the effect of the analog filters on the input wave as addition of effects on individual sinusoidal harmonics.
 * 
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
