public class Deck {
	private Card[] deck;
	private int cardsUsed;
	public Deck(){
		deck=new Card[52];
		int counter = 0;
		for (int i = 0; i <= 3;i++) {
			for (int j = 1; j < 14 ;j++ ) {
				deck[counter] = new Card(i,j);
				
				counter++;
				System.out.println("card number: "+counter+"\t"+ deck[counter-1].getStrCard());
			}
		}
	}
	public void shuffle() {
		for (int i = 51;i > 0;i--) {
			int rand = (int)(Math.random()*(i+1));
			Card temp = deck[i];
			deck[i] = deck[rand];
			deck[rand] = temp;
		}
		cardsUsed = 0;
		int counter = 0;
		for (int i = 0; i < 52;i++) {	
				System.out.println("card number: "+(i+1)+"\t"+ deck[i].getStrCard());	
		}
	}
	public int cardsLeft() {
		return 52 - cardsUsed;
	}
	public Card dealCard() {
		if (cardsUsed == 52) 
			shuffle();
			cardsUsed++;
			return deck[cardsUsed -1];
	}
}
