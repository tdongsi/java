package my.practice.pattern;

public class ObserverDemo {

	public static void main(String[] args) {
		String[] twitterMesssages = {"World Peace", "RIP, another celebrity!!", "At party!!"};
		
		Celebrity cel = new Celebrity("Nothing");
		CrazyFan crazy = new CrazyFan();
		FyiFan fyi = new FyiFan();
		
		cel.addObserver(crazy);
		cel.addObserver(fyi);
		
		for (String status : twitterMesssages) {
			cel.setTwitter(status);
		}
	}

}
