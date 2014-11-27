package my.vip.applets.filters;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author tdongsi
 *
 */
public class Filters extends JApplet implements ChangeListener, ActionListener,
		AdjustmentListener {
	/**
	 * Drop-down menu of filters: lowpass, highpass, bandpass, bandstop
	 */
	private JComboBox filterList;
	
	/**
	 * Sliders to set values of frequency, resistance, capacity, inductance  
	 */
	private JSlider freqSlider, rSlider, cSlider, lSlider;
	
	/**
	 * Labels
	 */
	private JLabel filterLabel, rLabel, cLabel, lLabel, charLabel;
	
	/**
	 * Min, max, initial values for the slider that controls frequency value 
	 */
	private final int FMIN = 1, FMAX = 10, FINIT = 5;
	
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
	 * Magnitudes of the component waves to create a square wave 
	 */
	private final double[] SQUARE = { 1.27324, 0.0, 0.42441, 0.0, 0.25465 };
	
	/**
	 * Magnitudes of the component waves to create a saw-tooth wave
	 */
	private final double[] SAWTOOTH = { 0.31830, 0.15915, 0.10610, 0.07958, 0.06366 };
	
	/**
	 * Panels to display input and output waves
	 */
	private FourierPanel inWave, outWave;
	
	/**
	 * Panels to display the component waves and controls to change their amplitude and phase
	 */
	private ComponentPanel inCmp[], outCmp[];
	
	/**
	 * Buttons
	 */
	private JButton square, sawtooth, pause, showLeft, showRight;
	
	/**
	 * Timer for animation
	 */
	private Timer animationTimer;
	private static int animationDelay = 32;

	/**
	 * Scroll bars for input and output component waves, respectively
	 */
	private JScrollBar inBar, outBar;
	
	/**
	 * Panes associated with the above scroll bars
	 */
	private JScrollPane inScroller, outScroller;

	private JPanel leftDeck, rightDeck;
	private CardLayout cardLeft, cardRight;
	
	/**
	 * Header panel: containing input wave, the filter circuit, and the output wave
	 */
	private JPanel header;
	private JPanel input;
	private JPanel filter;
	private JPanel output;
	
	/**
	 * Body panel: containing input sinusoidal components, circuit parameters, and output sinusoidal components
	 */
	private JPanel body;
	private JPanel leftPanel;
	private JPanel centerPanel;
	private JPanel rightPanel;
	private JPanel rlcControl;

	/************************************
	 * Internal states of the application
	 ************************************/
	
	/**
	 * Common frequency of the waves 
	 */
	private int frequency;
	private int index;
	
	/**
	 * Current values of resistance, capacitance, and inductance
	 */
	private double r, c, l;
	
	/**
	 * The magnitudes 
	 */
	private double magResponse[] = new double[FourierPanel.HARMONIC_NUMBER];
	/**
	 * The phases
	 */
	private double phiResponse[] = new double[FourierPanel.HARMONIC_NUMBER];

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = Filters.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find fil: " + path);
			return null;
		}
	}

	public void init() {
		r = (double) RINIT;
		c = (double) CINIT;
		l = (double) LINIT;
		frequency = FINIT;
		for (int i = 0; i < magResponse.length; i++) {
			magResponse[i] = 1.0;
			phiResponse[i] = 0.0;
		}
		
		prepareHeaderPanel();

		prepareBodyPanel();

		Container container = getContentPane();
		container.add(header, BorderLayout.NORTH);
		container.add(body, BorderLayout.CENTER);
		
		// Start animation
		animationTimer = new Timer(animationDelay, this);
		animationTimer.start();
	}

	private void prepareBodyPanel() {
		
		Color[] inColor = { Color.red, Color.orange, Color.magenta,
				Color.green, Color.cyan };
		String[] inString = { "Fundamental f", "1st harmonic 2f",
				"2nd harmonic 3f", "3rd harmonic 4f", "4th harmonic 5f" };
		
		/*
		 * LEFT 
		 */
		prepareBodyLeft(inColor, inString);
		
		/*
		 * RIGHT
		 */
		prepareBodyRight(inColor, inString);
		
		/*
		 * CENTER
		 */
		prepareBodyCenter();

		body = new JPanel(new GridLayout(1, 3));
		body.add(leftPanel);
		body.add(centerPanel);
		body.add(rightPanel);
		body.setBackground(Color.white);
	}

	private void prepareBodyCenter() {
		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.white);
		
		// Add two buttons for special periodic waves, namely square and sawtooth
		JPanel buttGroup = new JPanel(new BorderLayout());
		buttGroup.setBackground(Color.white);
		JPanel buttRow = new JPanel(new GridLayout(1, 2));
		JLabel buttLabel = new JLabel("Special waves", JLabel.CENTER);
		square = new JButton(" Square");
		sawtooth = new JButton("Sawtooth");
		square.addActionListener(this);
		sawtooth.addActionListener(this);
		buttRow.add(square);
		buttRow.add(sawtooth);
		buttGroup.add(buttLabel, BorderLayout.NORTH);
		buttGroup.add(buttRow, BorderLayout.CENTER);

		// Add a group of sliders for controlling filter parameters
		prepareBodyCenterRlcControl();

		// Add a button for pausing
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

		centerPanel.add(buttGroup, BorderLayout.NORTH);
		centerPanel.add(rlcControl, BorderLayout.CENTER);
		centerPanel.add(pause, BorderLayout.SOUTH);
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	private void prepareBodyCenterRlcControl() {
		rlcControl = new JPanel(new GridLayout(5, 1, 0, 10));
		rlcControl.setBackground(Color.white);
		JLabel rlcTitle = new JLabel("Filter parameter control", JLabel.CENTER);
		rlcTitle.setForeground(Color.blue);
		rlcControl.add(rlcTitle);
		
		// Create label and slider for setting resistance
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
		rlcControl.add(rControl);
		
		// Create label and slider for setting capacitance
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
		rlcControl.add(cControl);
		
		// Create label and slider for setting inductance
		ImageIcon lIcon = createImageIcon("images/inductor.jpg");
		lLabel = new JLabel("   L = " + (double) LINIT / 100 + " milliHenry",
				lIcon, JLabel.CENTER);
		lSlider = new JSlider(JSlider.HORIZONTAL, LMIN, LMAX, LINIT);
		lSlider.addChangeListener(this);
		lSlider.setMajorTickSpacing(100);
		lSlider.setMinorTickSpacing(50);
		lSlider.setPaintTicks(true);
		lSlider.setPaintLabels(true);
		lSlider.setBackground(Color.white);
		JPanel lControl = new JPanel(new BorderLayout());
		lControl.setBackground(Color.white);
		lControl.add(lLabel, BorderLayout.NORTH);
		lControl.add(lSlider, BorderLayout.CENTER);
		lSlider.setEnabled(false);
		rlcControl.add(lControl);
		
		// Create a lable to show characteristic frequency of the filter
		charLabel = new JLabel(" Characteristic frequency: " + charFreq(index)
				+ " Hz", JLabel.CENTER);
		rlcControl.add(charLabel);
	}

	private void prepareBodyRight(Color[] inColor, String[] inString) {
		// Add panels for harmonic components
		outCmp = new ComponentPanel[FourierPanel.HARMONIC_NUMBER];
		JPanel outSet = new JPanel(new GridLayout(FourierPanel.HARMONIC_NUMBER, 1, 0, 2));
		outSet.setPreferredSize(new Dimension(200, 625));

		for (int i = 0; i < outCmp.length; i++) {
			outCmp[i] = new ComponentPanel(inString[i], outWave.magnitude[i],
					outWave.phase[i], (double) FINIT * (i + 1), inColor[i]);
			outCmp[i].magSlider.setEnabled(false);
			outCmp[i].phiSlider.setEnabled(false);
			outSet.add(outCmp[i]);
		}

		outScroller = new JScrollPane(outSet);
		outScroller.setPreferredSize(new Dimension(200, 300));
		outBar = outScroller.getVerticalScrollBar();
		outBar.addAdjustmentListener(this);
		
		showRight = new JButton("Sinusoidal components");
		showRight.addActionListener(this);

		rightPanel = new JPanel(new BorderLayout(0, 5));
		rightPanel.setBackground(Color.white);
		
		rightDeck = new JPanel();
		cardRight = new CardLayout();
		rightDeck.setLayout(cardRight);
		JPanel blank1 = new JPanel(new BorderLayout());
		blank1.setBackground(Color.white);
		JLabel rightBlank = new JLabel("Click above to view components",
				JLabel.CENTER);
		blank1.add(rightBlank, BorderLayout.CENTER);
		rightDeck.add(blank1, "card one");
		rightDeck.add(outScroller, "card two");
		
		rightPanel.add(showRight, BorderLayout.NORTH);
		rightPanel.add(rightDeck, BorderLayout.CENTER);
	}

	private void prepareBodyLeft(Color[] inColor, String[] inString) {
		// Add panels for harmonic components
		inCmp = new ComponentPanel[FourierPanel.HARMONIC_NUMBER];
		JPanel inSet = new JPanel(new GridLayout(FourierPanel.HARMONIC_NUMBER, 1, 0, 2));
		inSet.setPreferredSize(new Dimension(200, 625));

		for (int i = 0; i < inCmp.length; i++) {
			inCmp[i] = new ComponentPanel(inString[i], inWave.magnitude[i],
					inWave.phase[i], (double) FINIT * (i + 1), inColor[i]);
			inCmp[i].magSlider.addChangeListener(this);
			inCmp[i].phiSlider.addChangeListener(this);
			inSet.add(inCmp[i]);
		}

		inScroller = new JScrollPane(inSet);
		inScroller.setPreferredSize(new Dimension(200, 300));
		inBar = inScroller.getVerticalScrollBar();
		inBar.addAdjustmentListener(this);
		
		showLeft = new JButton("Sinusoidal components");
		showLeft.addActionListener(this);
		
		leftPanel = new JPanel(new BorderLayout(0, 5));
		leftPanel.setBackground(Color.white);
		
		leftDeck = new JPanel();
		cardLeft = new CardLayout();
		leftDeck.setLayout(cardLeft);
		JPanel blank = new JPanel(new BorderLayout());
		blank.setBackground(Color.white);
		JLabel leftBlank = new JLabel("Click above to view components",
				JLabel.CENTER);
		blank.add(leftBlank, BorderLayout.CENTER);
		leftDeck.add(blank, "card one");
		leftDeck.add(inScroller, "card two");

		leftPanel.add(showLeft, BorderLayout.NORTH);
		leftPanel.add(leftDeck, BorderLayout.CENTER);
	}

	private void prepareHeaderPanel() {
		header = new JPanel(new GridLayout(1, 3));
		
		prepareHeaderInput();
		
		prepareHeaderFilter();

		prepareHeaderOutput();

		header.add(input);
		header.add(filter);
		header.add(output);
	}

	private void prepareHeaderOutput() {
		output = new JPanel(new BorderLayout());
		
		JLabel outLabel = new JLabel("Output", JLabel.CENTER);
		outLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		outWave = new FourierPanel();
		
		output.setBackground(Color.white);
		output.add(outLabel, BorderLayout.NORTH);
		output.add(outWave, BorderLayout.CENTER);
	}

	private void prepareHeaderFilter() {
		filter = new JPanel(new BorderLayout());
		
		// Create a slider to selecting the value of base frequency
		freqSlider = new JSlider(JSlider.HORIZONTAL, FMIN, FMAX, FINIT);
		freqSlider.addChangeListener(this);
		freqSlider.setMajorTickSpacing(1);
		freqSlider.setSnapToTicks(true);
		freqSlider.setPaintLabels(true);
		freqSlider.setBackground(Color.white);
		JPanel freqPanel = new JPanel(new BorderLayout());
		freqPanel.setBackground(Color.white);
		JLabel freqLabel = new JLabel("  f kHz", JLabel.CENTER);
		freqPanel.add(freqLabel, BorderLayout.WEST);
		freqPanel.add(freqSlider, BorderLayout.CENTER);

		// Create a combo box for selecting the filter type
		String[] filterString = { "No filter", "Lowpass filter",
				"Highpass filter", "Bandpass filter", "Bandstop filter" };
		filterList = new JComboBox(filterString);
		filterList.addActionListener(this);

		// Draw the filter circuit
		filterLabel = new JLabel();
		filterLabel.setHorizontalAlignment(JLabel.CENTER);
		updateFilter(filterList.getSelectedIndex());
		filterLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		filterLabel.setPreferredSize(new Dimension(200, 120));
		
		// Put the filter panel together
		filter.setBackground(Color.white);
		filter.add(filterLabel, BorderLayout.CENTER);
		filter.add(filterList, BorderLayout.NORTH);
		filter.add(freqPanel, BorderLayout.SOUTH);
	}

	private void prepareHeaderInput() {
		input = new JPanel(new BorderLayout());
		
		JLabel inLabel = new JLabel("Input", JLabel.CENTER);
		inLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		inWave = new FourierPanel();
		
		input.setBackground(Color.white);
		input.add(inLabel, BorderLayout.NORTH);
		input.add(inWave, BorderLayout.CENTER);
	}

	/**
	 * Load the right filter circuit image, based on index number
	 * 
	 * @param i
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

	/* 
	 * Define actions for different action events, coming from different GUI sources
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		// If it is the timer, repaint all the panels
		if (source == animationTimer) {
			inWave.repaint();
			outWave.repaint();
			for (int i = 0; i < inCmp.length; i++) {
				inCmp[i].panel.repaint();
				outCmp[i].panel.repaint();
			}
			
			// If it is from the drop-down menu
		} else if (source == filterList) {
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
			charLabel.setText(" Characteristic frequency: "
					+ (int) charFreq(index) + " Hz");
			updateOut();
			
			// If it is from the "Square" (Special waves) button
		} else if (source == square) {
			for (int i = 0; i < inCmp.length; i++) {
				inCmp[i].magSlider.setValue((int) (22 * SQUARE[i]));
				inCmp[i].phiSlider.setValue(0);
			}
			
			// From the "Sawtooth" (Special waves) button
		} else if (source == sawtooth) {
			for (int i = 0; i < inCmp.length; i++) {
				inCmp[i].magSlider.setValue((int) (90 * SAWTOOTH[i]));
				inCmp[i].phiSlider.setValue(0);
			}
			
			// From the right button "Sinusoidal components"
		} else if (source == showRight) {
			cardRight.next(rightDeck);
			
			// From the left button "Sinusoidal components"
		} else if (source == showLeft) {
			cardLeft.next(leftDeck);
		}

	}

	/* 
	 * Define different actions for different sliders
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		
		if (source == freqSlider) {
			// From frequency slider ("f kHz")
			
			frequency = freqSlider.getValue();
			inWave.setFreq((double) frequency);
			outWave.setFreq((double) frequency);
			for (int i = 0; i < inCmp.length; i++) {
				inCmp[i].freq = (i + 1) * frequency;
				inCmp[i].update();
				outCmp[i].freq = (i + 1) * frequency;
				outCmp[i].update();
			}
			updateOut();
			
		} else if (source == rSlider) {
			// From resistance slider
			
			r = (double) rSlider.getValue();
			rLabel.setText("   R = " + r + " ohm");
			charLabel.setText(" Characteristic frequency: "
					+ (int) charFreq(index) + " Hz");
			if (index != 0) {
				updateOut();
			}
			
		} else if (source == cSlider) {
			// From capacitance slider
			
			c = (double) cSlider.getValue();
			cLabel.setText("   C = " + c / 100 + " microFarad");
			charLabel.setText(" Characteristic frequency: "
					+ (int) charFreq(index) + " Hz");
			if (index != 0) {
				updateOut();
			}
			
		} else if (source == lSlider) {
			// From inductance slider
			
			l = (double) lSlider.getValue();
			lLabel.setText("   L = " + l / 100 + " milliHenry");
			charLabel.setText(" Characteristic frequency: "
					+ (int) charFreq(index) + " Hz");
			if (index != 0) {
				updateOut();
			}
			
		} else {
			// From one of sliders in ComponentPanel instances
			
			for (int i = 0; i < inCmp.length; i++) {
				if (source == inCmp[i].magSlider) {
					int value = inCmp[i].magSlider.getValue();
					inCmp[i].mag = (double) value;
					inCmp[i].update();
					inWave.setMagnitude(i, (double) value);
					outWave.setMagnitude(i, magResponse[i] * inWave.magnitude[i]);
					outCmp[i].magSlider.setValue((int) outWave.magnitude[i]);
					outCmp[i].mag = outWave.magnitude[i];
					outCmp[i].update();
				} else if (source == inCmp[i].phiSlider) {
					int value = inCmp[i].phiSlider.getValue();
					inCmp[i].phi = (double) value;
					inCmp[i].update();
					inWave.setPhi(i, (double) value);
					outWave.setPhi(i, inWave.phase[i] + phiResponse[i]);
					outCmp[i].phiSlider.setValue((int) outWave.phase[i]);
					outCmp[i].phi = outWave.phase[i];
					outCmp[i].update();
				} // end if
			} // end for
		} // end else
	}

	/* Make the two scroll-bars aligned with each other
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.AdjustmentListener#adjustmentValueChanged(java.awt.event.AdjustmentEvent)
	 */
	public void adjustmentValueChanged(AdjustmentEvent e) {
		if (e.getSource() == inBar) {
			outBar.setValue(inBar.getValue());
		} else if (e.getSource() == outBar) {
			inBar.setValue(outBar.getValue());
		}
	}

	/**
	 * Pure physics: compute magnitude response, based on frequency and filter configuration 
	 * 
	 * @param i: id of the filter configuration (e.g., 1 for low pass)
	 * @param freq: frequency value
	 * @return
	 */
	private double computeMagnitudeResponse(int i, double freq) {
		if (i == 1) {
			double w = Math.PI * 20.0 * freq;
			return 1.0 / Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 2) {
			double w = Math.PI * 20.0 * freq;
			return w * c * r * 1e-6
					/ Math.sqrt(1 + Math.pow(w * c * r * 1e-6, 2.00));
		} else if (i == 3) {
			double w = Math.PI * 20.0 * freq;
			return Math.abs((w * r * c * 1e-6))
					/ Math.sqrt(Math.pow(1 - l * c * 1e-9 * w * w, 2.0)
							+ Math.pow(c * 1e-6 * w * r, 2.0));
		} else if (i == 4) {
			double w = Math.PI * 20.0 * freq;
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
	 * @return
	 */
	private double computePhaseResponse(int i, double freq) {
		if (i == 1) {
			double w = Math.PI * 20.0 * freq;
			return -(180 * Math.atan(w * c * 1e-6 * r) / Math.PI);
		} else if (i == 2) {
			double w = Math.PI * 20.0 * freq;
			return 90 - (180 * Math.atan(w * c * 1e-6 * r) / Math.PI);
		} else if (i == 3) {
			double w = Math.PI * 20.0 * freq;
			return 90 - (180 * Math.atan(w * c * 1e-6 * r
					/ (1 - l * c * 1e-9 * w * w)) / Math.PI);
		} else if (i == 4) {
			double w = Math.PI * 20.0 * freq;
			return -(180 * Math.atan(w * c * 1e-6 * r
					/ (1 - l * c * 1e-9 * w * w)) / Math.PI);
		} else {
			return 0.0;
		}
	}

	/**
	 * Compute characteristic frequency of the filter, based on the filter circuit parameters.
	 *  
	 * @param i: index indicating filter type (e.g., lowpass, bandpass)
	 * @return Characteristic frequency of the filter.
	 */
	private double charFreq(int i) {
		if (i == 1 || i == 2) {
			return 1 / (r * c * 1e-8 * Math.PI * 2);
		} else if (i == 3 || i == 4) {
			return 1 / (Math.sqrt(l * c * 1e-13) * 2 * Math.PI);
		} else {
			return 0.0;
		}
	}

	/**
	 * Compute the phase and magnitude response and update the output waves.
	 */
	private void updateOut() {
		for (int i = 0; i < magResponse.length; i++) {
			magResponse[i] = computeMagnitudeResponse(index, (double) ((i + 1) * frequency));
			phiResponse[i] = computePhaseResponse(index, (double) ((i + 1) * frequency));
			outWave.setMagnitude(i, magResponse[i] * inWave.magnitude[i]);
			outWave.setPhi(i, inWave.phase[i] + phiResponse[i]);
			outCmp[i].magSlider.setValue((int) outWave.magnitude[i]);
			outCmp[i].mag = outWave.magnitude[i];
			outCmp[i].update();
			outCmp[i].phiSlider.setValue((int) outWave.phase[i]);
			outCmp[i].phi = outWave.phase[i];
			outCmp[i].update();
		}
	}

}
