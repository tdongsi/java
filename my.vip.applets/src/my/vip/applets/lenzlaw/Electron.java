package my.vip.applets.lenzlaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * For drawing a yellow electron moving in circle
 * The circle fits into the rectangle that this class extends.
 * 
 * @author tdongsi
 *
 */
public class Electron extends Rectangle {
	private int xc, yc;
	private double radian;

	public Electron(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		xc = x;
		yc = y;
		translate((int)(Math.random() * 360D));
	}

	/**
	 * Moving the electron in approximate ellipse, bounded by the rectangle this class extends.
	 * 
	 * @param degree angle in degree
	 */
	public void translate(int degree)
	{
		radian += ( degree * Math.PI ) / 180D;
		x = (int)( xc + Math.cos(radian) * width);
		y = (int)( yc + Math.sin(radian) * height);
	}

	public void paint(Graphics g) {
		g.setColor( Color.yellow );
		g.fillOval( x - 2, y - 2, 4, 4);
	}

	public void ringChanged( int w ) {
		width = w / 2 - 8;
	}

}
