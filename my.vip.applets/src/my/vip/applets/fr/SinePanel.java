package my.vip.applets.fr;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A simple panel with a colored sine wave drawn on it.
 * 
 * @author tdongsi
 *
 */
class SinePanel extends JPanel {
	public static final int SCALE = 250;
	public static final int WIDTH = 200;
	public static final int HEIGHT = 120;
	
	private int currentPhase;
	
	/**
	 * Internal states for frequency, magnitude, and phase of the sine wave
	 */
	private double freq, mag, phi;
	
	/**
	 * Y coordinates of the sine wave drawn on the panel 
	 */
	private int[] yValues = new int[SCALE];
	
	/**
	 * Color of the sine wave 
	 */
	private Color color;

	/**
	 * Construct with default values
	 */
	public SinePanel() {
		freq = 5.0 / SCALE;
		phi = 0.0;
		currentPhase = 0;
		mag = 50.0; // initial magnitude
		setyValues();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		color = Color.red;
	}

	/**
	 * Specified the color of the sine wave
	 * 
	 * @param c
	 */
	public SinePanel(Color c) {
		this();
		color = c;
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 * 
	 * Override to add a sine wave
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawSine(g, currentPhase);
		currentPhase = (currentPhase - 2) % SCALE;
	}

	/**
	 * Change the magnitude of the sine wave.
	 * 
	 * @param magnitude
	 */
	public void setMagnitude(double magnitude) {
		this.mag = magnitude;
		setyValues();
		repaint();
	}

	/**
	 * Draw the sine wave, given the Y-coordinates in yValues.
	 */
	private void drawSine(Graphics gr, int currentPhase) {
		gr.setColor(color);
		for (int i = 0; i <= WIDTH; i++) {
			gr.drawLine(i, yValues[(i + currentPhase + SCALE) % SCALE], i + 1,
					yValues[(i + currentPhase + SCALE+1) % SCALE]);
		}

	}


	/**
	 * Change the frequency, i.e. wavelength, of the sine wave. 
	 * 
	 * @param freq
	 */
	public void setFreq(double freq) {
		this.freq = freq / SCALE;
		setyValues();
		repaint();
	}

	/**
	 * Change the phase of the sine wave.
	 * 
	 * @param phi
	 */
	public void setPhi(double phi) {
		this.phi = phi;
		setyValues();
		repaint();
	}

	/**
	 * Compute the y values of the sine wave as x going from 0 to length.
	 */
	private void setyValues() {
		// Wave length
		double w = 2 * Math.PI * freq;
		
		int h = this.getHeight() / 2;
		if (h == 0)
			h = HEIGHT/2;
		for (int i = 0; i < yValues.length; i++) {
			double sin = Math.sin(i * w + phi);
			yValues[i] = (int)(mag * sin + h);
		}
	}
	
	/**
	 * Getter for magnitude
	 */
	public double getMagnitude() {
		return mag;
	}

	/**
	 * Getter for phase
	 */
	public double getPhi() {
		return phi;
	}

}