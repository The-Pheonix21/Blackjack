import java.awt.*;
import java.applet.*;
public class blackJackApplet extends Applet {
	private cardImage [] picturesAreHard;
	private cardImage [] picturesAreHard1;
	private Deck deck;
	private int width = 80;
	private int height = 100;

	public void init() {
		this.picturesAreHard = new cardImage[2];
		this.picturesAreHard1 = new cardImage[2];
		Card temp;
		deck = new Deck();
		 deck.shuffle();
		for (int i = 0;i < 2; i++) {
			temp = deck.dealCard();
			picturesAreHard[i] = new cardImage(temp.getCardImage());

		}
		deck.shuffle();
		 for (int j = 0;j < 2; j++) {
		 	temp = deck.dealCard();
		 	picturesAreHard1[j] = new cardImage(temp.getCardImage());

		 }
	}
	public void paint(Graphics g) {
		int countrow=0;
		int x=100;
		int y=100;
		for (int i = 0;i < 2;i++) {
			if ((i+1)%13==0) {
				x=75+(width*(i%13)+(25*(i%13)));
			}else {
				x=75+(width*((i)%13))+(25*((i)%13));
			}
			y=50+(countrow*height);
			picturesAreHard[i].draw(g,new Rectangle(x,y,width,height));
		}
		countrow=countrow+2;
		for (int i = 0;i < 2;i++) {
			if ((i+1)%13==0) {
				x=75+(width*(i%13)+(25*(i%13)));
			}else {
				x=75+(width*((i)%13))+(25*((i)%13));
			}
			if ((i%13==0) && (i>9)) {
				countrow++;
			}
			y=50+(countrow*height);
			picturesAreHard1[i].draw(g,new Rectangle(x,y,width,height));
		}
	}
}