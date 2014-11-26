package my.vip.applets.filters;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A panel that draws an animated periodic waveform (e.g., square, sawtooth),
 * using additive synthesis of a periodic wave with a specified number of harmonics
 * based on Fourier transform.
 * 
 * @author tdongsi
 *
 */
class FourierPanel extends JPanel {
	private int currentPhase;
	/**
	 * Frequency scale factor.
	 */
	private int SCALE = SinePanel.WIDTH;
	
	/**
	 * Number of harmonics for additive synthesis of a periodic wave.
	 */
	public static final int HARMONIC_NUMBER = 5;
	
	/**
	 * Current base frequency
	 */
	private double frequency;
	
	/**
	 * Current magnitudes of all harmonics 
	 * 
	 * TODO: Make them private?
	 */
	double[] magnitude;
	
	/**
	 * Current phases of all harmonics
	 * 
	 * TODO: Make them private?
	 */
	double[] phase;
	
	private int[] yValues = new int[SCALE];

	/**
	 * Initialize to show an animated sine wave
	 */
	public FourierPanel() {
		magnitude = new double[HARMONIC_NUMBER];
		magnitude[0] = 20.0; // only one component
		phase = new double[HARMONIC_NUMBER];

		currentPhase = 0;
		frequency = 5.0 / SCALE;
		setyValues();
		
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawWave(g, currentPhase);
		currentPhase = (currentPhase - 2) % SCALE;
	}

	/**
	 * Change the base frequency
	 * 
	 * @param freq
	 */
	public void setFreq(double freq) {
		frequency = freq / SCALE;
		setyValues();
		repaint();
	}

	/**
	 * Change the magnitude value of the specified harmonic.
	 */
	public void setMagnitude(int index, double magnitude) {
		this.magnitude[index] = magnitude;
		setyValues();
		repaint();
	}

	/**
	 * Change the phase value of the specified harmonic.
	 */
	public void setPhi(int index, double phi) {
		this.phase[index] = phi;
		setyValues();
		repaint();
	}

	/**
	 * Draw the periodic wave, given the Y-coordinates in yValues.
	 */
	private void drawWave(Graphics gr, int currentPhase) {
		gr.setColor(Color.blue);
		for (int i = 0; i <= 230; i++) {
			gr.drawLine(i, (int) yValues[(i + currentPhase + SCALE) % SCALE], i + 1,
					(int) yValues[(i + currentPhase + SCALE+1) % SCALE]);
		}

	}

	/**
	 * Compute the y values of the periodic wave as x going from 0 to length.
	 */
	private void setyValues() {
		int HEIGHT = 160; // default height
		double w = 2 * Math.PI * frequency;
		
		int h = this.getHeight() / 2;
		// System.out.println( "h: " + h );
		if (h == 0)
			h = HEIGHT/2;
		
		for (int i = 0; i < yValues.length; i++) {
			double temp = h;
			// Add all harmonics
			for (int j = 0; j < magnitude.length; j++) {
				double sin = Math.sin((j + 1) * w * i + phase[j]);
				temp += magnitude[j] * sin;
			}
			
			if (temp <= 0) {
				yValues[i] = 1;
			} else if (temp >= HEIGHT) {
				yValues[i] = HEIGHT-1;
			}
			
			yValues[i] = (int) temp;
		}
	}

}