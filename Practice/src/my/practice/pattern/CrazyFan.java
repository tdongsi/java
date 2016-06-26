package my.practice.pattern;

import java.util.Observable;
import java.util.Observer;

public class CrazyFan implements Observer {
	
	private Celebrity idol;

	@Override
	public void update(Observable o, Object arg) {
		idol = (Celebrity) o;
		System.out.println("OMG. It's crazy #crazy #love :" + idol.getTwitter() );
	}

}
