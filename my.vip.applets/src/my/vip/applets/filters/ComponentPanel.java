package my.vip.applets.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

class ComponentPanel extends JPanel {
	JSlider phiSlider, magSlider;
	SinePanel panel;
	double freq, mag, phi;
	static final int PHIMAX = 180, PHIMIN = -180;

	public ComponentPanel(String label, double mag1, double phi1, double freq1,
			Color c) {
		phi = phi1;
		mag = mag1;
		freq = freq1;
		int phiD = (int) (phi / Math.PI * 180);

		phiSlider = new JSlider(JSlider.VERTICAL, PHIMIN, PHIMAX, phiD);
		phiSlider.setMajorTickSpacing(90);
		phiSlider.setPaintLabels(true);
		phiSlider.setBorder(BorderFactory.createEtchedBorder());

		magSlider = new JSlider(JSlider.VERTICAL, 0, 30, (int) mag);
		Hashtable labelTable = new Hashtable();
		labelTable.put(new Integer(0), new JLabel("0"));
		labelTable.put(new Integer(30), new JLabel("Max"));
		magSlider.setLabelTable(labelTable);
		magSlider.setPaintLabels(true);
		magSlider.setBorder(BorderFactory.createEtchedBorder());

		JLabel title = new JLabel(label, JLabel.CENTER);
		JLabel phiLabel = new JLabel("Phase", JLabel.CENTER);
		JLabel magLabel = new JLabel("Amp.", JLabel.CENTER);

		JPanel phiPanel = new JPanel(new BorderLayout());
		JPanel magPanel = new JPanel(new BorderLayout());
		phiPanel.add(phiLabel, BorderLayout.NORTH);
		phiPanel.add(phiSlider, BorderLayout.CENTER);
		magPanel.add(magLabel, BorderLayout.NORTH);
		magPanel.add(magSlider, BorderLayout.CENTER);

		JPanel controlPanel = new JPanel(new GridLayout(1, 2));
		controlPanel.setBorder(BorderFactory.createEtchedBorder());
		controlPanel.add(phiPanel);
		controlPanel.add(magPanel);

		panel = new SinePanel(c);
		JPanel displayPanel = new JPanel(new BorderLayout());
		displayPanel.add(title, BorderLayout.NORTH);
		displayPanel.add(panel, BorderLayout.CENTER);

		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.WEST);
		add(displayPanel, BorderLayout.CENTER);
		update();
	}

	public void update() {
		panel.setFreq(freq);
		panel.setPhi(phi);
		panel.setMagnitude(mag);
	}

}
