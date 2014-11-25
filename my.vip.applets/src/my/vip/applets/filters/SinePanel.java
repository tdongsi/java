package my.vip.applets.filters;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

class SinePanel extends JPanel {
	private double freq, max, phi;
	private int currentPhase;
	private double[] yValue = new double[250];
	private Color color;

	public SinePanel() {
		freq = 5.0 / 250;
		phi = 0.0;
		currentPhase = 0;
		setyValue();
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		color = Color.red;
	}

	public SinePanel(Color c) {
		this();
		color = c;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawSin(g, currentPhase);
		currentPhase = (currentPhase - 2) % 250;
	}

	public void setMagnitude(double max1) {
		max = max1;
		setyValue();
		repaint();
	}

	private void drawSin(Graphics gr, int currentPhase) {
		gr.setColor(color);
		for (int i = 0; i <= 120; i++) {
			gr.drawLine(i, (int) yValue[(i + currentPhase + 250) % 250], i + 1,
					(int) yValue[(i + currentPhase + 251) % 250]);
		}

	}

	public void actionPerformed(ActionEvent event) {
		repaint();
	}

	public void setFreq(double freq1) {
		freq = freq1 / 250;
		setyValue();
		repaint();
	}

	public void setPhi(double phi1) {
		phi = phi1;
		setyValue();
		repaint();
	}

	private void setyValue() {
		double w = 2 * Math.PI * freq;
		int h = this.getHeight() / 2;
		if (h == 0)
			h = 53;
		for (int i = 0; i < yValue.length; i++) {
			double sin = Math.sin(i * w + phi);
			yValue[i] = max * sin + h;
		}
	}

}