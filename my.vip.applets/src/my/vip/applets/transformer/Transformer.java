package my.vip.applets.transformer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * A TA project for the course Basic Electrical Enginering, under Vacation Internship Program (VIP).
 * An applet to illustrate how a transformer works. Programmed in Java 1.4. 
 * 
 * Applet width = 415 height = 370.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
@SuppressWarnings("serial")
public class Transformer extends JApplet implements ChangeListener {
	// minimum and maximum voltage
	private final int V_MIN = 0, V_MAX = 120;
	// minimum, maximum windings
	private final int W_MIN = 3, W_MAX = 9;
	// number of images
	private final int NUM_IMG = 7;
	
	// The current number of primary windings, secondary windings, and current input voltage
	private int currPrimWinding = W_MIN;
	private int currSecWinding = W_MIN;
	private int currInVolt = 0;
	// The current output voltage
	private double currOutVolt = 0.0;
	
	// voltPanel: the container of the input voltage slider
	// windingPanel: the container of winding sliders
	private JPanel voltPanel, windingPanel;
	// The slider for input voltage, primary winding, and secondary winding
	private JSlider voltSlider, primSlider, secSlider;
	// The labels for the panels
	private JLabel voltageLabel, primaryWindingLabel, secondaryWindingLabel, inputVoltLabel, outputVoltLabel;

	// the panel and data structures for showing the transformer picture
	private JPanel picture;
	private JLabel top, bottom, left, right;
	private ImageIcon topIcon, bottomIcon;
	private ImageIcon[] leftIcon, rightIcon;

	// the panels for drawing input and output AC currents
	private JPanel inputPanel, outputPanel;
	private SinePanel input, output;

	/**
	 * The main canvas panel
	 */
	private JPanel container;
	
	/**
	 * An area reserved for adding drop-down menus and their menu bar. 
	 */
	JPanel menu;

	public void init() {
		// Add a menu bar
		prepareMenuBar();

		// Prepare the top slider for controlling input voltage
		prepareInputVoltageSlider();

		// Prepare the left panel for animated primary AC
		preparePrimaryCurrent();

		// Prepare the right panel for animated secondary AC 
		prepareSecondaryCurrent();

		// Prepare the center panel for the transformer picture
		prepareTransformerPic();

		// Prepare the bottom panel with sliders for adjusting windings
		prepareWindingControl();
		
		// Add the prepared panels into the main canvas panel
		prepareMainCanvas();

		Container c = getContentPane();
		c.add(menu, BorderLayout.NORTH);
		c.add(container, BorderLayout.CENTER);
	}

	/**
	 * Add the prepared panels into the main canvas in the correct layout
	 */
	private void prepareMainCanvas() {
		container = new JPanel();
		container.setLayout( new BorderLayout() );
		container.setBackground(Color.white);
		container.add(voltPanel, BorderLayout.NORTH);		
		container.add(windingPanel, BorderLayout.SOUTH);
		container.add(inputPanel, BorderLayout.WEST);
		container.add(outputPanel, BorderLayout.EAST);
		container.add(picture, BorderLayout.CENTER);
	}

	/**
	 * Prepare the bottom panel with sliders for adjusting windings
	 */
	private void prepareWindingControl() {
		windingPanel = new JPanel();
		windingPanel.setLayout(new GridLayout(2, 2));
		windingPanel.setBackground( Color.white );
		
		primaryWindingLabel = new JLabel("Primary winding", JLabel.CENTER);
		primaryWindingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		primSlider = new JSlider(JSlider.HORIZONTAL, W_MIN, W_MAX, W_MIN);
		primSlider.addChangeListener(this);
		primSlider.setMajorTickSpacing(1);
		primSlider.setPaintTicks(true);
		primSlider.setPaintLabels(true);
		primSlider.setBorder(BorderFactory.createEtchedBorder());
		primSlider.setSnapToTicks(true);

		secondaryWindingLabel = new JLabel("Secondary winding", JLabel.CENTER);
		secondaryWindingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		secSlider = new JSlider(JSlider.HORIZONTAL, W_MIN, W_MAX, W_MIN);
		secSlider.addChangeListener(this);
		secSlider.setMajorTickSpacing(1);
		secSlider.setPaintTicks(true);
		secSlider.setPaintLabels(true);
		secSlider.setBorder(BorderFactory.createEtchedBorder());
		secSlider.setSnapToTicks(true);
		
		windingPanel.add(primaryWindingLabel);
		windingPanel.add(secondaryWindingLabel);
		windingPanel.add(primSlider);
		windingPanel.add(secSlider);
	}

	/**
	 * Prepare the center panel for the transformer picture
	 */
	private void prepareTransformerPic() {
		picture = new JPanel(new BorderLayout());
		picture.setBackground(Color.white);
		
		topIcon = createImageIcon("images/top.jpg");
		if (topIcon != null) {
			top = new JLabel(topIcon);
			top.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		} else {
			top = new JLabel("Images not found");
			top.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		}
		picture.add(top, BorderLayout.NORTH);

		leftIcon = new ImageIcon[NUM_IMG];
		rightIcon = new ImageIcon[NUM_IMG];

		for (int i = 0; i < NUM_IMG; i++) {
			leftIcon[i] = createImageIcon("images/left" + (i + W_MIN) + ".jpg");
			rightIcon[i] = createImageIcon("images/right" + (i + W_MIN) + ".jpg");
		}

		if (leftIcon[0] != null) {
			left = new JLabel(leftIcon[0]);
		} else {
			left = new JLabel("Images not found");
		}
		picture.add(left, BorderLayout.WEST);

		if (rightIcon[0] != null) {
			right = new JLabel(rightIcon[0]);
		} else {
			right = new JLabel("Images not found");
		}
		picture.add(right, BorderLayout.EAST);

		bottomIcon = createImageIcon("images/bottom.jpg");
		if (bottomIcon != null) {
			bottom = new JLabel(bottomIcon);
		} else {
			bottom = new JLabel("Images not found");
		}
		picture.add(bottom, BorderLayout.SOUTH);
	}

