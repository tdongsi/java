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
	double angle;
	/**
	 * The length of the arrow
	 */
	int length;
	/**
	 * x position of the circle
	 */
	int xCenter;
	/**
	 * y position of the circle
	 */
	int yCenter;
	/**
	 * x coordinates of the arrow's vertexes
	 */
	int xVertexes[];
	/**
	 * y coordinates of the arrow's vertexes
	 */
	int yVertexes[];

	public Arrow(int x, int y, int width, int height, int i1)
	{
		super(x, y, width, height);
		xVertexes = new int[3];
		yVertexes = new int[3];
		xCenter = x;
		yCenter = y;
		translate(i1, true);
	}

	/**
	 * @param rad the arrow position on the circle, as an angle in degree.
	 * @param flag indicating direction of the arrow poiting and movement
	 */
	public void translate(int rad, boolean flag)
	{
		if(flag)
			rad = -rad;
		angle += ( rad * Math.PI ) / 180D;
		x = (int)( xCenter + Math.cos(angle) * width);
		y = (int)( yCenter + Math.sin(angle) * height);
		length = rad >= 0 ? -1 : 1;
	}

	/**
	 * Paint a triangle that represents the arrow
	 * The tip of the arrow is at (x,y)
	 */
	public void paint(Graphics g)
	{
		xVertexes[0] = x;
		yVertexes[0] = y;
		xVertexes[1] = (int)( xCenter + Math.cos(angle + 0.2D * length) * (width + 4));
		yVertexes[1] = (int)( yCenter + Math.sin(angle + 0.2D * length) * (height + 4));
		xVertexes[2] = (int)( xCenter + Math.cos(angle + 0.2D * length) * (width - 4));
		yVertexes[2] = (int)( yCenter + Math.sin(angle + 0.2D * length) * (height - 4));
		g.fillPolygon(xVertexes, yVertexes, 3);
	}

	/**
	 * @param w
	 */
	public void ringChanged( int w ) {
		xCenter = MyPanel.WIDTH / 2 - w / 2;
	}

}
