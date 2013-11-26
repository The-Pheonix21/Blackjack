 import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;
/**
    * The public static class BlackjackGUI.Applet represents this program
    * as an applet.  The applet's init() method simply sets the content 
    */
   public class BlackjackGUI extends JApplet {
      public void init() {
         setContentPane( new BlackjackGUI() );
      }
   
   
   /**
    * The constructor lays out the panel.  A CardPanel occupies the CENTER 
    * position of the panel (where CardPanel is a subclass of JPanel that is 
    * defined below).  On the bottom is a panel that holds three buttons.  
    * The CardPanel listens for ActionEvents from the buttons and does all 
    * the real work of the program.
    */
   public BlackjackGUI() {
      
      setBackground( new Color(130,50,40) );
      
      setLayout( new BorderLayout(3,3) );
      
      CardPanel board = new CardPanel();
      add(board, BorderLayout.CENTER);
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground( new Color(220,200,180) );
      add(buttonPanel, BorderLayout.SOUTH);
      
      JButton hitButton = new JButton( "Hit!" );
      hitButton.addActionListener(board);
      buttonPanel.add(hitButton);
      
      JButton standButton = new JButton( "Stand!" );
      standButton.addActionListener(board);
      buttonPanel.add(standButton);
      
      JButton newGame = new JButton( "New Game" );
      newGame.addActionListener(board);
      buttonPanel.add(newGame);
      
      //setBorder(BorderFactory.createLineBorder( new Color(130,50,40), 3) );
      
   }  // end constructor
   
   
   
   /**
    * A nested class that displays the game and does all the work
    * of keeping track of the state and responding to user events.
    */
   private class CardPanel extends JPanel implements ActionListener {
      
      Deck deck;         // A deck of cards to be used in the game.
      
      BlackjackHand dealerHand;   // Hand containing the dealer's cards.
      BlackjackHand playerHand;   // Hand containing the user's cards.
      
      String message;  // A message drawn on the canvas, which changes
                       //    to reflect the state of the game.
      
      boolean gameInProgress;  // Set to true when a game begins and to false
                               //   when the game ends.
      
      Font bigFont;      // Font that will be used to display the message.
      Font smallFont;    // Font that will be used to draw the cards.
      Card dFirst;
      int noWinsPlayer = 0;
      int noWinsDealer = 0;
      
      /**
       * The constructor creates the fonts and starts the first game.
       * It also sets a preferred size of 460-by-310 for the panel.
       * The paintComponent() method assumes that this is in fact the
       * size of the panel (although it can be a little taller with
       * no bad effect).
       */
      CardPanel() {
         setPreferredSize( new Dimension(460,310) );
         setBackground( new Color(0,120,0) );
         smallFont = new Font("SansSerif", Font.PLAIN, 12);
         bigFont = new Font("Serif", Font.BOLD, 16);
         doNewGame();
      }
      
      /**
       * Respond when the user clicks on a button by calling the appropriate 
       * method.  Note that the buttons are created and listening is set
       * up in the constructor of the BlackjackPanel class.
       */
      public void actionPerformed(ActionEvent evt) {
         String command = evt.getActionCommand();
         if (command.equals("Hit!"))
            doHit();
         else if (command.equals("Stand!"))
            doStand();
         else if (command.equals("New Game"))
            doNewGame();
      }
      
      
      /**
       * This method is called when the user clicks the "Hit!" button.  First 
       * check that a game is actually in progress.  If not, give  an error 
       * message and exit.  Otherwise, give the user a card.  The game can end 
       * at this point if the user goes over 21 or if the user has taken 5 cards 
       * without going over 21.
       */
      void doHit() {
         if (gameInProgress == false) {
            message = "Click \"New Game\" to start a new game.";
            repaint();
            return;
         }
         playerHand.addCard( deck.dealCard() );
         if ( playerHand.BlackjackValue() > 21 ) {
            message = "You've busted!  Sorry, you lose.";
            gameInProgress = false;
            noWinsDealer ++;
         }
         else {
            message = "You have " + playerHand.BlackjackValue() + ".  Hit or Stand?";
         }
         repaint();
      }
      
      
      /**
       * This method is called when the user clicks the "Stand!" button.
       * Check whether a game is actually in progress.  If it is, the game 
       * ends.  The dealer takes cards until either the dealer has 5 cards 
       * or more than 16 points.  Then the  winner of the game is determined. 
       */
      void doStand() {
         if (gameInProgress == false) {
            message = "Click \"New Game\" to start a new game.";
            repaint();
            return;
         }
         gameInProgress = false;
         while (dealerHand.BlackjackValue() <= 16)
            dealerHand.addCard( deck.dealCard() );
         if (dealerHand.BlackjackValue() > 21){
            message = "You win!  Dealer has busted with " + dealerHand.BlackjackValue() + ".";
            noWinsPlayer ++;
        }
         else if (dealerHand.BlackjackValue() > playerHand.BlackjackValue()){
            message = "Sorry, you lose, " + dealerHand.BlackjackValue()
                        + " to " + playerHand.BlackjackValue() + ".";
            noWinsDealer ++;
          }
         else if (dealerHand.BlackjackValue() == playerHand.BlackjackValue()){
            message = "Sorry, you lose.  Dealer wins on a tie.";
            noWinsDealer ++;
          }
         else{
            message = "You win, " + playerHand.BlackjackValue()
                        + " to " + dealerHand.BlackjackValue() + "!";
            noWinsPlayer ++;
          }
         repaint();
      }
      
      
      /**
       * Called by the constructor, and called by actionPerformed() if  the 
       * user clicks the "New Game" button.  Start a new game.  Deal two cards 
       * to each player.  The game might end right then  if one of the players 
       * had blackjack.  Otherwise, gameInProgress is set to true and the game 
       * begins.
       */
      void doNewGame() {
         if (gameInProgress) {
               // If the current game is not over, it is an error to try
               // to start a new game.
            message = "You still have to finish this game!";
            repaint();
            return;
         }
         deck = new Deck();   // Create the deck and hands to use for this game.
         dealerHand = new BlackjackHand();
         playerHand = new BlackjackHand();
         dealerHand.handClear();
        playerHand.handClear();
         deck.shuffle();
         dealerHand.addCard( deck.dealCard() );  // Deal two cards to each player.
         dealerHand.addCard( deck.dealCard() );
         playerHand.addCard( deck.dealCard() );
         playerHand.addCard( deck.dealCard() );
         if (dealerHand.BlackjackValue() == 21) {
            message = "Sorry, you lose.  Dealer has Blackjack.";
            gameInProgress = false;
            noWinsDealer ++;
         }
         else if (playerHand.BlackjackValue() == 21) {
            message = "You win!  You have Blackjack.";
            gameInProgress = false;
            noWinsPlayer ++;
         }
         else {
            message = "You have " + playerHand.BlackjackValue() + ".  Hit or stand?";
            gameInProgress = true;
         }
         repaint();
      }  // end newGame();
      
      
      /**
       * The paint method shows the message at the bottom of the
       * canvas, and it draws all of the dealt cards spread out
       * across the canvas.
       */
      public void paintComponent(Graphics g) {
         
         super.paintComponent(g); // fill with background color.
         
         g.setFont(bigFont);
         g.setColor(Color.GREEN);
         g.drawString(message, 10, getHeight() - 10);
         g.drawString(("Player Score: " + noWinsPlayer + "  Dealer Score: " + noWinsDealer), 10, getSize().height-50);
         
         // Draw labels for the two sets of cards.
         
         g.drawString("Dealer's Cards:", 10, 23);
         g.drawString("Your Cards:", 10, 153);
         
         // Draw dealer's cards.  Draw first card face down if
         // the game is still in progress,  It will be revealed
         // when the game ends.
         
         g.setFont(smallFont);
         if (gameInProgress)
            drawCard(g, null, 10, 30);
         else
            drawCard(g, dealerHand.getCard(0), 10, 30);
         for (int i = 1; i < dealerHand.getCardCount(); i++)
            drawCard(g, dealerHand.getCard(i), 10 + i * 90, 30);
         
         // Draw the user's cards.
         
         for (int i = 0; i < playerHand.getCardCount(); i++)
            drawCard(g, playerHand.getCard(i), 10 + i * 90, 160);
         
      }  // end paint();
      
      
      /**
       * Draws a card as a 80 by 100 rectangle with upper left corner at (x,y).
       * The card is drawn in the graphics context g.  If card is null, then
       * a face-down card is drawn.  (The cards are rather primitive!)
       */
      void drawCard(Graphics g, Card card, int x, int y) {
                  // Draws a card about 70 by 90...
        // first if shows back for dealer hand
        //else shows card for dealer and player
          String temp;
          if (card == null) { 
             dFirst=dealerHand.getCard(0);
             dFirst.CardImage("");
             dFirst.drawbjCard(g, new Rectangle(x+4,y+4,71,91));
          }
          else {
             temp=card.getCardImageName();
             card.CardImage(temp);
             card.drawbjCard(g, new Rectangle(x+1, y+1, 77, 97));
            
          }
       }  // end drawCard()
      
      
   } // end nested class CardPanel
   
   
} // end class BlackjackGUI