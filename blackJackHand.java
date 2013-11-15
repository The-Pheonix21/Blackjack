public class BlackjackHand {
  int totalHandValue;
  int cardCount;
   boolean ace = false;
   boolean aceHigh = false;
   public BlackjackHand() {
           // Create a Hand object that is initially empty.
      totalHandValue=0;
      cardCount=0;

   }
   
   public void handClear() {
         // Discard all the cards from the hand.
      totalHandValue=0;
      cardCount=0;
   }
   
   
   public int getCardCount() {
         // Return the number of cards in the hand.
      return cardCount;
   }
   
   
   public int BlackjackValue(Card cdealt) {
            // Returns the value of this hand for the
            // game of Blackjack
      int cardVal;
        cardCount++;
       
             cardVal = cdealt.getValue();  // The normal value, 1 to 13.
             if (cardVal > 10) {
                 cardVal = 10;   // For a Jack, Queen, or King.
             }
             if (cardVal == 1) {
                 ace = true;     // There is at least one ace.
             }
             totalHandValue = totalHandValue + cardVal;
         

             // Now, val is the value of the hand, counting any ace as 1.
             // If there is an ace, and if changing its value from 1 to 
             // 11 would leave the score less than or equal to 21,
             // then do so by adding the extra 10 points to val. 

          if ( ace == true  &&  totalHandValue + 10 <= 21 ){
              totalHandValue = totalHandValue + 10;
             aceHigh=true;}
             if (totalHandValue > 21 && aceHigh){
              totalHandValue=totalHandValue - 10;
              aceHigh=false;
             }
          return totalHandValue;

     }  // end getBlackjackValue()
  
}