package my.vip.applets.filters;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class FourierPanel extends JPanel {
	private double freq;
	private int currentPhase;
	protected double[] mag;
	protected double[] phi;
	private double[] yValue = new double[250];

	public FourierPanel() {
		mag = new double[5];
		mag[0] = 20.0;
		mag[1] = mag[2] = mag[3] = mag[4] = 0.0;
		phi = new double[5];
		for (int i = 0; i < phi.length; i++) {
			phi[i] = 0.0;
		}

		currentPhase = 0;
		freq = 5.0 / 250.0;
		setyValue();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawSin(g, currentPhase);
		currentPhase = (currentPhase - 2) % 250;
	}

	public void setFreq(double freq1) {
		freq = freq1 / 250;
		setyValue();
		repaint();
	}

	public void setMagnitude(int index, double max1) {
		mag[index] = max1;
		setyValue();
		repaint();
	}

	public void setPhi(int index, double phi1) {
		phi[index] = phi1;
		setyValue();
		repaint();
	}

	private void drawSin(Graphics gr, int currentPhase) {
		gr.setColor(Color.blue);
		for (int i = 0; i <= 230; i++) {
			gr.drawLine(i, (int) yValue[(i + currentPhase + 250) % 250], i + 1,
					(int) yValue[(i + currentPhase + 251) % 250]);
		}

	}

	private void setyValue() {
		double w = 2 * Math.PI * freq;
		int h = this.getHeight() / 2;
		// System.out.println( "h: " + h );
		if (h == 0)
			h = 80;
		for (int i = 0; i < yValue.length; i++) {
			yValue[i] = h;
			for (int j = 0; j < mag.length; j++) {
				double sin = Math.sin((j + 1) * w * i + phi[j]);
				yValue[i] += mag[j] * sin;
			}
			if (yValue[i] <= 0) {
				yValue[i] = 1;
			} else if (yValue[i] >= 160) {
				yValue[i] = 159;
			}
		}
	}

}