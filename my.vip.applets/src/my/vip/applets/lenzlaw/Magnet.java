package my.vip.applets.lenzlaw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * For displaying the magnet
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
	private int dotInLine[];
	private int xAxis[][];
	private int yAxis[][];
	private int xTempAxis[][];
	private int yTempAxis[][];
	
	/**
	 * Position of the top left corner
	 */
	private int xTopLeft, yTopLeft;

	/**
	 * Graphic file for this magnet
	 */
	ImageIcon magnet;

	/**
	 * Initial position
	 */
	private int initX, initY;

	public Magnet(int x, int y, int width, int height) {
		super( x, y, width, height);
		int screenWidth = (int)(1.5 * MyPanel.WIDTH);
		int screenHeight = (int)(1.5 * MyPanel.HEIGHT);
		spacingWidth = 5;
		spacingHeight = 5;
		
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
		formLine();
	}

	/**
	 * Compute the coordinates of the flux lines.
	 * This code is ported from a C++ code.
	 * NOTE on June 2013: The mathematics in this method (from 2004) is beyond me now.
	 */
	private void formLine() {
		
		// Initialize
		double coef = 1.2D;
		int count = 20;
		int[] a = new int[ count ];
		int[] b = new int[ count ];
		double[] c = new double[ count ];
		
		for (int i = 0; i < count; i++) {
			if ( i < count/2 ) {
				a[ i ] = xTopLeft;
				b[ i ] = yTopLeft + (int)( (double)HEIGHT * i / (double)(count/2 - 1));
				c[ i ] = coef;
			} else {
				a[ i ] = xTopLeft + WIDTH;
				b[ i ] = yTopLeft + (int)( (double)HEIGHT * (i - count/2) / (double)( count/2 - 1));
				c[ i ] = -coef;
			}
		}
		
		// Computing the coordinates of flux lines
		for ( int i = 0; i < M; i++ ) {
			for (int j = 0; j < N; j++ ) {
				double d1 = 0.0;
				for (int k = 0; k < count; k++ ) {
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
						getxAxis()[i][m] = e - xTopLeft;
						getyAxis()[i][m] = j * spacingHeight - yTopLeft;
						getxTempAxis()[i][m] = getxAxis()[i][m] + initX;
						if ( getxTempAxis()[i][m] < 0 || getxTempAxis()[i][m] > MyPanel.WIDTH ) {
							getxTempAxis()[i][m] = 0;
						}
						getyTempAxis()[i][m] = getyAxis()[i][m] + initY;
						if ( getyTempAxis()[i][m] < 0 || getyTempAxis()[i][m] > MyPanel.HEIGHT ) {
							getyTempAxis()[i][m] = 0;
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
						getxAxis()[i][m] = j * spacingWidth - xTopLeft;
						getyAxis()[i][m] = e - yTopLeft;
						getxTempAxis()[i][m] = getxAxis()[i][m] + initX;
						if ( getxTempAxis()[i][m] < 0 || getxTempAxis()[i][m] > MyPanel.WIDTH ) {
							getxTempAxis()[i][m] = 0;
						}
						getyTempAxis()[i][m] = getyAxis()[i][m] + initY;
						if ( getyTempAxis()[i][m] < 0 || getyTempAxis()[i][m] > MyPanel.HEIGHT ) {
							getyTempAxis()[i][m] = 0;
						} 
					}
				}
			}
			getDotInLine()[i] = m;
		}	

	}

	/**
	 * Drawing the object
	 */
	public void paint( Graphics g ) {
		// draw the flux lines
		g.setColor( Color.blue );
		for (int i = 0; i < lineNumber; i++ ) {
			for (int j = 0; j < getDotInLine()[i]; j++) {
				g.drawLine( getxTempAxis()[i][j], getyTempAxis()[i][j], getxTempAxis()[i][j], getyTempAxis()[i][j] );
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

	public int getSpacingWidth() {
		return spacingWidth;
	}

	public int[] getDotInLine() {
		return dotInLine;
	}

	public int[][] getxTempAxis() {
		return xTempAxis;
	}

	public int[][] getyTempAxis() {
		return yTempAxis;
	}

	public int[][] getxAxis() {
		return xAxis;
	}

	public int[][] getyAxis() {
		return yAxis;
	}

	public int getLineNumber() {
		return lineNumber;
	}

}
