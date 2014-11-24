package my.vip.applets.dcmotor;

import javax.swing.JFrame;

/**
 * A project when I was working as a Teaching Assistant (TA) for the course Basic Electrical Enginering, 
 * under Vacation Internship Program (VIP).
 * An applet to illustrate how a DC (direct current) motor works. Programmed in Java 1.4. 
 * 
 * @author tdongsi
 * 
 */
public class MainApp {

	public static void main(String args[]) {
		JFrame window = new JFrame("Direct current electric motor");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		DcMotor applet = new DcMotor();
		applet.init();
		applet.start();
		window.getContentPane().add(applet);
		window.pack();
		window.setResizable(false);
		window.setVisible(true);
		
		/*
		 * System.out.println( "width: " + window.getContentPane().getWidth() +
		 * " height: " + window.getContentPane().getHeight() );
		 */
	}

}
