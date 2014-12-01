package my.vip.applets.lenzlaw2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

/**
 * This is the older version of my.vip.applets.lenzlaw.Magnet
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
class Magnet extends Rectangle {
	final static int WIDTH = 500, HEIGHT = 600;
	final static int magnetHeight = 210, magnetWidth = 105;
	protected int screenWidth, screenHeight;
	protected int spacingOne, spacingTwo;
	protected int M, N;
	protected double array[][];
	protected double coef;
	protected int a[], b[];
	protected int count;
	protected double c[];
	protected double lines[] = { 0.0, 2.5, 5.0, 7.5, 10.0, 12.5, 15.0, 17.5, 20.0, 22.5, 25.0, 27.5, 30.0, 
			-30.0, -27.5, -25.0, -22.5, -20.0, -17.5, -15.0, -12.5, -10.0, -7.5, -5.0, -2.5 };
	protected int lineNumber;
	protected int dotInLine[];
	protected int xAxis[][];
	protected int yAxis[][];
	protected int halfWidth = magnetWidth / 2;
	protected int xTopLeft, yTopLeft;

	ImageIcon magnet;

	protected int xDiff, yDiff;

	public Magnet(int x, int y, int width, int height) {
		super( x, y, width, height);
		screenWidth = (int)(1.5 * WIDTH);
		screenHeight = (int)(1.5 * HEIGHT);
		spacingOne = 5;
		spacingTwo = 5;
		M = screenWidth / spacingOne;
		N = screenHeight / spacingTwo;
		array = new double[M][N];
		coef = 1.2D;
		count = 20;
		a = new int[ count ];
		b = new int[ count ];
		c = new double[ count ];
		lineNumber = lines.length;
		dotInLine = new int[ lineNumber ];
		xAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		yAxis = new int[ lineNumber ][ ( M-1 ) * ( N-1 )];
		xTopLeft = screenWidth/2 - halfWidth;
		yTopLeft = (screenHeight - magnetHeight)/2;
		magnet = createImageIcon( "images/magnet.jpg" );
		xDiff = super.x - xTopLeft;
		yDiff = super.y - yTopLeft;
		initialize();
		formLine();
	}

	protected void initialize() {
		for (int i = 0; i < count; i++) {
			if ( i < count/2 ) {
				a[ i ] = xTopLeft;
				b[ i ] = yTopLeft + (int)( (double)magnetHeight * i / (double)(count/2 - 1));
				c[ i ] = coef;
			} else {
				a[ i ] = xTopLeft + magnetWidth;
				b[ i ] = yTopLeft + (int)( (double)magnetHeight * (i - count/2) / (double)( count/2 - 1));
				c[ i ] = -coef;
			}
		}
	}

	protected void formLine() {
		for ( int i = 0; i < M; i++ ) {
			for (int j = 0; j < N; j++ ) {
				double d1 = 0.0;
				for (int k = 0; k < count; k++ ) {
					double d2 = i * spacingOne - a[ k ];
					double d3 = j * spacingTwo - b[ k ];
					d1 += (double)2 * c[ k ] * Math.log( d2*d2 + d3*d3);
				}
				array[i][j] = d1;
			}
		}

		for ( int i = 0; i < lineNumber; i++ ) {
			int m = 0;
			for ( int j = 0; j < N; j++ ) {
				for ( int k = 0; k < M-1; k++ ) {
					double d = (lines[i] - array[k][j]) / (array[k+1][j] - array[k][j]);
					int e = k * spacingOne + (int)( d * spacingOne);
					if ( k * spacingOne < e && e <= (k+1) * spacingOne ) {
						m++;
						xAxis[i][m] = e + xDiff;
						yAxis[i][m] = j * spacingTwo + yDiff;
					}
				}
			}

			for ( int j = 0; j < M; j++ ) {
				for ( int k = 0; k < N - 1; k++) {
					double d = ( lines[i] - array[j][k] ) / ( array[j][k+1] - array[j][k]);
					int e = k * spacingTwo + (int) ( d * spacingTwo);
					if ( k * spacingTwo < e && e <= (k+1) * spacingTwo ) {
						m++;
						xAxis[i][m] = j * spacingOne + xDiff;
						yAxis[i][m] = e + yDiff;
					}
				}
			}
			dotInLine[i] = m;
		}	

	}

	public void paint( Graphics g ) {
		g.setColor( Color.blue );
		for (int i = 0; i < lineNumber; i++ ) {
			for (int j = 0; j < dotInLine[i]; j++) {
				g.drawLine( xAxis[i][j], yAxis[i][j], xAxis[i][j], yAxis[i][j] );
			}
		}

		g.drawImage( magnet.getImage(), x, y, magnetWidth, magnetHeight, null);
	}

	private ImageIcon createImageIcon( String path ) {
		java.net.URL imgURL = LenzLaw2.class.getResource( path );
		if ( imgURL != null ) {
			return new ImageIcon( imgURL );
		} else {
			System.err.println( "Couldn't find file: " + path );
			return null;
		}
	}
}
