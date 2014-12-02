package my.vip.applets.lenzlaw;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

/**
 * For displaying images of the magnet and a black metal ring.
 * For computing and displaying the "magnetic field" by the magnet.
 * 
 * @author tdongsi
 *
 */
class MyPanel extends JPanel {
	/**
	 * Fixed width of the panel
	 */
	public final static int WIDTH = 500;
	/**
	 * Fixed height of the panel
	 */
	public final static int HEIGHT = 500;
	
	/**
	 * The mangnet object to be drawn in the panel
	 */
	Magnet magnet;
	/**
	 * The top and bottom of the black ring
	 */
	Ring top, bottom;
	/**
	 * The fixed position of the ring (only size is changed)
	 */
	private final int RING_POSITION = 75;
	
	int xTemp = 0, yTemp = 0;
	Image screen;
	Graphics gr;
	int w, h;

	/**
	 * For counting flux lines through the ring and its difference
	 */
	private int currentFlux, lastFlux;
	/**
	 * Change in magnetic flux lines.
	 */
	private int fluxDiff;
	
	// Internal limits
	final int yLimitTop = RING_POSITION + Ring.RING_HEIGHT;
	final int yLimitBottom = MyPanel.HEIGHT - Magnet.HEIGHT;
	final int xLimitRight = MyPanel.WIDTH - Magnet.WIDTH;
	private int leftLim, rightLim, topLim, bottomLim, safeDis;

	// add arrows and electrons
	private int alpha;
	/**
	 * Array of yellow electrons
	 */
	protected Electron current[];
	
	/**
	 * Array of blue arrows running in circle. 
	 */
	protected Arrow arrow[];

