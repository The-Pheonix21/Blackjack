public class blackJack {
	public static void main(String[] args) {
		boolean e = playBlackJack();
	}
	static boolean playBlackJack(){
		Deck deck;
		deck = new Deck();
		deck.shuffle();
		return true; 
	}
}
