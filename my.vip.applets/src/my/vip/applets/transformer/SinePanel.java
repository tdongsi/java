package my.vip.applets.transformer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A panel for drawing a sine wave to represent an AC.
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
public class SinePanel extends JPanel implements ActionListener {
	private final int ANIMATION_DELAY = 10;
	
	private double phase = 0, freq = 0.1, max;
	private int currentPhase = 0;
	protected Timer animationTimer;

	public SinePanel() {
		animationTimer = new Timer( ANIMATION_DELAY, this );
		animationTimer.start();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Draw the sine wave and increase the phase, making it animated
	 */
	public void paintComponent ( Graphics g ) {
		super.paintComponent(g);

		drawSin(g, phase, freq, max);
		currentPhase = (currentPhase - 10) % 360;
		phase = ( (double) currentPhase / 180 ) * Math.PI;
	}

	/**
	 * Redraw the sine wave with the new magnitude.
	 * 
	 * @param max new magnitude
	 */
	public void draw( double max ) {
		this.max = max;
		repaint();
	}

	/**
	 * Draw a sine wave with the given Graphics object.
	 * 
	 * @param gr
	 * @param phase the starting phase of the sine wave
	 * @param freq the frequency of the wave
	 * @param max the magnitude of the wave
	 */
	private void drawSin( Graphics gr, double phase, double freq, double max) {
		gr.setColor( Color.red );
		int i = 0;
		double val0 = 0.0;

		do {
			double sin = Math.sin( (double)i * freq + phase );
			double val1 = max * sin;
			if ( i != 0) {
				gr.drawLine( i - 1, 90 + (int)val0, i, 90 + (int)val1);
			}
			val0 = val1;
		} while (++i < 100);

	}

	public void actionPerformed( ActionEvent event ) {
		repaint();
	}

	public void stopAnimation() {
		animationTimer.stop();
	}

}
