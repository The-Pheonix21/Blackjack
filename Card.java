public class Card {
	private int value;
	private String[] strvalue = {"Ace","2","3","4","5","6","7","8","9","10","Jack","Queen","King"};
	private int suit;
	private String[] suitstr = {"Clubs","Hearts","Diamonds","Spades"};
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
	public String getCardImage() {
		String temp;
		if (strvalue[value-1].equals("10")) {
			temp = "10";
		}else {
			temp=strvalue[value-1].substring(0,1);
		}
		return temp+suitstr[suit];
	}
}