	public MyPanel() {
		magnet = new Magnet( ( MyPanel.WIDTH - Magnet.WIDTH ) / 2, (MyPanel.HEIGHT - Magnet.HEIGHT) / 2,
				Magnet.WIDTH, Magnet.HEIGHT );
		top = new Ring( "back", RING_POSITION - Ring.RING_HEIGHT, MyPanel.WIDTH );
		bottom = new Ring( "front", RING_POSITION, MyPanel.WIDTH);
		currentFlux = 0;
		lastFlux = 0;
		alpha = 0;
		
		current = new Electron[80];
		for ( int i = 0; i < current.length; i++ ) {
			current[i] = new Electron( MyPanel.WIDTH/2, RING_POSITION, Ring.RING_WIDTH/2 -10, Ring.RING_HEIGHT-10);
		}
		
		arrow = new Arrow[4];
		for ( int i = 0; i < arrow.length; i++ ) {
			arrow[i] = new Arrow( MyPanel.WIDTH/2 + Ring.RING_WIDTH/ (i>=2? 2 : -2), RING_POSITION, 50, 50, 90 + 180 * (i%2));
		}

		leftLim = bottom.x + 15;
		rightLim = bottom.x + bottom.width - 15;
		topLim = bottom.y - magnet.getSpacingWidth();
		bottomLim = bottom.y + magnet.getSpacingWidth();
		safeDis = 5 * magnet.getSpacingWidth();

		setPreferredSize( new Dimension (MyPanel.WIDTH, MyPanel.HEIGHT) );

		addMouseListener(
				new MouseAdapter() {
					public void mousePressed( MouseEvent e ) {
						xTemp = e.getX();
						yTemp = e.getY();
					}

					public void mouseReleased( MouseEvent e ) {
						alpha = 0;
						repaint();
					}
				}
				);

		addMouseMotionListener(
				new MouseMotionAdapter() {
					public void mouseMoved( MouseEvent e ) {
						if ( magnet.contains( e.getX(), e.getY() ) ) {
							setCursor( new Cursor( Cursor.HAND_CURSOR ) );
						} else {
							setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) );
						}
					}

					public void mouseDragged( MouseEvent e ) {
						int i = e.getX();
						int j = e.getY();
						int xTrans, yTrans;

						if (magnet.contains( i, j )){
							xTrans = i - xTemp;
							yTrans = j - yTemp;

							if ( magnet.y < yLimitTop ) {
								yTrans = 10;
							}
							if ( magnet.y > yLimitBottom ) {
								yTrans = -10;
							}
							if ( magnet.x > xLimitRight ) {
								xTrans = -10;
							}
							if ( magnet.x < 0 ) {
								xTrans = 10;
							}

							magnet.x += xTrans;
							magnet.y += yTrans;
							for (int a = 0; a < magnet.getLineNumber(); a++) {
								for (int b = 0; b < magnet.dotInLine[a]; b++) {
									magnet.xTempAxis[a][b] = magnet.xAxis[a][b] + magnet.x;
									if ( magnet.xTempAxis[a][b] < 0 || magnet.xTempAxis[a][b] > MyPanel.WIDTH) {
										magnet.xTempAxis[a][b] = 0;
									}

									magnet.yTempAxis[a][b] = magnet.yAxis[a][b] + magnet.y;
									if ( magnet.yTempAxis[a][b] < 0 || magnet.yTempAxis[a][b] > MyPanel.HEIGHT) {
										magnet.yTempAxis[a][b] = 0;
									}
								}
							}

							alpha = 10;
							xTemp = i;
							yTemp = j;
							repaint();
						}
					}
				}
				);
	}

	/* 
	 * Overload to pain different rectangles in the JPanel 
	 * 
	 * (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		w = getWidth();
		h = getHeight();
		screen = createImage( w, h );
		gr = screen.getGraphics();

		top.paint(gr);
		magnet.paint(gr);

		if ( alpha != 0 ) {
			gr.setColor( Color.blue );
			gr.drawOval( (MyPanel.WIDTH - top.width)/2 - 50, RING_POSITION - 50, 100, 100);
			gr.drawOval( (MyPanel.WIDTH + top.width)/2 - 50, RING_POSITION - 50, 100, 100);

			for (int i = 0; i < arrow.length; i++ ) {
				arrow[i].translate( (isIncrease()? -1 : 1) * alpha, i < 2);
				arrow[i].paint(gr);
			}
		}

		bottom.paint(gr);

		if ( alpha != 0 ) {
			for (int i = 0; i < current.length; i++ ) {
				current[i].translate( (isIncrease()? 1: -1) * alpha);
				current[i].paint(gr);
			}
		}
		gr.setColor( Color.black );
		gr.drawRect( 0, 0, MyPanel.WIDTH - 1, MyPanel.HEIGHT - 1 );

		int i = calculateFlux();
		if ( currentFlux != i ) {
			lastFlux = currentFlux;
			currentFlux = i;
			fluxDiff = Math.abs(currentFlux - lastFlux);
		}
		g.drawImage( screen, 0, 0, this);
	}

	public int getFlux() {
		return currentFlux;
	}

	public int lastFlux() {
		return lastFlux;
	}

	public int fluxDiff() {
		return fluxDiff;
	}

	public boolean isIncrease() {
		return lastFlux < currentFlux;
	}

	private int add( int x1, int x2, int i ) {
		if ( x2 <= 0 ) {
			return 1;
		} else {
			if ( i < magnet.getLineNumber() / 2 ) {
				if ( x1 < x2 )
					return 1;
				else
					return -1;
			} else {
				if ( x1 < x2 )
					return -1;
				else
					return 1;
			}
		}
	}


	private int calculateFlux() {
		int flux = 0;
		int x1, x2;

		for ( int i = 0; i < magnet.getLineNumber(); i++ ) {

			x1 = x2 = 0;

			for ( int j = 0; j <  magnet.dotInLine[i]; j++ ) {
				if ( magnet.yTempAxis[i][j] < bottomLim &&
						magnet.yTempAxis[i][j] > topLim ) {
					x1 = magnet.xTempAxis[i][j];
					break;
				}
			}

			for ( int j = magnet.dotInLine[i] - 1; j >=  0; j-- ) {
				if ( magnet.yTempAxis[i][j] < bottomLim &&
						magnet.yTempAxis[i][j] > topLim ) {
					x2 = magnet.xTempAxis[i][j];
					if ( Math.abs( x1 - x2 ) > safeDis )
						break;
					else
						x2 = 0;
				}
			}

			boolean insidex1 = x1 > leftLim && x1 < rightLim;
			boolean insidex2 = x2 > leftLim && x2 < rightLim;

			if ( insidex1 && insidex2 ) {

			} else if ( !insidex1 && !insidex2 ) {

			} else if ( insidex1 && !insidex2 ) {
				flux += add( x1, x2, i);
			} else if ( !insidex1 && insidex2 ) {
				flux += add( x2, x1, i);
			}

		}

		return flux;
	}

	/**
	 * Update left and right limits
	 */
	public void updateLimits() {
		leftLim = bottom.x + 15;
		rightLim = bottom.x + bottom.width - 15;
	}

}
