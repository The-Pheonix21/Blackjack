 import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;

   public class BlackjackGUI extends JApplet {
      public void init() {
         setContentPane( new BlackjackGUI() );
      }
   
   
   // sets up the button and the panel and the color
   public BlackjackGUI() {
      
      setBackground( new Color(130,50,40) );
      
      setLayout( new BorderLayout(3,3) );
      
      CardPanel board = new CardPanel();
      add(board, BorderLayout.CENTER);
      
      JPanel buttonPanel = new JPanel();
      buttonPanel.setBackground( new Color(220,200,180) );
      add(buttonPanel, BorderLayout.SOUTH);
      
      JButton newGame = new JButton( "New Game" );
      newGame.addActionListener(board);
      buttonPanel.add(newGame);
      
      JButton newBet = new JButton( "Bet $10" );
      newBet.addActionListener(board);
      buttonPanel.add(newBet);

      JButton hitButton = new JButton( "Hit" );
      hitButton.addActionListener(board);
      buttonPanel.add(hitButton);
      
      JButton standButton = new JButton( "Stand" );
      standButton.addActionListener(board);
      buttonPanel.add(standButton);
      
      JButton doubleButton = new JButton("Double Down");
      doubleButton.addActionListener(board);
      buttonPanel.add(doubleButton);
      
      
   }  
   
   
   
   // nested class for j panel
   private class CardPanel extends JPanel implements ActionListener {
      
      Deck deck;         
      
      blackJackHand dealerHand;   
      blackJackHand playerHand;   

      String message;  // string to write messages
      
      boolean gameInProgress;  //true=started, false=new game
      
      Font bigFont;      
      Font smallFont;    
      Card dFirst;  // used to print back of the card for dealer
      int noWinsPlayer = 0;
      int noWinsDealer = 0;
      int playerAmt = 100; // players pockets
      int playerBet = 0;
      boolean betYet = false; // If bet true else false
      
      
      CardPanel() {
         setPreferredSize( new Dimension(460,310) );
         setBackground( new Color(0,120,0) );
         smallFont = new Font("SansSerif", Font.PLAIN, 12);
         bigFont = new Font("Serif", Font.BOLD, 16);
         doNewGame();
      }
      
      // takes input from button pressed and runs the method 
      public void actionPerformed(ActionEvent evt) {
         String command = evt.getActionCommand();
         if (command.equals("Hit"))
            doHit();
         else if (command.equals("Stand"))
            doStand();
          else if(command.equals("New Game")){
            resetBet();
            repaint();
            doNewGame();
          }
          else if (command.equals("Bet $10")) 
            doBet();
          else if (command.equals("Double Down")) 
            doDoubleDown();
          
      }
      
      
     // button hit checks for bet and game in progress
      void doHit() {
         if (betYet == false) {
           message = "Click\"Bet $10\" to begin play. ";
           repaint();
           return;
         }
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
         else if (playerHand.BlackjackValue() == 21) {
           message = "You have " + playerHand.BlackjackValue();
           repaint();
           doStand();
           return;
         }
         else {
            message = "You have " + playerHand.BlackjackValue() + ".  Hit or Stand?";
         }
         repaint();
      }
      // button bet checks for bet 
      void doBet(){
        if (betYet == true) {
           message= "You have already placed a bet. ";
           repaint();
      }
       
      if (betYet == false) {
         playerBet = 10;
        if (playerBet > playerAmt){
            message = "SORRY!  You CANNOT double down because you don't have enough money.";
            repaint();
            return;
        }
        betYet = true;
        playerAmt -= playerBet;
        doNewGame();
      }
    }
    // button Double down checks for bet and game in progress
    void doDoubleDown(){
      if (betYet == false) {
        message = "You must place a bet first. Click\"Bet $10\" to begin play.";
        repaint();
        return;
      }
      if (gameInProgress == false) {
        message = "Click\"Deal\"to start a new game.";
        repaint();
        return;
      }
      //if bet is > than Amt - no bet
       if (playerBet > playerAmt){
            message = "SORRY!  You CANNOT double down because you don't have enough money.";
            repaint();
            return;
        }
      playerAmt = playerAmt - playerBet;
      playerBet = playerBet *2;
      playerHand.addCard(deck.dealCard());
      if ( playerHand.BlackjackValue() > 21) {
        message = "You've busted! Sorry, you lose";
        gameInProgress = false;
        noWinsDealer ++;
        repaint();
        return;
      }
      else if (playerHand.BlackjackValue() == 21) {
        message = "You have " + playerHand.BlackjackValue();
        repaint();
        return;
      }else{
        message = "You have " + playerHand.BlackjackValue();
      }
      repaint();
      doStand();
    }
       // button Stand checks for bet and game in progress
    //plays the dealer hand.  If hand <= 16, have to take hit
    //if dealer hand greater than  or = 17 have to stand.
      void doStand() {
          if (betYet==false) {
            message = "Click\"Bet $10\" to begin play.";
            repaint();
            return;
          }
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
            playerAmt += 2*playerBet;
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
            playerAmt += 2*playerBet;
          }
         repaint();
      }
      // resets the playerBet and bet boolean chek
         void resetBet(){
          betYet = false;
          playerBet = 0;
         }  
      //
       // Called when button "New Game" pressed and player has bet.  If player hasn't bet,
       // returns with message to place bet.  Uses new deck and sets up new cards.  Start a new game.  Deal two cards 
       // Sets gameInProgress is set to true .
       // begins.
       //
      void doNewGame() {
         if (gameInProgress) {
            message = "You still have to finish this game!";
            repaint();
            return;
         }
         if (betYet==false) {
            message = "Click\"Bet $10\" to begin play.";
            repaint();
            return;
          }
         deck = new Deck();   // Create the deck and hands to use for this game.
         dealerHand = new blackJackHand();
         playerHand = new blackJackHand();
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
            playerAmt += 2*playerBet;
         }
         else {
            message = "You have " + playerHand.BlackjackValue() + ".  Hit or stand?";
            gameInProgress = true;
         }
         repaint();
      }  
      
      
        // The paint method shows the message at the bottom of the
        // canvas, and it draws all of the dealt cards spread out
        // across the canvas.
       
      public void paintComponent(Graphics g) {
         
         super.paintComponent(g); // fill with background color.
         
         g.setFont(bigFont);
         g.setColor(Color.GREEN);
         g.drawString(message, 10, getHeight() - 10);
         g.drawString(("Player amount : $" + playerAmt), 10, getSize().height-90);
         g.drawString(("Player Score: " + noWinsPlayer + "  Dealer Score: " + noWinsDealer), 10, getSize().height-50);
         
         // Title for dealer and player hands.
         
         g.drawString("Dealer's Cards:", 10, 23);
         g.drawString("Your Cards:", 10, 153);
         
         // Draw cards, if betYet is true.  If false, don't draw.  
         // Dealer first card is down
         
         g.setFont(smallFont);
         if(betYet){
          g.drawString(("You've bet " + playerBet),10, getSize().height-70);
         if (gameInProgress)
            drawCard(g, null, 10, 30);
         else
            drawCard(g, dealerHand.getCard(0), 10, 30);
         for (int i = 1; i < dealerHand.getCardCount(); i++)
            drawCard(g, dealerHand.getCard(i), 10 + i * 90, 30);
         
         // Draw the user's cards.
         
         for (int i = 0; i < playerHand.getCardCount(); i++)
            drawCard(g, playerHand.getCard(i), 10 + i * 90, 160);
         } else if (betYet == false) g.drawString((""),10, getSize().height-70);
            
         }  // end paint();
      
      
      // drawsCard image
      void drawCard(Graphics g, Card card, int x, int y) {
                  // Draws a card about 70 by 90...
        // first it shows back for dealer hand
        //else shows card for dealer and player
          String temp;
          if (card == null) { 
             dFirst=dealerHand.getCard(0);
             dFirst.cardImage("");
             dFirst.drawblackJackCard(g, new Rectangle(x+4,y+4,71,91));
          }
          else {
             temp=card.getCardImageName();
             card.cardImage(temp);
             card.drawblackJackCard(g, new Rectangle(x+1, y+1, 77, 97));
            
          }
       }  // end drawCard()
      
      
   } 
   
   
} 