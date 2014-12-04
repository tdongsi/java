package my.vip.applets.impedance;

import javax.swing.JFrame;

/**
 * @author tdongsi
 *
 */
public class MainApp {

	public static void main(String args[]) {
		JFrame window = new JFrame(
				"Impedance transformation in transformer circuit");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImpedanceTransform applet = new ImpedanceTransform();
		applet.init();
		applet.start();
		window.getContentPane().add(applet);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
	}

}
