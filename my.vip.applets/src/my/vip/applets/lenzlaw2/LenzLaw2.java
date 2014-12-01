package my.vip.applets.lenzlaw2;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import my.vip.applets.lenzlaw.Arrow;
import my.vip.applets.lenzlaw.Electron;
import my.vip.applets.lenzlaw.Ring;

/**
 * This is the older version of my.vip.applets.lenzlaw.LenzLaw
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
@SuppressWarnings("serial")
public class LenzLaw2 extends JApplet implements MouseMotionListener, ChangeListener {
	MyPanel panel;
	JPanel status, menu;
	JLabel flux, change, radius;
	JTextField fluxField, changeField;
	JSlider slider;
	final int R_MIN = 2, R_MAX = 8;

	public void init() {
		menu = new JPanel();
		menu.setLayout( new BorderLayout() );

		JMenu fileMenu = new JMenu( "File" );
		fileMenu.setMnemonic( 'F' );
		JMenuItem about = new JMenuItem( "About..." );
		about.setMnemonic( 'A' );
		about.addActionListener(
				new ActionListener() {
					public void actionPerformed( ActionEvent event ) {
						JOptionPane.showMessageDialog( LenzLaw2.this, "Written by Dong Si Tue Cuong\n" + 
								"A project under Vacation Internship\n" +
								"Summer May 2004", "About", JOptionPane.INFORMATION_MESSAGE );
					}
				}
				);
		fileMenu.add( about );

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

		JMenuBar bar = new JMenuBar();
		bar.add( fileMenu );
		menu.add( bar, BorderLayout.WEST );
		menu.setBorder(BorderFactory.createEtchedBorder());

		panel = new MyPanel();
		panel.setBackground( Color.white );
		panel.addMouseMotionListener( this );

		status = new JPanel();
		status.setLayout( new GridLayout( 3, 2 ) );

		flux = new JLabel( "Magnetic flux lines through the ring  ", JLabel.RIGHT);
		flux.setAlignmentX( Component.RIGHT_ALIGNMENT );
		fluxField = new JTextField( " ", 2 );
		fluxField.setEditable( false );
		status.add( flux );
		status.add( fluxField );

		change = new JLabel( "Last change of magnetic flux  ", JLabel.RIGHT);
		change.setAlignmentX( Component.RIGHT_ALIGNMENT );
		changeField = new JTextField( " ", 2 );
		changeField.setEditable( false );
		changeField.setFont( new Font("MonoSpaced", Font.PLAIN, 14) );
		status.add( change );
		status.add( changeField );

		radius = new JLabel( "Change the size of the ring  ", JLabel.RIGHT);
		radius.setAlignmentX( Component.RIGHT_ALIGNMENT );
		slider = new JSlider( JSlider.HORIZONTAL, R_MIN, R_MAX, Ring.RING_WIDTH / 50 );
		slider.addChangeListener(this);
		slider.setSnapToTicks( true );
		status.add( radius );
		status.add( slider );

		Container container = getContentPane();
		container.setBackground(Color.white);
		container.add( panel, BorderLayout.CENTER );
		container.add( status, BorderLayout.SOUTH );
		container.add( menu, BorderLayout.NORTH );
	}

	public static void main( String args[] ) {
		JFrame window = new JFrame( "Lenz's Law Demonstration" );
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		LenzLaw2 applet = new LenzLaw2();
		applet.init();
		applet.start();
		window.getContentPane().add(applet);
		window.setResizable(false);
		window.setSize( Magnet.WIDTH, Magnet.HEIGHT);
		window.setVisible(true);
	}

	public void mouseMoved( MouseEvent e ) {
	}

	public void mouseDragged( MouseEvent e ) {
		fluxField.setText( " " + panel.getFlux() );
		changeField.setText( (panel.isIncrease()?"Increase":"Decrease")+" by "+ panel.fluxDiff() );
	}

	public void stateChanged( ChangeEvent e ) {
		int w = 50 * slider.getValue();
		panel.top.setWidth( w );
		panel.bottom.setWidth( w );

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



