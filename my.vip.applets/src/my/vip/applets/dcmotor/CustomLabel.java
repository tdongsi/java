package my.vip.applets.dcmotor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.JLabel;

/**
 * Customized labels to draw the arrow heads indicating electrical current's direction,
 * in addition to drawing the circuit as an Icon.
 * 
 * @author tdongsi
 *
 */
class CustomLabel extends JLabel {
	
	/**
	 * Number that indicates frame number of the DC motor.
	 * At frames 5 and 12, the brushes are not touching due to the gap in commutator.
	 * For scientific rigor, a current is not present at frame 5 and 12.
	 */
	private int iconNum; 
	
	/**
	 * Variable that indicates the direction of the DC current.
	 */
	private int state;
	
	/**
	 * X and Y coordinates of the first triangle for the arrow head.
	 * Left arrow head when the current is clockwise.
	 */
	private final int x1[] = { 12, 23, 19 };
	private final int y1[] = { 8, 1, 15 };
	
	/**
	 * X and Y coordinates of the second triangle for the arrow head.
	 * Right arrow head when the current is clockwise.
	 */
	private final int x2[] = { 106, 105, 115 };
	private final int y2[] = { 9, 23, 14 };
	
	/**
	 * X and Y coordinates of the third triangle for the arrow head.
	 * Left arrow head when the current is counter-clockwise.
	 */
	private final int x3[] = { 16, 16, 24 };
	private final int y3[] = { 0, 12, 4 };
	
	/**
	 * X and Y coordinates of the forth triangle for the arrow head.
	 * Right arrow head when the current is counter-clockwise.
	 */
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
		// Repaint the component, based on the icon.
		super.paintComponent(gr);
		
		// Paint the blue arrow heads
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
