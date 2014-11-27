package my.vip.applets.fr;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class FrequencyPanel extends JPanel {
	private int frequency, index;
	private double[] yValue = new double[400];
	private double[] idealValue = new double[400];
	private double r, l, c;
	private final double sqrt2 = 305 - 212.132;

	public FrequencyPanel() {
		frequency = 500;
		index = 0;
		r = 50.0;
		c = 20.0;
		l = 200.0;
		setyValue();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		drawFreq(g);
	}

	public void setFreq(double freq) {
		frequency = (int) freq;
		repaint();
	}

	public void setIndex(int i) {
		index = i;
		setyValue();
		repaint();
	}

	public void setR(double res) {
		r = res;
		setyValue();
		repaint();
	}

	public void setC(double cap) {
		c = cap;
		setyValue();
		repaint();
	}

	public void setL(double ind) {
		l = ind;
		setyValue();
		repaint();
	}

	private void drawFreq(Graphics gr) {
		gr.setColor(Color.magenta);
		for (int i = 0; i < yValue.length - 1; i++) {
			gr.drawLine(i + 5, (int) idealValue[i], i + 6,
					(int) idealValue[i + 1]);
		}
		gr.drawLine(20, 315, 40, 315);
		gr.drawString("Ideal response", 45, 320);
		gr.setColor(Color.blue);
		for (int i = 0; i < yValue.length - 1; i++) {
			gr.drawLine(i + 5, (int) yValue[i], i + 6, (int) yValue[i + 1]);
		}
		gr.drawLine(150, 315, 170, 315);
		gr.drawString("Actual response", 180, 320);
		gr.setColor(Color.red);
		int temp = (frequency - 100) / 10;
		if (temp == 0)
			temp = 1;
		gr.fillOval(temp, (int) yValue[temp - 1] - 5, 10, 10);
		gr.drawLine(temp + 5, 5, temp + 5, 305);
		gr.setColor(Color.black);
		gr.drawLine(4, 306, 409, 306);
		gr.drawLine(4, 306, 4, 4);
		gr.drawString("H(w)", 9, 19);
		gr.drawString("w", 400, 315);
	}

	private void setyValue() {
		for (int i = 0; i < yValue.length; i++) {
			yValue[i] = 305 - 300 * magResponse(index, i * 10 + 100);
			if (yValue[i] > sqrt2) {
				idealValue[i] = 305;
			} else {
				idealValue[i] = 5;
			}
		}
	}

	private double magResponse(int i, int freq) {
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
