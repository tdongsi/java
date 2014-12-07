package my.vip.applets.impedance;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Extend JLabel to display multiple Complex numbers on it.
 * 
 * @author tdongsi
 *
 */
class CustomLabel extends JLabel {
	/**
	 * Text and positions associated with each complex numbers
	 */
	private LabelData[] label;
	/**
	 * Complex numbers.
	 */
	private Complex[] value;
	/**
	 * Number of complex numbers.
	 */
	private int number;

	public CustomLabel() {
		super();
	}

	public CustomLabel(Icon image) {
		super(image);
	}

	public CustomLabel(Icon image, int iNumber) {
		super(image);
		this.number = iNumber;
		value = new Complex[number];
		for (int i = 0; i < value.length; i++) {
			value[i] = new Complex();
		}
	}

	public CustomLabel(Icon image, int iNumber, LabelData[] labels, Complex[] values) {
		this(image, iNumber);
		setLabel(labels);
		setValue(values);
	}

	/**
	 * Update complex numbers to be displayed
	 */
	public void setValue(Complex[] iValue) {
		for (int i = 0; i < value.length; i++) {
			value[i].setReal(iValue[i].getReal());
			value[i].setImaginary(iValue[i].getImaginary());
		}
		repaint();
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);

		gr.setFont(new Font("Arial", Font.PLAIN, 10));
		for (int i = 0; i < number; i++) {
			gr.setColor(Color.black);
			gr.drawString(label[i].text + " = " + value[i].toString(), label[i].xPos, label[i].yPos);
		}
	}
	
	private void setLabel(LabelData[] label) {
		this.label = label;
		repaint();
	}
	
	/**
	 * Text and positions associated with each complex numbers
	 */
	static class LabelData {
		final private String text;
		final private int xPos;
		final private int yPos;
		
		/**
		 * @param label
		 * @param xPos
		 * @param yPos
		 */
		public LabelData(String label, int xPos, int yPos) {
			super();
			this.text = label;
			this.xPos = xPos;
			this.yPos = yPos;
		}
		
	}

}
