package my.vip.applets.dcmotor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

class CustomLabel extends JLabel {
	private int iconNum, state;
	private final int x1[] = { 12, 23, 19 };
	private final int y1[] = { 8, 1, 15 };
	private final int x2[] = { 106, 105, 115 };
	private final int y2[] = { 9, 23, 14 };
	private final int x3[] = { 16, 16, 24 };
	private final int y3[] = { 0, 12, 4 };
	private final int x4[] = { 102, 110, 110 };
	private final int y4[] = { 20, 10, 24 };

	public CustomLabel() {
		super();
		iconNum = 0;
		state = 0;
	}

	public CustomLabel(Icon icon) {
		super(icon);
		iconNum = 0;
		state = 0;
	}

	public void setNum(int num) {
		iconNum = num;
		repaint();
	}

	public void setState(int state1) {
		state = state1;
		repaint();
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.setColor(Color.blue);
		if (iconNum != 5 && iconNum != 12) {
			if (state == 0) {
				gr.fillPolygon(x1, y1, 3);
				gr.fillPolygon(x2, y2, 3);
			} else {
				gr.fillPolygon(x3, y3, 3);
				gr.fillPolygon(x4, y4, 3);
			}
		}
	}

}
