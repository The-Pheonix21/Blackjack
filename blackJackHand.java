public class blackJackHand {
	int totalHandValue;  // black jack value of the hand
	int cardCount;	//number of cards in the hand
	
	boolean ace = false;//ace there true or false
	boolean aceHigh = false;// used 11 or not
	Card [] handCards = new Card[11];
	public blackJackHand(){
		totalHandValue=0;
		cardCount=0;

	}
	public void addCard(Card newCard){
		handCards[cardCount] = newCard;
		cardCount++;
	}
	public Card getCard(int i){
		return handCards[i];
	}
	// clears everything for the hand
	public void handClear(){
		for (int i=0; i<cardCount; i++) {
			handCards[i]=null;
		}
		totalHandValue=0;
		cardCount=0;
		aceHigh=false;
		ace=false;

	}
	//card counter illegal
	public int getCardCount(){
		return cardCount;
	}
	// value of hand
	public int BlackjackValue(){
		int cardVal;
		totalHandValue=0;
		Card temp;
		for (int i=0; i < cardCount; i++) {
			temp= handCards[i];
			cardVal = temp.getValue();
			if (cardVal > 10) {
				cardVal = 10;
			}
			if (cardVal == 1) {
				ace=true;
			}
			totalHandValue = totalHandValue + cardVal;
		}
		if (ace == true && totalHandValue + 10 <= 21) {
		totalHandValue = totalHandValue + 10;		
		aceHigh=true;}	
		if (totalHandValue > 21 && aceHigh) {
			totalHandValue-=10;
			aceHigh=false;
		}
		return totalHandValue;
	}

}