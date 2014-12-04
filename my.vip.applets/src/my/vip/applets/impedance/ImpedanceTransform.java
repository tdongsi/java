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


class ImpedanceTransform extends JApplet implements ItemListener,
		ChangeListener {
	private final String[] sourceLabel = { "R", "V" };
	private Complex[] sourceValue = { new Complex(1000.0), new Complex(1000.0) };
	private final int[] sourcexPos = { 45, 35 };
	private final int[] sourceyPos = { 35, 70 };
	private final String[] loadLabel = { "R" };
	private Complex[] loadValue = { new Complex(1000.0) };
	private final int[] loadxPos = { 20 };
	private final int[] loadyPos = { 70 };
	private final String[] label = { "View from source", "Normal view",
			"View from load" };
	private final boolean[] buttonSelect = { false, true, false };
	private final int RMIN = 0, RMAX = 18, RINIT = 9;
	private final int IMIN = 50, IMAX = 1000, IINIT = 1000;
	private final int VMIN = 0, VMAX = 1000, VINIT = 1000;
	private final String[] ratioLabel = { "1:10", "1:9", "1:8", "1:7", "1:6",
			"1:5", "1:4", "1:3", "1:2", "1:1", "2:1", "3:1", "4:1", "5:1",
			"6:1", "7:1", "8:1", "9:1", "10:1" };
	private final int[] ratioMap = { -10, -9, -8, -7, -6, -5, -4, -3, -2, 1, 2,
			3, 4, 5, 6, 7, 8, 9, 10 };

	CustomLabel sourceNormal, transNormal, loadNormal;
	CustomLabel sourceSource, loadSource;
	CustomLabel sourceLoad, loadLoad;
	ImageIcon sourceIcon, transIcon, loadIcon;
	JPanel diagram;
	CardLayout cardManager;
	JRadioButton[] radioButton = new JRadioButton[3];
	ButtonGroup radioGroup;

	JSlider ratioSlider, reSourceSlider, reLoadSlider, reVoltSlider;
	JSlider imSourceSlider, imLoadSlider, imVoltSlider;
	JLabel ratio;
	double vp, vs;
	int rSliderValue;

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
		rSliderValue = RINIT;
		diagram = new JPanel();
		cardManager = new CardLayout();
		diagram.setLayout(cardManager);

		JPanel normal = new JPanel(new GridLayout(1, 3));
		normal.setBackground(Color.white);
		sourceIcon = createImageIcon("images/Source.jpg");
		transIcon = createImageIcon("images/Transformer.jpg");
		loadIcon = createImageIcon("images/Load.jpg");
		sourceNormal = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue,
				sourcexPos, sourceyPos);
		transNormal = new CustomLabel(transIcon);
		loadNormal = new CustomLabel(loadIcon, 1, loadLabel, loadValue,
				loadxPos, loadyPos);
		normal.add(sourceNormal);
		normal.add(transNormal);
		normal.add(loadNormal);

		JPanel fromSource = new JPanel(new GridLayout(1, 3));
		fromSource.setBackground(Color.white);
		sourceSource = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue,
				sourcexPos, sourceyPos);
		loadSource = new CustomLabel(loadIcon, 1, loadLabel, loadValue,
				loadxPos, loadyPos);
		fromSource.add(sourceSource);
		fromSource.add(loadSource);
		fromSource.add(Box.createHorizontalStrut(100));

		JPanel fromLoad = new JPanel(new GridLayout(1, 3));
		fromLoad.setBackground(Color.white);
		sourceLoad = new CustomLabel(sourceIcon, 2, sourceLabel, sourceValue,
				sourcexPos, sourceyPos);
		loadLoad = new CustomLabel(loadIcon, 1, loadLabel, loadValue, loadxPos,
				loadyPos);
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

	public void itemStateChanged(ItemEvent e) {
		JRadioButton temp = (JRadioButton) e.getSource();
		cardManager.show(diagram, temp.getText());
	}

	public void stateChanged(ChangeEvent e) {
		DecimalFormat twoDigits = new DecimalFormat("0.00");
		Object temp = e.getSource();
		if (temp == ratioSlider) {
			rSliderValue = ratioSlider.getValue();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
			updateLabel();
		} else if (temp == reLoadSlider) {
			loadValue[0].setRe((double) reLoadSlider.getValue());
			loadNormal.setValue(loadValue);
			loadLoad.setValue(loadValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		} else if (temp == reSourceSlider) {
			sourceValue[0].setRe((double) reSourceSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		} else if (temp == reVoltSlider) {
			sourceValue[1].setRe((double) reVoltSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		} else if (temp == imLoadSlider) {
			loadValue[0].setIm((double) imLoadSlider.getValue());
			loadNormal.setValue(loadValue);
			loadLoad.setValue(loadValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		} else if (temp == imSourceSlider) {
			sourceValue[0].setIm((double) imSourceSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		} else if (temp == imVoltSlider) {
			sourceValue[1].setIm((double) imVoltSlider.getValue());
			sourceNormal.setValue(sourceValue);
			sourceSource.setValue(sourceValue);
			updateLabel();
			calculateVpVs();
			ratio.setText("Vp: " + twoDigits.format(vp) + "            "
					+ ratioLabel[rSliderValue] + "            " + "Vs: "
					+ twoDigits.format(vs));
		}

	}

	private void updateLabel() {
		double ratio = stepRatio();

		Complex[] tempValueSource = { Complex.multiply(loadValue[0], ratio
				* ratio) }; // Source view
		loadSource.setValue(tempValueSource);
		Complex[] tempValueLoad = {
				Complex.multiply(sourceValue[0], 1.0 / (ratio * ratio)),
				Complex.multiply(sourceValue[1], 1.0 / ratio) };
		sourceLoad.setValue(tempValueLoad);
	}

	private double stepRatio() {
		int i = ratioMap[rSliderValue];
		if (i > 0) {
			return (double) i;
		} else {
			return -1.0 / (double) i;
		}
	}

	private void calculateVpVs() {
		double ratio = stepRatio();
		Complex load = Complex.multiply(loadValue[0], ratio * ratio);
		Complex totalImp = Complex.add(load, sourceValue[0]);
		vp = sourceValue[1].getMag() * load.getMag() / totalImp.getMag();
		vs = vp / stepRatio();
	}

}