	/**
	 *  Prepare the right panel for animated secondary AC
	 */
	private void prepareSecondaryCurrent() {
		outputPanel = new JPanel();
		outputPanel.setLayout( new BorderLayout() );
		outputPanel.setBackground( Color.white );
		outputVoltLabel = new JLabel("Output: 000.00 V AC", JLabel.CENTER);
		outputVoltLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		output = new SinePanel();
		output.setBackground(Color.white);
		output.draw( currOutVolt * 0.25 );
		outputPanel.add( outputVoltLabel, BorderLayout.SOUTH );
		outputPanel.add( output, BorderLayout.CENTER );
	}

	/**
	 *  Prepare the left panel for animated primary AC
	 */
	private void preparePrimaryCurrent() {
		inputPanel = new JPanel();
		inputPanel.setLayout(new BorderLayout() );
		inputPanel.setBackground( Color.white );
		inputVoltLabel = new JLabel("Input: 000.00 V AC", JLabel.CENTER);
		inputVoltLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		input = new SinePanel();
		input.setBackground( Color.white );
		input.draw( (double)currInVolt * 0.25 );
		inputPanel.add( inputVoltLabel, BorderLayout.SOUTH );
		inputPanel.add( input, BorderLayout.CENTER );
	}

	/**
	 * Add a slider for controlling input voltage, with a label
	 */
	private void prepareInputVoltageSlider() {
		voltPanel = new JPanel();
		voltPanel.setLayout(new GridLayout(2, 1));
		voltPanel.setBackground( Color.white );
		voltageLabel = new JLabel("Input Voltage", JLabel.CENTER);
		voltageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		voltSlider = new JSlider(JSlider.HORIZONTAL, V_MIN, V_MAX, V_MIN);
		voltSlider.addChangeListener(this);
		voltSlider.setMajorTickSpacing(10);
		voltSlider.setMinorTickSpacing(5);
		voltSlider.setPaintTicks(true);
		voltSlider.setPaintLabels(true);
		voltSlider.setBackground( Color.white );
		voltPanel.add(voltageLabel);
		voltPanel.add(voltSlider);
	}

	/**
	 * Add a menu bar with File menu with About and Exit items.
	 */
	private void prepareMenuBar() {
		menu = new JPanel();
		menu.setLayout( new BorderLayout() );
		
		// Add File menu
		JMenu fileMenu = new JMenu( "File" );
		fileMenu.setMnemonic( 'F' );

		// Add menu item About
		JMenuItem about = new JMenuItem( "About..." );
		about.setMnemonic( 'A' );
		about.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent event ) {
						JOptionPane.showMessageDialog( Transformer.this, "Written by Tue-Cuong Dong-Si\n" + 
								"Summer May 2004", "About", JOptionPane.INFORMATION_MESSAGE );
					}
				}
				);
		fileMenu.add( about );
		
		// Add menu item Exit
		JMenuItem exit = new JMenuItem( "Exit" );
		exit.setMnemonic( 'x' );
		exit.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent event ) {
						System.exit( 0 );
					}
				}
				);
		fileMenu.add( exit );

		// Add the menu bar
		JMenuBar bar = new JMenuBar();
		bar.add( fileMenu );
		menu.add( bar, BorderLayout.WEST );
		menu.setBorder(BorderFactory.createEtchedBorder());
	}

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == voltSlider)
			currInVolt = voltSlider.getValue();
		else if (e.getSource() == primSlider) {
			currPrimWinding = primSlider.getValue();
			updatePic(true);
		} else if (e.getSource() == secSlider) {
			currSecWinding = secSlider.getValue();
			updatePic(false);
		}
		updateVolt();
	}

	/**
	 * Update the voltage display.
	 */
	private void updateVolt() {
		DecimalFormat twoDigits = new DecimalFormat("000.00");
		inputVoltLabel.setText("Input: " + twoDigits.format(currInVolt) + " V AC");
		currOutVolt = (double)currInVolt * currSecWinding/currPrimWinding;
		outputVoltLabel.setText("Output: " + twoDigits.format(currOutVolt) + " V AC");
		input.draw( (double)currInVolt * 0.25 );
		output.draw( currOutVolt * 0.25 );
	}

	/**
	 * Update the picture of the primary or secondary winding when it is changed
	 * 
	 * @param isLeft true if the primary winding slider changes, otherwise false.
	 */
	private void updatePic(boolean isLeft) {
		if (isLeft) {
			int i = currPrimWinding - W_MIN;
			if (leftIcon[i] != null) {
				left.setIcon(leftIcon[i]);
			} else {
				left.setText("Image not found");
			}
		} else {
			int i = currSecWinding - W_MIN;
			if (rightIcon[i] != null) {
				right.setIcon(rightIcon[i]);
			} else {
				right.setText("Image not found");
			}
		}
	}

	/**
	 * Retrieve the image from the given path
	 */
	private ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Transformer.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

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
