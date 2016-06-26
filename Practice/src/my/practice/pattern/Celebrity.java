package my.practice.pattern;

import java.util.Observable;

public class Celebrity extends Observable {
	
	private String twitter;

	public Celebrity(String twitter) {
		super();
		this.twitter = twitter;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
		setChanged();
		notifyObservers();
	}
	
}
