package my.vip.applets.lenzlaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * For displaying the black metal ring
 * 
 * @author tdongsi
 *
 */
@SuppressWarnings("serial")
public class Ring extends Rectangle {
	/**
	 * initial width
	 */
	public static final int RING_WIDTH = 154;
	
	/**
	 * intial height
	 */
	public static final int RING_HEIGHT = 25;
	
	/**
	 * The image of the black metal ring
	 */
	private ImageIcon ringImage;
	
	/**
	 * Information about the width of the screen
	 */
	private int screenWidth;

	/**
	 * Object constructor
	 * 
	 * @param filename Name of the image file
	 * @param pos Initial position of the image
	 * @param screenWidth The canvas width
	 */
	public Ring( String filename, int pos, int screenWidth ) {
		super( (screenWidth - RING_WIDTH) / 2, pos, RING_WIDTH, RING_HEIGHT );
		this.ringImage = createImageIcon( "images/" + filename + ".gif" );
		this.screenWidth = screenWidth;
	}

	public void paint( Graphics g ) {
		g.setColor( Color.green );
		g.drawImage( ringImage.getImage(), x, y, width, height, null );
		//	g.drawLine( x + 15, y, x + width - 15, y );
	}

	private ImageIcon createImageIcon( String path ) {
		java.net.URL imgURL = LenzLaw.class.getResource( path );
		if ( imgURL != null ) {
			return new ImageIcon( imgURL );
		} else {
			System.err.println( "Couldn't find file: " + path );
			return null;
		}
	}

	public void setWidth( int w ) {
		width = w;
		x = ( screenWidth - w ) / 2;
	}

}
