package my.practice.pattern;

import java.util.Observable;
import java.util.Observer;

public class FyiFan implements Observer {
	
	private Celebrity idol;

	@Override
	public void update(Observable o, Object arg) {
		idol = (Celebrity) o;
		System.out.println("FYI: " + idol.getTwitter() );
	}

}
