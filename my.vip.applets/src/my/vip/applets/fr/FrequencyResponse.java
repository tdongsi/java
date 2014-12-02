package my.vip.applets.fr;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Hashtable;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.NumberFormatter;

/**
 * In this implementation, an interactive application demonstrates different passive analog filters, 
 * namely lowpass, highpass, bandpass, bandstop filters. 
 * Their ideal frequency response and actual frequency response will be demonstrated.
 * 
 * @author tdongsi
 *
 */
public class FrequencyResponse extends JApplet implements ChangeListener,
		ActionListener, PropertyChangeListener {
	/**
	 * Drop-down menu of filters: lowpass, highpass, bandpass, bandstop
	 */
	private JComboBox filterList;
	
	/**
	 * Sliders to set values of frequency, resistance, capacity, inductance, respectively.
	 */
	private JSlider freqSlider, rSlider, cSlider, lSlider;
	
	/**
	 * Labels
	 */
	private JLabel filterLabel, rLabel, cLabel, lLabel, charLabel;
	
	/**
	 * Input text field for frequency value.
	 */
	private JFormattedTextField freqField;
	
	/**
	 * Min, max, initial values for the slider that controls frequency value 
	 */
	private final int FMIN = 100, FMAX = 4100, FINIT = 500;
	
	/**
	 * Min, max, initial values for the slider that controls resistance value 
	 */
	private final int RMIN = 10, RMAX = 100, RINIT = 50;
	
	/**
	 * Min, max, initial values for the slider that controls capacitance value
	 */
	private final int CMIN = 10, CMAX = 100, CINIT = 20;
	
	/**
	 * Min, max, initial values for the slider that controls inductance value
	 */
	private final int LMIN = 50, LMAX = 850, LINIT = 200;
	
	/**
	 * Panels to display input and output waves
	 */
	private SinePanel inWave, outWave;
	
	/**
	 * Timer for animation
	 */
	private static int animationDelay = 32;
	private Timer animationTimer;

	/**
	 * Current value of frequency
	 */
	private int frequency;
	
	/**
	 * ID of the current filter. E.g., 1 for lowpass filter. 
	 */
	private int index;
	
	/**
	 * Current values of resistance, capacitance, and inductance
	 */
	private int r, c, l;
	
	/**
	 * Pause button
	 */
	private JButton pause;
	
	
	/**
	 * Header panel: containing input wave, the filter circuit, and the output wave
	 */
	private JPanel header;
	private JPanel input;
	private JPanel filter;
	private JPanel output;
	
	/**
	 * Body panel: containing circuit parameters and their controls, as well as frequency responses
	 */
	private JPanel body;
	private JPanel freqPanel;
	private JPanel rlcControl;

	/**
	 * A panel with actual and ideal frequency response drawn on it.
	 */
	private FrequencyPanel frequencyResponse;

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = FrequencyResponse.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find fil: " + path);
			return null;
		}
	}

	public void init() {
		r = RINIT;
		c = CINIT;
		l = LINIT;
		frequency = FINIT / 100;

		prepareHeaderPanel();
		
		prepareBodyPanel();

		Container container = getContentPane();
		container.setLayout(new BorderLayout(0, 10));
		container.setBackground(Color.white);
		container.add(header, BorderLayout.NORTH);
		container.add(body, BorderLayout.CENTER);
		
		// Start animation
		animationTimer = new Timer(animationDelay, this);
		animationTimer.start();
	}

	private void prepareBodyPanel() {
		body = new JPanel(new BorderLayout(5, 0));
		
		// Prepare JPanel freqPanel
		prepareFrequencyControl();

		// Prepare JPanel rlcControl
		prepareCircuitControl();
		
		// Pause button
		pause = new JButton("Pause animation");
		pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (animationTimer.isRunning()) {
					animationTimer.stop();
					pause.setText("Resume animation");
				} else {
					animationTimer.start();
					pause.setText("Pause animation");
				}
			}
		});
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.white);
		centerPanel.add(rlcControl, BorderLayout.CENTER);
		centerPanel.add(pause, BorderLayout.SOUTH);

		JPanel display = new JPanel(new BorderLayout());
		frequencyResponse = new FrequencyPanel();
		display.add(frequencyResponse, BorderLayout.CENTER);
		display.add(freqPanel, BorderLayout.SOUTH);
		
		body.add(centerPanel, BorderLayout.WEST);
		body.add(display, BorderLayout.CENTER);
		body.setBackground(Color.white);
	}

	/**
	 * Prepare sliders and associated labels
	 * for setting resistance, capacitance, and inductance for the filter circuits
	 */
	private void prepareCircuitControl() {
		// Resistance slider and its associated label
		ImageIcon rIcon = createImageIcon("images/resistor.jpg");
		rLabel = new JLabel("   R = " + RINIT + " ohm", rIcon, JLabel.CENTER);
		rSlider = new JSlider(JSlider.HORIZONTAL, RMIN, RMAX, RINIT);
		rSlider.addChangeListener(this);
		rSlider.setMajorTickSpacing(10);
		rSlider.setMinorTickSpacing(5);
		rSlider.setPaintTicks(true);
		rSlider.setPaintLabels(true);
		rSlider.setBackground(Color.white);
		JPanel rControl = new JPanel(new BorderLayout());
		rControl.setBackground(Color.white);
		rControl.add(rLabel, BorderLayout.NORTH);
		rControl.add(rSlider, BorderLayout.CENTER);
		
		// Capacitance slider and its associated label
		ImageIcon cIcon = createImageIcon("images/capacitor.jpg");
		cLabel = new JLabel("   C = " + (double) CINIT / 100 + " microFarad",
				cIcon, JLabel.CENTER);
		cSlider = new JSlider(JSlider.HORIZONTAL, CMIN, CMAX, CINIT);
		cSlider.addChangeListener(this);
		cSlider.setMajorTickSpacing(10);
		cSlider.setMinorTickSpacing(5);
		cSlider.setPaintTicks(true);
		cSlider.setPaintLabels(true);
		cSlider.setBackground(Color.white);
		JPanel cControl = new JPanel(new BorderLayout());
		cControl.setBackground(Color.white);
		cControl.add(cLabel, BorderLayout.NORTH);
		cControl.add(cSlider, BorderLayout.CENTER);
		cSlider.setEnabled(false);
		
		// Inductance slider and its associated label
		ImageIcon lIcon = createImageIcon("images/inductor.jpg");
		lLabel = new JLabel("   L = " + (double) LINIT / 100 + " milliHenry",
				lIcon, JLabel.CENTER);
		lSlider = new JSlider(JSlider.HORIZONTAL, LMIN, LMAX, LINIT);
		lSlider.addChangeListener(this);
		lSlider.setMajorTickSpacing(200);
		lSlider.setMinorTickSpacing(50);
		lSlider.setPaintTicks(true);
		lSlider.setPaintLabels(true);
		lSlider.setBackground(Color.white);
		JPanel lControl = new JPanel(new BorderLayout());
		lControl.setBackground(Color.white);
		lControl.add(lLabel, BorderLayout.NORTH);
		lControl.add(lSlider, BorderLayout.CENTER);
		lSlider.setEnabled(false);
		
		// Label for characteristic frequency
		charLabel = new JLabel(" Characteristic frequency: " + computeCharacteristicFrequency(index)
				+ " Hz", JLabel.CENTER);
		
		rlcControl = new JPanel(new GridLayout(5, 1, 0, 10));
		rlcControl.setBackground(Color.white);
		JLabel rlcTitle = new JLabel("Circuit parameter control", JLabel.CENTER);
		rlcTitle.setForeground(Color.blue);
		rlcControl.add(rlcTitle);
		rlcControl.add(rControl);
		rlcControl.add(cControl);
		rlcControl.add(lControl);
		rlcControl.add(charLabel);
	}

	/**
	 * Prepare a slider and a text field in a JPanel
	 * for setting frequency value.
	 */
	private void prepareFrequencyControl() {
		
		// The slider to set frequency value
		{
			freqSlider = new JSlider(JSlider.HORIZONTAL, FMIN, FMAX, FINIT);
			freqSlider.addChangeListener(this);
			freqSlider.setMajorTickSpacing(100);
			
			Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
			for (int i = FMIN; i <= FMAX; i += 500) {
				labelTable.put(i, new JLabel("" + i + "0"));
			}
			
			freqSlider.setLabelTable(labelTable);
			freqSlider.setPaintLabels(true);
			freqSlider.setBackground(Color.white);
		}

		// The text field to set frequency value
		{
			java.text.NumberFormat numberFormat = java.text.NumberFormat
					.getIntegerInstance();
			NumberFormatter formatter = new NumberFormatter(numberFormat);
			formatter.setMinimum(new Integer(FMIN * 10));
			formatter.setMaximum(new Integer(FMAX * 10));
			
			freqField = new JFormattedTextField(formatter);
			freqField.setValue(new Integer(FINIT * 10));
			freqField.setColumns(4);
			freqField.addPropertyChangeListener(this);
			freqField.getInputMap().put(
					KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "check");
			freqField.getActionMap().put("check", new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					if (!freqField.isEditValid()) {
						Toolkit.getDefaultToolkit().beep();
						freqField.selectAll();
					} else
						try {
							freqField.commitEdit();
						} catch (java.text.ParseException exc) {
						}
				}
			});
		}

		// Put them together in a JPanel named freqPanel
		freqPanel = new JPanel(new BorderLayout());
		freqPanel.setBackground(Color.white);
		JLabel freqLabel = new JLabel(" f Hz", JLabel.CENTER);
		freqPanel.add(freqLabel, BorderLayout.WEST);
		freqPanel.add(freqSlider, BorderLayout.CENTER);
		freqPanel.add(freqField, BorderLayout.EAST);
	}

	private void prepareHeaderPanel() {
		header = new JPanel(new GridLayout(1, 3, 10, 0));
		
		prepareHeaderInput();

		prepareHeaderFilter();

		prepareHeaderOutput();

		header.add(input);
		header.add(filter);
		header.add(output);
		header.setBackground(Color.white);
	}

	private void prepareHeaderOutput() {
		output = new JPanel(new BorderLayout());
		
		// Label
		JLabel outLabel = new JLabel("Output", JLabel.CENTER);
		outLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Panel with animated sine wave
		outWave = new SinePanel(Color.blue);
		
		output.setBackground(Color.white);
		output.add(outLabel, BorderLayout.NORTH);
		output.add(outWave, BorderLayout.CENTER);
	}

	private void prepareHeaderFilter() {
		
		// Label
		filterLabel = new JLabel();
		filterLabel.setHorizontalAlignment(JLabel.CENTER);
		filterLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		filterLabel.setPreferredSize(new Dimension(200, 120));
		
		// Drop-down menu for a list of filters
		String[] filterString = { "No filter", "Lowpass filter",
				"Highpass filter", "Bandpass filter", "Bandstop filter" };
		filterList = new JComboBox<String>(filterString);
		filterList.addActionListener(this);
		updateFilter(filterList.getSelectedIndex());
		
		filter = new JPanel(new BorderLayout());
		filter.setBackground(Color.white);
		filter.add(filterLabel, BorderLayout.CENTER);
		filter.add(filterList, BorderLayout.NORTH);
	}

	private void prepareHeaderInput() {
		input = new JPanel(new BorderLayout());
		
		// Label
		JLabel inLabel = new JLabel("Input", JLabel.CENTER);
		inLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// Panel with animated sine wave
		inWave = new SinePanel(Color.blue);
		
		input.setBackground(Color.white);
		input.add(inLabel, BorderLayout.NORTH);
		input.add(inWave, BorderLayout.CENTER);
	}

	/**
	 * Load the right filter circuit image, based on index number
	 * 
	 * @param i: id of the filter (e.g., 1 for lowpass filter) and also index of image.
	 */
	private void updateFilter(int i) {
		ImageIcon icon;
		String[] filterName = { "None", "Lowpass", "Highpass", "Bandpass",
				"Bandstop" };

		icon = createImageIcon("images/" + filterName[i] + ".jpg");
		filterLabel.setIcon(icon);
		if (icon != null) {
			filterLabel.setText(null);
		} else {
			filterLabel.setText("Images not found");
		}
	}

	public void actionPerformed(ActionEvent e) {
		Object temp = e.getSource();
		if (temp == animationTimer) {
			inWave.repaint();
			outWave.repaint();
		} else if (temp == filterList) {
			index = filterList.getSelectedIndex();
			updateFilter(index);
			if (index == 1 || index == 2) {
				cSlider.setEnabled(true);
				lSlider.setEnabled(false);
			} else if (index > 2) {
				cSlider.setEnabled(true);
				lSlider.setEnabled(true);
			} else {
				cSlider.setEnabled(false);
				lSlider.setEnabled(false);
			}
			charLabel.setText("Characteristic frequency: "
					+ (int) computeCharacteristicFrequency(index) + " Hz");
			updateOutput();
			frequencyResponse.setIndex(index);
		}

	}

	public void stateChanged(ChangeEvent e) {
		JSlider temp = (JSlider) e.getSource();
		if (temp == freqSlider) {
			int value = freqSlider.getValue();
			frequency = value / 100;
			inWave.setFrequency((double) frequency);
			outWave.setFrequency((double) frequency);
			updateOutput();
			frequencyResponse.setFrequency(freqSlider.getValue());
			freqField.setValue(new Integer(10 * value));
			freqField.setText(String.valueOf(10 * value));
		} else if (temp == rSlider) {
			r = rSlider.getValue();
			rLabel.setText("   R = " + r + " ohm");
			charLabel.setText(" Characteristic frequency: "
					+ (int) computeCharacteristicFrequency(index) + " Hz");
			if (index != 0) {
				updateOutput();
			}
			frequencyResponse.setResistance(r);
		} else if (temp == cSlider) {
			c = cSlider.getValue();
			cLabel.setText("   C = " + c / 100 + " microFarad");
			charLabel.setText(" Characteristic frequency: "
					+ (int) computeCharacteristicFrequency(index) + " Hz");
			if (index != 0) {
				updateOutput();
			}
			frequencyResponse.setCapacitance(c);
		} else if (temp == lSlider) {
			l = lSlider.getValue();
			lLabel.setText("   L = " + l / 100 + " milliHenry");
			charLabel.setText(" Characteristic frequency: "
					+ (int) computeCharacteristicFrequency(index) + " Hz");
			if (index != 0) {
				updateOutput();
			}
			frequencyResponse.setInductance(l);
		}
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == freqField) {
			if ("value".equals(e.getPropertyName())) {
				Number value = (Number) e.getNewValue();
				if (value != null) {
					freqSlider.setValue(value.intValue() / 10);
				}
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
	private double computeMagnitudeResponse(int i, double freq) {
		double w = Math.PI * 20.0 * freq;
		
		if (i == 1) {
			return 1.0 / Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 2) {
			return w * c * r * 1e-6
					/ Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 3) {
			return Math.abs((w * r * c * 1e-6))
					/ Math.sqrt(Math.pow(1 - l * c * 1e-9 * w * w, 2.0)
							+ Math.pow(c * 1e-6 * w * r, 2.0));
		} else if (i == 4) {
			return Math.abs((1 - l * c * 1e-9 * w * w))
					/ Math.sqrt(Math.pow(1 - l * c * 1e-9 * w * w, 2.0)
							+ Math.pow(w * r * c * 1e-6, 2.00));
		} else {
			return 1.00;
		}
	}

	/**
	 * Pure physics: compute phase response, based on frequency and filter configuration 
	 * 
	 * @param i: id of the filter configuration (e.g., 1 for low pass)
	 * @param freq: frequency value
	 * @return phase shift
	 */
	private double computePhaseResponse(int i, double freq) {
		double w = Math.PI * 20.0 * freq;
		
		if (i == 1) {
			return -(180 * Math.atan(w * c * 1e-6 * r) / Math.PI);
		} else if (i == 2) {
			return 90 - (180 * Math.atan(w * c * 1e-6 * r) / Math.PI);
		} else if (i == 3) {
			return 90 - (180 * Math.atan(w * c * 1e-6 * r
					/ (1 - l * c * 1e-9 * w * w)) / Math.PI);
		} else if (i == 4) {
			return -(180 * Math.atan(w * c * 1e-6 * r
					/ (1 - l * c * 1e-9 * w * w)) / Math.PI);
		} else {
			return 0.0;
		}
	}

	/**
	 * Compute the characteristic frequency of the filter, based on the current
	 * resistance, capacitance, and inductance as internal variables.
	 * 
	 * @param i: index for the type of filter circuit
	 * @return
	 */
	private double computeCharacteristicFrequency(int i) {
		if (i == 1 || i == 2) {
			return 1 / (r * c * 1e-8 * Math.PI * 2);
		} else if (i == 3 || i == 4) {
			return 1 / (Math.sqrt(l * c * 1e-13) * 2 * Math.PI);
		} else {
			return 0.0;
		}
	}

	/**
	 * Update and re-draw the output wave
	 */
	private void updateOutput() {
		outWave.setMagnitude(inWave.getMagnitude()
				* computeMagnitudeResponse(index, frequency));
		outWave.setPhase(inWave.getPhase() + computePhaseResponse(index, frequency));
	}

}
