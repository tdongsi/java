package my.vip.applets.fr;

import javax.swing.JFrame;

/**
 * In this application, an interactive application demonstrates different passive analog filters, 
 * namely lowpass, highpass, bandpass, bandstop filters. 
 * Their ideal frequency response and actual frequency response will be demonstrated.
 * 
 * @author tdongsi
 */
public class MainApp {

	public static void main(String args[]) {
		JFrame window = new JFrame("Frequency response of filters");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		FrequencyResponse applet = new FrequencyResponse();
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
