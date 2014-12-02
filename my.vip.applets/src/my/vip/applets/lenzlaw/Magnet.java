package my.vip.applets.lenzlaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * For displaying the magnet plus the lines representing the magnetic field.
 * 
 * @author tdongsi
 *
 */
class Magnet extends Rectangle {
	/**
	 * Recommended height of the magnet
	 */
	public final static int HEIGHT = 180;
	/**
	 * Recommended width of the magnet
	 */
	public final static int WIDTH = 90;
	
	private double lines[] = { 0.0, 2.5, 5.0, 7.5, 10.0, 12.5, 15.0, 17.5, 20.0, 22.5, 25.0, 
			-25.0, -22.5, -20.0, -17.5, -15.0, -12.5, -10.0, -7.5, -5.0, -2.5 };
	private int lineNumber = lines.length;
	
	private int spacingWidth, spacingHeight;
	private int M, N;
	private double temp[][];
	
	// Arrays for plotting the flux lines
	int dotInLine[];
	int xAxis[][];
	int yAxis[][];
	int xTempAxis[][];
	int yTempAxis[][];
	
	/**
	 * Position of the top left corner
	 */
	private int xTopLeft, yTopLeft;

	/**
	 * Graphic file for this magnet
	 */
	private ImageIcon magnet;

	/**
	 * Initial position
	 */
	private int initX, initY;

	/**
	 * Constructor with specified position (x, y) and dimension (width, height)
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Magnet(int x, int y, int width, int height) {
		super( x, y, width, height);
		int screenWidth = (int)(1.5 * MyPanel.WIDTH);
		int screenHeight = (int)(1.5 * MyPanel.HEIGHT);
		spacingWidth = 5;
		spacingHeight = 5;
		
		// For generating the magnetic flux lines
		M = screenWidth / spacingWidth;
		N = screenHeight / spacingHeight;
		temp = new double[M][N];
		dotInLine = new int[ lineNumber ];
		xAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		yAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		xTempAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		yTempAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		xTopLeft = screenWidth/2 - WIDTH / 2;
		yTopLeft = (screenHeight - HEIGHT)/2;
		magnet = createImageIcon( "images/magnet.jpg" );
		initX = super.x;
		initY = super.y;
		generateFluxLines();
	}

	/**
	 * Compute the coordinates of the flux lines.
	 * This code is ported from a C++ code.
	 * NOTE on June 2013: I could not recall the mathematics in this method (from 2004).
	 */
	private void generateFluxLines() {
		
		// Initialize
		double coef = 1.2D;
		int COUNT = 20;
		int[] a = new int[ COUNT ];
		int[] b = new int[ COUNT ];
		double[] c = new double[ COUNT ];
		
		for (int i = 0; i < COUNT; i++) {
			if ( i < COUNT/2 ) {
				a[ i ] = xTopLeft;
				b[ i ] = yTopLeft + (int)( (double)HEIGHT * i / (double)(COUNT/2 - 1));
				c[ i ] = coef;
			} else {
				a[ i ] = xTopLeft + WIDTH;
				b[ i ] = yTopLeft + (int)( (double)HEIGHT * (i - COUNT/2) / (double)( COUNT/2 - 1));
				c[ i ] = -coef;
			}
		}
		
		// Computing the coordinates of flux lines
		for ( int i = 0; i < M; i++ ) {
			for (int j = 0; j < N; j++ ) {
				double d1 = 0.0;
				for (int k = 0; k < COUNT; k++ ) {
					double d2 = i * spacingWidth - a[ k ];
					double d3 = j * spacingHeight - b[ k ];
					d1 += (double)2 * c[ k ] * Math.log( d2*d2 + d3*d3);
				}
				temp[i][j] = d1;
			}
		}

		for ( int i = 0; i < lineNumber; i++ ) {
			int m = 0;
			for ( int j = 0; j < N; j++ ) {
				for ( int k = 0; k < M-1; k++ ) {
					double d = (lines[i] - temp[k][j]) / (temp[k+1][j] - temp[k][j]);
					int e = k * spacingWidth + (int)( d * spacingWidth);
					if ( k * spacingWidth < e && e <= (k+1) * spacingWidth ) {
						m++;
						xAxis[i][m] = e - xTopLeft;
						yAxis[i][m] = j * spacingHeight - yTopLeft;
						xTempAxis[i][m] = xAxis[i][m] + initX;
						if ( xTempAxis[i][m] < 0 || xTempAxis[i][m] > MyPanel.WIDTH ) {
							xTempAxis[i][m] = 0;
						}
						yTempAxis[i][m] = yAxis[i][m] + initY;
						if ( yTempAxis[i][m] < 0 || yTempAxis[i][m] > MyPanel.HEIGHT ) {
							yTempAxis[i][m] = 0;
						} 
					}
				}
			}

			for ( int j = 0; j < M; j++ ) {
				for ( int k = 0; k < N - 1; k++) {
					double d = ( lines[i] - temp[j][k] ) / ( temp[j][k+1] - temp[j][k]);
					int e = k * spacingHeight + (int) ( d * spacingHeight);
					if ( k * spacingHeight < e && e <= (k+1) * spacingHeight ) {
						m++;
						xAxis[i][m] = j * spacingWidth - xTopLeft;
						yAxis[i][m] = e - yTopLeft;
						xTempAxis[i][m] = xAxis[i][m] + initX;
						if ( xTempAxis[i][m] < 0 || xTempAxis[i][m] > MyPanel.WIDTH ) {
							xTempAxis[i][m] = 0;
						}
						yTempAxis[i][m] = yAxis[i][m] + initY;
						if ( yTempAxis[i][m] < 0 || yTempAxis[i][m] > MyPanel.HEIGHT ) {
							yTempAxis[i][m] = 0;
						} 
					}
				}
			}
			dotInLine[i] = m;
		}	

	}

	/**
	 * Drawing the object
	 */
	public void paint( Graphics g ) {
		// draw the flux lines
		g.setColor( Color.blue );
		for (int i = 0; i < lineNumber; i++ ) {
			for (int j = 0; j < dotInLine[i]; j++) {
				g.drawLine( xTempAxis[i][j], yTempAxis[i][j], xTempAxis[i][j], yTempAxis[i][j] );
			}
		}

		// draw the magnet
		g.drawImage( magnet.getImage(), x, y, WIDTH, HEIGHT, null);
	}

	/**
	 * Get the image file from the given (relative) path
	 */
	private ImageIcon createImageIcon( String path ) {
		java.net.URL imgURL = LenzLaw.class.getResource( path );
		if ( imgURL != null ) {
			return new ImageIcon( imgURL );
		} else {
			System.err.println( "Couldn't find file: " + path );
			return null;
		}
	}

	/**
	 * Getter method
	 */
	public int getSpacingWidth() {
		return spacingWidth;
	}

	/**
	 * Getter method
	 */
	public int getLineNumber() {
		return lineNumber;
	}

}
