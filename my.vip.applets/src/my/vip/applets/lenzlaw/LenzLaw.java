package my.vip.applets.lenzlaw;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A Java project for the course Basic Electrical Enginering, under an Internship Program.
 * An applet/application to illustrate the Lenz's Law. Originally programmed in Java 1.4.
 *  
 * @author tdongsi
 *
 */
public class LenzLaw extends JApplet implements MouseMotionListener, ChangeListener {
	/**
	 * The main panel with the magnet, the ring and magnetic field being drawn
	 */
	private MyPanel panel;
	/**
	 * Text fields for status of magnetic flux
	 */
	private JTextField fluxField, changeField;
	/**
	 * Slider to change the size of the ring
	 */
	private JSlider slider;
	/**
	 * Min, max value for the slider
	 */
	private final int R_MIN = 2, R_MAX = 8;
	
	// sub-panels in the application
	private JPanel menu;
	private JPanel status;

	public void init() {

		// Prepare a menu bar
		prepareMenuBar();

		// Add the main canvas
		panel = new MyPanel();
		panel.setBackground( Color.white );
		panel.addMouseMotionListener( this );

		// Prepare a control panel
		prepareControlPanel();

		Container container = getContentPane();
		container.setBackground(Color.white);
		container.add( panel, BorderLayout.CENTER );
		container.add( status, BorderLayout.SOUTH );
		container.add( menu, BorderLayout.NORTH );
	}

	/**
	 * Add a control panel with sliders and status readings
	 */
	private void prepareControlPanel() {
		
		status = new JPanel( new GridLayout( 3, 2 ) );
		
		JLabel flux = new JLabel( "Magnetic flux lines through the ring  ", JLabel.RIGHT);
		flux.setAlignmentX( Component.RIGHT_ALIGNMENT );
		fluxField = new JTextField( " ", 2 );
		fluxField.setEditable( false );
		status.add( flux );
		status.add( fluxField );

		JLabel change = new JLabel( "Last change of magnetic flux  ", JLabel.RIGHT);
		change.setAlignmentX( Component.RIGHT_ALIGNMENT );
		changeField = new JTextField( " ", 2 );
		changeField.setEditable( false );
		changeField.setFont( new Font("MonoSpaced", Font.PLAIN, 14) );
		status.add( change );
		status.add( changeField );

		// Add slider to change the size of the ring
		JLabel radius = new JLabel( "Change the size of the ring  ", JLabel.RIGHT);
		radius.setAlignmentX( Component.RIGHT_ALIGNMENT );
		slider = new JSlider( JSlider.HORIZONTAL, R_MIN, R_MAX, Ring.RING_WIDTH / 50 );
		slider.addChangeListener(this);
		slider.setSnapToTicks( true );
		status.add( radius );
		status.add( slider );
	}

	/**
	 * Add a menu bar with File menu with About and Exit items.
	 */
	private void prepareMenuBar() {
		// Add File menu droplist with About and Exit button.
		menu = new JPanel(new BorderLayout());

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		
		JMenuItem about = new JMenuItem("About...");
		about.setMnemonic('A');
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(LenzLaw.this,
						"Written by Tue-Cuong Dong-Si\n", "About",
								JOptionPane.INFORMATION_MESSAGE);
			}
		});
		fileMenu.add(about);

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
	}

	public void mouseMoved( MouseEvent e ) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 * 
	 * When dragging the magnet
	 * 
	 */
	public void mouseDragged( MouseEvent e ) {
		fluxField.setText( " " + panel.getFlux() );
		changeField.setText( (panel.isIncrease()?"Increase":"Decrease") + " by "+ panel.fluxDiff() );
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 * 
	 * When changing the slider
	 */
	public void stateChanged( ChangeEvent e ) {
		int w = 50 * slider.getValue();
		
		panel.top.setWidth( w );
		panel.bottom.setWidth( w );
		panel.updateLimits();

		for ( int i = 0; i < panel.current.length; i++ ) {
			panel.current[i].ringChanged( w );
		}

		panel.arrow[0].ringChanged( w );
		panel.arrow[1].ringChanged( w );	
		panel.arrow[2].ringChanged( -w );
		panel.arrow[3].ringChanged( -w );

		panel.repaint();
		fluxField.setText( " " + panel.getFlux() );
	}

}

