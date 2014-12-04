package my.vip.applets.impedance;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

class CustomLabel extends JLabel {
	private String[] label;
	private Complex[] value;
	private int[] xPos;
	private int[] yPos;
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
		label = new String[number];
		value = new Complex[number];
		for (int i = 0; i < value.length; i++) {
			value[i] = new Complex();
		}
		xPos = new int[number];
		yPos = new int[number];
	}

	public CustomLabel(Icon image, int num1, String[] label1, Complex[] value1,
			int[] xPos1, int[] yPos1) {
		this(image, num1);
		setLabel(label1);
		setValue(value1);
		setxPos(xPos1);
		setyPos(yPos1);
	}

	public void setLabel(String[] label1) {
		for (int i = 0; i < label.length; i++) {
			label[i] = label1[i];
		}
		repaint();
	}

	public void setValue(Complex[] value1) {
		for (int i = 0; i < value.length; i++) {
			value[i].setRe(value1[i].getRe());
			value[i].setIm(value1[i].getIm());
		}
		repaint();
	}

	public void setxPos(int[] xPos1) {
		for (int i = 0; i < xPos.length; i++) {
			xPos[i] = xPos1[i];
		}
		repaint();
	}

	public void setyPos(int[] yPos1) {
		for (int i = 0; i < yPos.length; i++) {
			yPos[i] = yPos1[i];
		}
		repaint();
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);

		gr.setFont(new Font("Arial", Font.PLAIN, 10));
		for (int i = 0; i < number; i++) {
			gr.setColor(Color.black);
			gr.drawString(label[i] + " = " + value[i].toString(), xPos[i],
					yPos[i]);
		}
	}

}
