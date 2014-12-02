package my.vip.applets.lenzlaw;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * For drawing the direction of magnetic field, caused by induced currents.
 * The arrow is moving around a circle which fits into a rectangle that this class extends.
 * 
 * @author tdongsi
 *
 */
@SuppressWarnings("serial")
public class Arrow extends Rectangle
{
	/**
	 * Relative position of the arrow on the circle, defined as angle
	 */
	private double radian;
	/**
	 * The length of the arrow
	 */
	private int length;
	/**
	 * x position of the circle
	 */
	private int xCenter;
	/**
	 * y position of the circle
	 */
	private int yCenter;
	/**
	 * x coordinates of the arrow's vertexes
	 */
	private int xVertexes[];
	/**
	 * y coordinates of the arrow's vertexes
	 */
	private int yVertexes[];

	public Arrow(int x, int y, int width, int height, int angle)
	{
		super(x, y, width, height);
		xVertexes = new int[3];
		yVertexes = new int[3];
		xCenter = x;
		yCenter = y;
		translate(angle, true);
	}

	/**
	 * @param angle the arrow position on the circle, as an angle in degree.
	 * @param flag indicating direction of the arrow poiting and movement
	 */
	public void translate(int angle, boolean flag)
	{
		if(flag)
			angle = -angle;
		radian += ( angle * Math.PI ) / 180D;
		x = (int)( xCenter + Math.cos(radian) * width);
		y = (int)( yCenter + Math.sin(radian) * height);
		length = angle >= 0 ? -1 : 1;
	}

	/**
	 * Paint a triangle that represents the arrow
	 * The tip of the arrow is at (x,y)
	 */
	public void paint(Graphics g)
	{
		xVertexes[0] = x;
		yVertexes[0] = y;
		xVertexes[1] = (int)( xCenter + Math.cos(radian + 0.2D * length) * (width + 4));
		yVertexes[1] = (int)( yCenter + Math.sin(radian + 0.2D * length) * (height + 4));
		xVertexes[2] = (int)( xCenter + Math.cos(radian + 0.2D * length) * (width - 4));
		yVertexes[2] = (int)( yCenter + Math.sin(radian + 0.2D * length) * (height - 4));
		g.fillPolygon(xVertexes, yVertexes, 3);
	}

	/**
	 * @param w
	 */
	public void ringChanged( int w ) {
		xCenter = MyPanel.WIDTH / 2 - w / 2;
	}

}
