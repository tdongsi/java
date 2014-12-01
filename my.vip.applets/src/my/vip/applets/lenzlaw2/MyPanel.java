package my.vip.applets.lenzlaw2;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

import my.vip.applets.lenzlaw.Arrow;
import my.vip.applets.lenzlaw.Electron;
import my.vip.applets.lenzlaw.Ring;

/**
 * This is the older version of my.vip.applets.lenzlaw.MyPanel
 * 
 * @author dongsi.tuecuong@gmail.com
 *
 */
class MyPanel extends JPanel {
	protected Magnet magnet;
	protected Ring top, bottom;
	final int ringPos = 75;
	int xTemp = 0, yTemp = 0;
	Image screen;
	Graphics gr;
	int w, h;

	private int currentFlux, lastFlux; //count flux lines through the ring

	//add arrows and electrons
	private int alpha;
	protected Electron current[];
	protected Arrow arrow[];

	public MyPanel() {
		magnet = new Magnet( ( Magnet.WIDTH - Magnet.magnetWidth ) / 2, (Magnet.HEIGHT - Magnet.magnetHeight) / 2,
				Magnet.magnetWidth, Magnet.magnetHeight );
		top = new Ring( "back", ringPos - Ring.RING_HEIGHT, Magnet.WIDTH );
		bottom = new Ring( "front", ringPos, Magnet.WIDTH);
		currentFlux = 0;
		lastFlux = 0;
		alpha = 0;
		current = new Electron[80];
		arrow = new Arrow[4];

		for ( int i = 0; i < current.length; i++ ) {
			current[i] = new Electron( Magnet.WIDTH/2, ringPos, Ring.RING_WIDTH/2 -10, Ring.RING_HEIGHT-10);
		}

		for ( int i = 0; i < arrow.length; i++ ) {
			arrow[i] = new Arrow( Magnet.WIDTH/2 + Ring.RING_WIDTH/ (i>=2? 2 : -2), ringPos, 50, 50, 90 + 180 * (i%2));
		}

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
					public void mouseDragged( MouseEvent e ) {
						int i = e.getX();
						int j = e.getY();
						int xTrans, yTrans;

						if (magnet.contains( i, j )){
							xTrans = i - xTemp;
							yTrans = j - yTemp;

							if ( magnet.y < ringPos + Ring.RING_HEIGHT ) {
								yTrans = 10;
							}
							if ( magnet.y + Magnet.magnetHeight > Magnet.HEIGHT ) {
								yTrans = -10;
							}
							if ( magnet.x + Magnet.magnetWidth > Magnet.WIDTH ) {
								xTrans = -10;
							}
							if ( magnet.x < 0 ) {
								xTrans = 10;
							}

							magnet.x += xTrans;
							magnet.y += yTrans;
							for (int a = 0; a < magnet.lineNumber; a++) {
								for (int b = 0; b < magnet.dotInLine[a]; b++) {
									magnet.xAxis[a][b] += xTrans;
									magnet.yAxis[a][b] += yTrans;
								}
							}

							alpha = (xTrans != 0) ? Math.abs(xTrans): Math.abs(yTrans);
							xTemp = i;
							yTemp = j;
							repaint();
						}
					}
				}
				);
	}

	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		w = getSize().width;
		h = getSize().height;
		screen = createImage( w, h );
		gr = screen.getGraphics();

		top.paint(gr);
		magnet.paint(gr);

		if ( alpha != 0 ) {
			gr.setColor( Color.blue );
			gr.drawOval( (Magnet.WIDTH - top.width)/2 - 50, ringPos - 50, 100, 100);
			gr.drawOval( (Magnet.WIDTH + top.width)/2 - 50, ringPos - 50, 100, 100);

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

		int i = calculateFlux();
		if ( currentFlux != i ) {
			lastFlux = currentFlux;
			currentFlux = i;
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
		return Math.abs( currentFlux - lastFlux );
	}

	public boolean isIncrease() {
		return lastFlux < currentFlux;
	}

	private boolean inside( int x ) {
		return (x > bottom.x + 15) && ( x < bottom.x + bottom.width - 15 );
	}

	private int add( int x1, int x2, int i ) {
		if ( x2 <= 0 ) {
			return 1;
		} else {
			if ( i < magnet.lineNumber / 2 ) {
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

		for ( int i = 0; i < magnet.lineNumber; i++ ) {

			x1 = x2 = 0;

			for ( int j = 0; j <  magnet.dotInLine[i]; j++ ) {
				if ( magnet.yAxis[i][j] < bottom.y + magnet.spacingOne &&
						magnet.yAxis[i][j] > bottom.y - magnet.spacingOne ) {
					x1 = magnet.xAxis[i][j];
					break;
				}
			}

			for ( int j = magnet.dotInLine[i] - 1; j >=  0; j-- ) {
				if ( magnet.yAxis[i][j] < bottom.y + magnet.spacingOne &&
						magnet.yAxis[i][j] > bottom.y - magnet.spacingOne ) {
					x2 = magnet.xAxis[i][j];
					if ( Math.abs( x1 - x2 ) > 5 * magnet.spacingOne )
						break;
					else
						x2 = 0;
				}
			}

			if ( inside(x1) && inside(x2) ) {

			} else if ( !inside(x1) && !inside(x2) ) {

			} else if ( inside(x1) && !inside(x2) ) {
				//		System.out.println(" x1 : " + x1 + " x2: " + x2 + " line " + i + " inside(x1) " + inside(x1) );
				//		System.out.println("flux: " + flux);
				flux += add( x1, x2, i);
				//		System.out.println(" flux : " + flux + " after add: " + add(x1, x2, i) );
			} else if ( !inside(x1) && inside(x2) ) {
				//		System.out.println(" x1 : " + x1 + " x2: " + x2 + " line " + i + " inside(x1) " + inside(x1) );
				//		System.out.println("flux: " + flux);
				flux += add( x2, x1, i);
				//		System.out.println(" flux : " + flux + " after add: " + add(x1, x2, i) );
			}

		}

		//	System.out.println(" RETURN flux: " + flux );
		return flux;
	}
}