package my.vip.applets.filters;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 * A panel for each sinusoidal harmonic component of a periodic electrical waveform.
 * It contains sliders that shows and allows control of phase and magnitude values,
 * and a view area that contains an animated sine wave for that harmonic component.
 * 
 * @author tdongsi
 *
 */
class ComponentPanel extends JPanel {
	/**
	 * Sliders that set magnitude and phase values
	 */
	JSlider phiSlider, magSlider;
	
	/**
	 * View panel that displays the sine wave
	 */
	SinePanel panel;
	
	/**
	 * Variables for internal states: frequency, magnitude, and phase.
	 */
	double freq, mag, phi;
	
	/**
	 * Max, min values of the slider that controls phase value
	 */
	private final int PHI_MAX = 180, PHI_MIN = -180;
	
	/**
	 * Max, min values of the slider that controls magnitude value
	 */
	private final int MAG_MAX = 30, MAG_MIN = 0;
	
	/**
	 * Control panel: with two sliders to control magnitude and phase.
	 */
	private JPanel controlPanel;
	
	/**
	 * View panel: showing an animated sine wave.
	 */
	private JPanel displayPanel;

	public ComponentPanel(String label, double magnitude, double phi, double freq,
			Color c) {
		this.phi = phi;
		this.mag = magnitude;
		this.freq = freq;
		
		prepareControlPanel();
		prepareViewPanel(label, c);

		setLayout(new BorderLayout());
		add(controlPanel, BorderLayout.WEST);
		add(displayPanel, BorderLayout.CENTER);
		update();
	}

	private void prepareViewPanel(String label, Color c) {
		JLabel title = new JLabel(label, JLabel.CENTER);
		panel = new SinePanel(c);
		
		displayPanel = new JPanel(new BorderLayout());
		displayPanel.add(title, BorderLayout.NORTH);
		displayPanel.add(panel, BorderLayout.CENTER);
	}

	private void prepareControlPanel() {
		int phiD = (int) (this.phi / Math.PI * 180);
		phiSlider = new JSlider(JSlider.VERTICAL, PHI_MIN, PHI_MAX, phiD);
		phiSlider.setMajorTickSpacing(90);
		phiSlider.setPaintLabels(true);
		phiSlider.setBorder(BorderFactory.createEtchedBorder());

		magSlider = new JSlider(JSlider.VERTICAL, MAG_MIN, MAG_MAX, (int) mag);
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("0"));
		labelTable.put(new Integer(30), new JLabel("Max"));
		magSlider.setLabelTable(labelTable);
		magSlider.setPaintLabels(true);
		magSlider.setBorder(BorderFactory.createEtchedBorder());
		
		JLabel phiLabel = new JLabel("Phase", JLabel.CENTER);
		JLabel magLabel = new JLabel("Amp.", JLabel.CENTER);

		JPanel phiPanel = new JPanel(new BorderLayout());
		JPanel magPanel = new JPanel(new BorderLayout());
		phiPanel.add(phiLabel, BorderLayout.NORTH);
		phiPanel.add(phiSlider, BorderLayout.CENTER);
		magPanel.add(magLabel, BorderLayout.NORTH);
		magPanel.add(magSlider, BorderLayout.CENTER);

		controlPanel = new JPanel(new GridLayout(1, 2));
		controlPanel.setBorder(BorderFactory.createEtchedBorder());
		controlPanel.add(phiPanel);
		controlPanel.add(magPanel);
	}

	public void update() {
		panel.setFreq(freq);
		panel.setPhi(phi);
		panel.setMagnitude(mag);
	}

}
