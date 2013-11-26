import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	private int value;
	private String[] strvalue = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
	private int suit;
	private String[] suitstr = {"Clubs","Hearts","Diamonds","Spades"};
	private Image image;
	private String name;
	public Card(int dsuit, int dvalue){
		this.value = dvalue;
		this.suit = dsuit;
	}
	public int getValue(){
		return this.value;
	}
	public int getSuit(){
		return this.suit;
	}
	public String getStrCard(){
		String cardInfo = strvalue[value-1] + " of "+ suitstr[suit];
		return cardInfo;
	}	
	// combines first letter of value to suit used for load image
	public String getCardImageName() {
		String temp;
		if (strvalue[value-1].equals("10")) {
			temp = "10";
		}else {
			temp=strvalue[value-1].substring(0,1);
		}
		return temp+suitstr[suit];
	}
	//For flipped over dealer card and takes the name
		public void cardImage(String x) {
		if (x!="" && x!=null) {
			this.name=getCardImageName();
		}else {
			this.name="back-blue";
		}
		this.image = loadImage(name);
		}
	public void drawblackJackCard(Graphics g, Rectangle r) {
		g.drawImage(image, r.x, r.y, r.width, r.height, null);
	}
	private static Image loadImage(String name) {
		String path = null;
		Image image = null;

	
		try{
		path = "cards" + File.separator + name + ".png";	
		File file = new File(path);
		image = ImageIO.read(file);
		}catch   (IOException e) {
			System.out.println("Could not load image at path" + path);
			System.exit(1);

		
		}
	return image;}
}