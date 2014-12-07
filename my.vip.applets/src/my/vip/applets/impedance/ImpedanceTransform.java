package my.vip.applets.impedance;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * In this implementation, an interactive application demonstrates how to eliminate
 * an ideal transformer in a circuit for impedance computation. The idea is
 * to shift impedance to one side of the transformer, either primary or secondary.
 * 
 * @author tdongsi
 *
 */
class ImpedanceTransform extends JApplet implements ItemListener,
		ChangeListener {
	/**
	 * Initial value for source impedance and voltage
	 */
	private Complex[] sourceValue = { new Complex(1000.0), new Complex(1000.0) };
	/**
	 * Initial value for load impedance
	 */
	private Complex[] loadValue = { new Complex(1000.0) };
	
	/**
	 * Information for labels
	 */
	private final CustomLabel.LabelData rSourceData = new CustomLabel.LabelData("R", 45, 35);
	private final CustomLabel.LabelData vSourceData = new CustomLabel.LabelData("V", 35, 70);
	private final CustomLabel.LabelData rLoadData = new CustomLabel.LabelData("R", 20, 70);
	private CustomLabel.LabelData[] sourceLabel = {rSourceData, vSourceData};
	private CustomLabel.LabelData[] loadLabel = {rLoadData};
	
	/**
	 * Labels for radio buttons at bottom.
	 */
	private final String[] label = { "View from source", "Normal view",
			"View from load" };
	/**
	 * States for radio buttons.
	 */
	private final boolean[] buttonSelect = { false, true, false };
	
	/**
	 * Min, max, initial values for the slider that controls transformer's winding ratio
	 */
	private final int RMIN = 0, RMAX = 18, RINIT = 9;
	/**
	 * Min, max, initial values for the slider that controls impedance (real and imaginary) values.
	 */
	private final int IMIN = 50, IMAX = 1000, IINIT = 1000;
	/**
	 * Min, max, initial values for the slider that controls voltage (real and imaginary) values.
	 */
	private final int VMIN = 0, VMAX = 1000, VINIT = 1000;
	/**
	 * Labels and value mapping for slider that controls transformer's primary/secondary winding ratio.
	 */
	private final String[] ratioLabel = { "1:10", "1:9", "1:8", "1:7", "1:6",
			"1:5", "1:4", "1:3", "1:2", "1:1", "2:1", "3:1", "4:1", "5:1",
			"6:1", "7:1", "8:1", "9:1", "10:1" };
	private final double[] ratioMap = { 1.0/10, 1.0/9, 1.0/8, 1.0/7, 1.0/6, 1.0/5, 1.0/4, 1.0/3, 1.0/2, 1.0, 2.0,
			3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0 };

	/**
	 * Labels in Normal view: source side, transformer, load side.
	 */
	CustomLabel sourceNormal, transNormal, loadNormal;
	/**
	 * Labels in Source view: source side, load side.
	 */
	CustomLabel sourceSource, loadSource;
	/**
	 * Labels in Load view: source side, load side.
	 */
	CustomLabel sourceLoad, loadLoad;
	/**
	 * Images for circuit of source side, transformer, and circuit for load side.
	 */
	ImageIcon sourceIcon, transIcon, loadIcon;
	JPanel diagram;
	CardLayout cardManager;
	JRadioButton[] radioButton = new JRadioButton[3];
	ButtonGroup radioGroup;

	/**
	 * Slider to specify winding ratio (primary/secondary) of the transformer.
	 */
	JSlider ratioSlider;
	/**
	 * Sliders for real parts of source impedance, load impedance, and source voltage. 
	 */
	JSlider reSourceSlider, reLoadSlider, reVoltSlider;
	/**
	 * Sliders for imaginary parts of source impedance, load impedance, and source voltage. 
	 */
	JSlider imSourceSlider, imLoadSlider, imVoltSlider;
	
	JLabel ratio;
	/**
	 * Voltage at primary and secondary winding, respectively
	 */
	double vp, vs;

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = ImpedanceTransform.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	public void init() {
		vp = 500.0;
		vs = 500.0;
		diagram = new JPanel();
		cardManager = new CardLayout();
		diagram.setLayout(cardManager);

		JPanel normal = new JPanel(new GridLayout(1, 3));
		normal.setBackground(Color.white);
		sourceIcon = createImageIcon("images/Source.jpg");
		transIcon = createImageIcon("images/Transformer.jpg");
		loadIcon = createImageIcon("images/Load.jpg");
		sourceNormal = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue);
		transNormal = new CustomLabel(transIcon);
		loadNormal = new CustomLabel(loadIcon, 1, loadLabel, loadValue);
		normal.add(sourceNormal);
		normal.add(transNormal);
		normal.add(loadNormal);

		JPanel fromSource = new JPanel(new GridLayout(1, 3));
		fromSource.setBackground(Color.white);
		sourceSource = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue);
		loadSource = new CustomLabel(loadIcon, 1, loadLabel, loadValue);
		fromSource.add(sourceSource);
		fromSource.add(loadSource);
		fromSource.add(Box.createHorizontalStrut(100));

		JPanel fromLoad = new JPanel(new GridLayout(1, 3));
		fromLoad.setBackground(Color.white);
		sourceLoad = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue);
		loadLoad = new CustomLabel(loadIcon, 1, loadLabel, loadValue);
		fromLoad.add(Box.createHorizontalStrut(100));
		fromLoad.add(sourceLoad);
		fromLoad.add(loadLoad);

		diagram.add(fromSource, label[0]);
		diagram.add(normal, label[1]);
		diagram.add(fromLoad, label[2]);
		cardManager.show(diagram, label[1]);

		JPanel checkBox = new JPanel(new GridLayout(1, 3));
		checkBox.setBackground(Color.white);
		checkBox.setPreferredSize(new Dimension(200, 60));
		radioGroup = new ButtonGroup();
		for (int i = 0; i < radioButton.length; i++) {
			radioButton[i] = new JRadioButton(label[i], buttonSelect[i]);
			radioButton[i].setBackground(Color.white);
			checkBox.add(radioButton[i]);
			radioButton[i].addItemListener(this);
			radioGroup.add(radioButton[i]);
		}

		ratioSlider = new JSlider(JSlider.HORIZONTAL, RMIN, RMAX, RINIT);
		// ratioSlider.setBackground( Color.white );
		ratioSlider.addChangeListener(this);
		Hashtable<Integer,JLabel> labelTable = new Hashtable<Integer,JLabel>();
		for (int i = RMIN; i <= RMAX; i++) {
			labelTable.put(new Integer(i), new JLabel(ratioLabel[i]));
		}
		ratioSlider.setLabelTable(labelTable);
		ratioSlider.setPaintLabels(true);
		Border lowEtched = BorderFactory
				.createEtchedBorder(EtchedBorder.LOWERED);
		ratioSlider.setBorder(BorderFactory.createTitledBorder(lowEtched,
				"Winding ratio", TitledBorder.CENTER, TitledBorder.TOP));
		ratioSlider.setSnapToTicks(true);

		ratio = new JLabel("Vp: " + vp + "            " + ratioLabel[RINIT]
				+ "            " + "Vs: " + vs, JLabel.CENTER);
		ratio.setForeground(Color.red);

		JPanel impPanel = new JPanel(new GridLayout(1, 2, 40, 0));

		JPanel sourcePanel = new JPanel(new GridLayout(2, 1));
		JPanel reSourcePan = new JPanel(new BorderLayout());
		JPanel imSourcePan = new JPanel(new BorderLayout());
		JLabel reSourceLab = new JLabel("Re  ", JLabel.CENTER);
		JLabel imSourceLab = new JLabel("Im  ", JLabel.CENTER);
		reSourceSlider = new JSlider(JSlider.HORIZONTAL, IMIN, IMAX, IINIT);
		imSourceSlider = new JSlider(JSlider.HORIZONTAL, -IMAX, IMAX, 0);
		reSourceSlider.addChangeListener(this);
		imSourceSlider.addChangeListener(this);
		reSourcePan.add(reSourceLab, BorderLayout.WEST);
		reSourcePan.add(reSourceSlider, BorderLayout.CENTER);
		imSourcePan.add(imSourceLab, BorderLayout.WEST);
		imSourcePan.add(imSourceSlider, BorderLayout.CENTER);
		sourcePanel.add(reSourcePan);
		sourcePanel.add(imSourcePan);
		sourcePanel.setPreferredSize(new Dimension(200, 60));
		sourcePanel.setBorder(BorderFactory.createTitledBorder(lowEtched,
				" Source impedance (ohm) ", TitledBorder.CENTER,
				TitledBorder.TOP));

		JPanel loadPanel = new JPanel(new GridLayout(2, 1));
		JPanel reLoadPan = new JPanel(new BorderLayout());
		JPanel imLoadPan = new JPanel(new BorderLayout());
		JLabel reLoadLab = new JLabel("Re  ", JLabel.CENTER);
		JLabel imLoadLab = new JLabel("Im  ", JLabel.CENTER);
		reLoadSlider = new JSlider(JSlider.HORIZONTAL, IMIN, IMAX, IINIT);
		imLoadSlider = new JSlider(JSlider.HORIZONTAL, -IMAX, IMAX, 0);
		reLoadSlider.addChangeListener(this);
		imLoadSlider.addChangeListener(this);
		reLoadPan.add(reLoadLab, BorderLayout.WEST);
		reLoadPan.add(reLoadSlider, BorderLayout.CENTER);
		imLoadPan.add(imLoadLab, BorderLayout.WEST);
		imLoadPan.add(imLoadSlider, BorderLayout.CENTER);
		loadPanel.add(reLoadPan);
		loadPanel.add(imLoadPan);
		loadPanel.setPreferredSize(new Dimension(200, 60));
		loadPanel.setBorder(BorderFactory
				.createTitledBorder(lowEtched, " Load impedance (ohm) ",
						TitledBorder.CENTER, TitledBorder.TOP));

		impPanel.add(sourcePanel);
		impPanel.add(loadPanel);

		JPanel voltPanel = new JPanel(new GridLayout(1, 2));
		JPanel reVoltPan = new JPanel(new BorderLayout());
		JPanel imVoltPan = new JPanel(new BorderLayout());
		JLabel reVoltLab = new JLabel("Re  ", JLabel.CENTER);
		JLabel imVoltLab = new JLabel("Im  ", JLabel.CENTER);
		reVoltSlider = new JSlider(JSlider.VERTICAL, VMIN, VMAX, VINIT);
		imVoltSlider = new JSlider(JSlider.VERTICAL, -VMAX, VMAX, 0);
		reVoltSlider.addChangeListener(this);
		imVoltSlider.addChangeListener(this);
		reVoltPan.add(reVoltLab, BorderLayout.NORTH);
		reVoltPan.add(reVoltSlider, BorderLayout.CENTER);
		imVoltPan.add(imVoltLab, BorderLayout.NORTH);
		imVoltPan.add(imVoltSlider, BorderLayout.CENTER);
		voltPanel.add(reVoltPan);
		voltPanel.add(imVoltPan);
		voltPanel.setBorder(BorderFactory.createTitledBorder(lowEtched,
				"Voltage", TitledBorder.CENTER, TitledBorder.TOP));
		voltPanel.setPreferredSize(new Dimension(60, 130));

		JPanel main = new JPanel(new BorderLayout());
		main.setBackground(Color.white);
		main.add(ratio, BorderLayout.NORTH);
		main.add(diagram, BorderLayout.CENTER);
		main.add(impPanel, BorderLayout.SOUTH);

		JPanel higherMain = new JPanel(new BorderLayout());
		higherMain.add(main, BorderLayout.CENTER);
		higherMain.add(voltPanel, BorderLayout.WEST);
		// higherMain.setBorder( BorderFactory.createLineBorder( Color.black )
		// );

		Container container = getContentPane();
		container.setLayout(new BorderLayout(0, 10));
		container.setBackground(Color.white);
		container.add(ratioSlider, BorderLayout.NORTH);
		container.add(higherMain, BorderLayout.CENTER);
		container.add(checkBox, BorderLayout.SOUTH);
	}

	/* 
	 * Specify GUI action when changing JRadioButton
	 * 
	 * (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {
		JRadioButton temp = (JRadioButton) e.getSource();
		cardManager.show(diagram, temp.getText());
	}

	/* 
	 * Specify GUI actions when changing JSlider's 
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		DecimalFormat twoDigits = new DecimalFormat("0.00");
		Object temp = e.getSource();
		if (temp == ratioSlider) {
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			computeImpedanceTransform();
			
		} else if (temp == reLoadSlider) {
			loadValue[0].setReal((double) reLoadSlider.getValue());
			loadNormal.setValue(loadValue);
			loadLoad.setValue(loadValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			
		} else if (temp == reSourceSlider) {
			sourceValue[0].setReal((double) reSourceSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			
		} else if (temp == reVoltSlider) {
			sourceValue[1].setReal((double) reVoltSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			
		} else if (temp == imLoadSlider) {
			loadValue[0].setImaginary((double) imLoadSlider.getValue());
			loadNormal.setValue(loadValue);
			loadLoad.setValue(loadValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			
		} else if (temp == imSourceSlider) {
			sourceValue[0].setImaginary((double) imSourceSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
			
		} else if (temp == imVoltSlider) {
			sourceValue[1].setImaginary((double) imVoltSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			computeImpedanceTransform();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[ratioSlider.getValue()] + "            " + "Vs: "
					+ twoDigits.format(vs));
		}

	}

	/**
	 * Compute impedance transform and update the labels.
	 * Compute load impedance in Sourcve view and source impedance in Load view,
	 * based on the current circuit parameters.
	 * Update the corresponding labels accordingly.
	 */
	private void computeImpedanceTransform() {
		double ratio = ratioMap[ratioSlider.getValue()];

		Complex[] tempValueSource = { Complex.multiply(loadValue[0], ratio
				* ratio) }; // Source view
		loadSource.setValue(tempValueSource);
		Complex[] tempValueLoad = {
				Complex.multiply(sourceValue[0], 1.0 / (ratio * ratio)),
				Complex.multiply(sourceValue[1], 1.0 / ratio) };
		sourceLoad.setValue(tempValueLoad);
	}

	/**
	 * Pure physics: compute voltage at primary widing (Vp) and secondary winding (Vs)
	 * based on the current circuit parameters (voltage source, source impedance, load impedance)
	 */
	private void calculateVpVs() {
		double ratio = ratioMap[ratioSlider.getValue()];
		
		Complex load = Complex.multiply(loadValue[0], ratio * ratio);
		Complex totalImp = Complex.add(load, sourceValue[0]);
		
		vp = sourceValue[1].getMagnitude() * load.getMagnitude() / totalImp.getMagnitude();
		vs = vp / ratioMap[ratioSlider.getValue()];
	}

}
