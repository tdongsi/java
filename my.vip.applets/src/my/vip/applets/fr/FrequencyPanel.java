package my.vip.applets.fr;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A customized panel to draw actual and ideal frequency responses on it.
 * 
 * @author tdongsi
 */
class FrequencyPanel extends JPanel {
	public static final int SCALE = 400;
	
	/**
	 * Frequency of the input wave
	 */
	private int frequency; 
	
	/**
	 * Index of the filter circuit
	 */
	private int index;
	
	/**
	 * Plot points for actual frequency response
	 */
	private double[] actualResponse = new double[SCALE];
	
	/**
	 * Plot points for ideal frequency response
	 */
	private double[] idealResponse = new double[SCALE];
	
	/**
	 * Internal variables for the circuit's resistance, inductance, and capacitance. 
	 */
	private double r, l, c;
	
	/**
	 * Cut off point
	 */
	private final double sqrt2 = 305 - 212.132;

	/**
	 * Initialize with default parameters
	 */
	public FrequencyPanel() {
		frequency = 500;
		index = 0;
		r = 50.0;
		c = 20.0;
		l = 200.0;
		computeFrequencyResponse();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawFrequencyResponse(g);
	}

	public void setFrequency(double frequency) {
		this.frequency = (int) frequency;
		repaint();
	}

	public void setIndex(int index) {
		this.index = index;
		computeFrequencyResponse();
		repaint();
	}

	public void setResistance(double resistance) {
		r = resistance;
		computeFrequencyResponse();
		repaint();
	}

	public void setCapacitance(double capacitance) {
		c = capacitance;
		computeFrequencyResponse();
		repaint();
	}

	public void setInductance(double inductance) {
		l = inductance;
		computeFrequencyResponse();
		repaint();
	}

	/**
	 * Draw the frequency responses on the panel
	 */
	private void drawFrequencyResponse(Graphics gr) {
		// Draw ideal frequency response
		gr.setColor(Color.magenta);
		for (int i = 0; i < actualResponse.length - 1; i++) {
			gr.drawLine(i + 5, (int) idealResponse[i], i + 6,
					(int) idealResponse[i + 1]);
		}
		gr.drawLine(20, 315, 40, 315);
		gr.drawString("Ideal response", 45, 320);
		gr.setColor(Color.blue);
		for (int i = 0; i < actualResponse.length - 1; i++) {
			gr.drawLine(i + 5, (int) actualResponse[i], i + 6, (int) actualResponse[i + 1]);
		}
		gr.drawLine(150, 315, 170, 315);
		
		// Draw actual frequency response
		gr.drawString("Actual response", 180, 320);
		gr.setColor(Color.red);
		int temp = (frequency - 100) / 10;
		if (temp == 0)
			temp = 1;
		gr.fillOval(temp, (int) actualResponse[temp - 1] - 5, 10, 10);
		gr.drawLine(temp + 5, 5, temp + 5, 305);
		
		// Draw labels
		gr.setColor(Color.black);
		gr.drawLine(4, 306, 409, 306);
		gr.drawLine(4, 306, 4, 4);
		gr.drawString("H(w)", 9, 19);
		gr.drawString("w", 400, 315);
	}

	/**
	 * Compute the y values of the actual and ideal frequency response as x going from 0 to length.
	 */
	private void computeFrequencyResponse() {
		for (int i = 0; i < actualResponse.length; i++) {
			actualResponse[i] = 305 - 300 * computeMagnitudeResponse(index, i * 10 + 100);
			if (actualResponse[i] > sqrt2) {
				idealResponse[i] = 305;
			} else {
				idealResponse[i] = 5;
			}
		}
	}

	/**
	 * Pure physics: compute magnitude response, based on frequency and filter configuration 
	 * 
	 * @param i: id of the filter configuration (e.g., 1 for low pass)
	 * @param freq: frequency value
	 * @return magnitude factor
	 */
	private double computeMagnitudeResponse(int i, int freq) {
		if (i == 1) {
			double w = Math.PI * 0.2 * freq;
			return 1.0 / Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 2) {
			double w = Math.PI * 0.2 * freq;
			return w * c * r * 1e-6
					/ Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 3) {
			double w = Math.PI * 0.2 * freq;
			return Math.abs((w * r * c * 1e-6))
					/ Math.sqrt(Math.pow(1 - l * c * 1e-9 * w * w, 2.0)
							+ Math.pow(c * 1e-6 * w * r, 2.0));
		} else if (i == 4) {
			double w = Math.PI * 0.2 * freq;
			return Math.abs((1 - l * c * 1e-9 * w * w))
					/ Math.sqrt(Math.pow(1 - l * c * 1e-9 * w * w, 2.0)
							+ Math.pow(w * r * c * 1e-6, 2.00));
		} else {
			return 1.00;
		}
	}
}
