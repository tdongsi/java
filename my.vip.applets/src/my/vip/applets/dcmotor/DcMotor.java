package my.vip.applets.dcmotor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DcMotor extends JApplet implements ActionListener, ChangeListener {
	final int WMIN = 35, WMAX = 49, WINIT = 40;
	final int IMG = 15, BTM = 2;
	JLabel top;
	CustomLabel bottom;
	ImageIcon[] topIcon;
	ImageIcon[] bottomIcon;
	JSlider wSlider;
	JButton changePoles, pause, reset;
	private int animationDelay;
	private Timer animationTimer;
	int topNum, bottomNum;

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = DcMotor.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void init() {
		JPanel menu = new JPanel(new BorderLayout());

		JMenu fileMenu = new JMenu("About...");
		fileMenu.setMnemonic('A');
		JMenuItem credit = new JMenuItem("Credit");
		credit.setMnemonic('C');
		credit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane
						.showMessageDialog(
								DcMotor.this,
								"Original idea and graphics are from the website\n"
										+ "http://micro.magnet.fsu.edu/electromag/java/generator/dc.html",
								"Credit", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		fileMenu.add(credit);

		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic('x');
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		fileMenu.add(exit);

		JMenuBar bar = new JMenuBar();
		bar.add(fileMenu);
		menu.add(bar, BorderLayout.WEST);
		menu.setBorder(BorderFactory.createEtchedBorder());

		topNum = 0;
		bottomNum = 0;
		animationDelay = 500 - 10 * WINIT;
		topIcon = new ImageIcon[IMG];
		bottomIcon = new ImageIcon[BTM];
		for (int i = 0; i < topIcon.length; i++) {
			topIcon[i] = createImageIcon("images/dcgen" + i + ".jpg");
		}
		for (int i = 0; i < bottomIcon.length; i++) {
			bottomIcon[i] = createImageIcon("images/bottom" + i + ".jpg");
		}

		top = new JLabel(topIcon[0]);
		bottom = new CustomLabel(bottomIcon[0]);
		JPanel display = new JPanel(new BorderLayout());
		display.setBackground(Color.white);
		display.add(top, BorderLayout.CENTER);
		display.add(bottom, BorderLayout.SOUTH);
		display.setPreferredSize(new Dimension(150, 183));

		changePoles = new JButton("Change direction");
		changePoles.addActionListener(this);
		pause = new JButton("Pause animation");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (animationTimer.isRunning()) {
					animationTimer.stop();
					pause.setText("Resume animation");
				} else {
					animationTimer.start();
					pause.setText("Pause animation");
				}
			}
		});
		JLabel freqLabel = new JLabel("Animation speed", JLabel.CENTER);
		wSlider = new JSlider(JSlider.HORIZONTAL, WMIN, WMAX, WINIT);
		wSlider.addChangeListener(this);
		wSlider.setBackground(Color.white);

		JPanel control = new JPanel(new BorderLayout(0, 10));
		control.setBackground(Color.white);
		JPanel buttonControl = new JPanel(new BorderLayout());
		JPanel sliderControl = new JPanel(new BorderLayout());
		buttonControl.add(changePoles, BorderLayout.SOUTH);
		buttonControl.add(pause, BorderLayout.NORTH);
		sliderControl.add(freqLabel, BorderLayout.NORTH);
		sliderControl.add(wSlider, BorderLayout.CENTER);
		sliderControl.setBackground(Color.white);
		control.add(buttonControl, BorderLayout.NORTH);
		control.add(sliderControl, BorderLayout.CENTER);

		JPanel controlDisplay = new JPanel(new BorderLayout());
		controlDisplay.add(control, BorderLayout.CENTER);
		controlDisplay.add(Box.createVerticalStrut(40), BorderLayout.NORTH);
		controlDisplay.add(Box.createVerticalStrut(40), BorderLayout.SOUTH);
		controlDisplay.setBackground(Color.white);

		Container container = getContentPane();
		container.setLayout(new BorderLayout(20, 0));
		container.setBackground(Color.white);
		container.add(menu, BorderLayout.NORTH);
		container.add(display, BorderLayout.CENTER);
		container.add(controlDisplay, BorderLayout.EAST);
		animationTimer = new Timer(animationDelay, this);
		animationTimer.start();
	}

	public void stateChanged(ChangeEvent e) {
		Object temp = e.getSource();
		if (temp == wSlider) {
			animationTimer.setDelay(500 - 10 * wSlider.getValue());
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object temp = e.getSource();
		if (temp == animationTimer) {
			if (bottomNum == 0) {
				topNum = (15 + topNum - 1) % IMG;
				bottom.setNum(topNum);
			} else {
				topNum = (topNum + 1) % IMG;
				bottom.setNum(topNum);
			}
			top.setIcon(topIcon[topNum]);
		} else if (temp == changePoles) {
			bottomNum = (bottomNum + 1) % BTM;
			bottom.setIcon(bottomIcon[bottomNum]);
			bottom.setState(bottomNum);
		}

	}

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
