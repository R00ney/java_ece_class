//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

/*
Hearts can be played with 3-8 players.  We will fix this at 4 to simplify our logic.
The HeartsGame class will also dictate which players are involved and whose turn it is. 
The class will figure out when the game is over.
The class will allow a player to take a turn.  
The class will have a child class that implements a Hand.  As specified in the rules, a Heart’s game is determined by a round of hands which culminates when a player reaches a score of 100 or greater.  (Keep in mind that the goal is to have the lowest score.)  So, we want to support several hands of play until the losing score is finally reached.
*/

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.io.Serializable;
import java.util.Date;
import java.util.*;

public class HeartsGame implements Serializable{


	
	public class CardComparator implements Comparator<Play> {
		// Remember the leading suit
		PlayingCard.Suit leadingSuit;	
		public CardComparator(PlayingCard.Suit s) {
			leadingSuit = s;
		}
		@Override
		public int compare(Play arg0, Play arg1) {
			if (arg0.card.getSuit()==arg1.card.getSuit())
				return -arg0.card.getRank().compareTo(arg1.card.getRank());
			if (arg0.card.getSuit()==leadingSuit)
				return -1; // arg0 should come first
			if (arg1.card.getSuit()==leadingSuit)
				return 1;  // arg1 should come first

			// otherwise, just use the normal Suit.compareTo() order.
			return arg0.card.getSuit().compareTo(arg1.card.getSuit());
		}
		
	}
	
	public class Play implements Serializable {
		private static final long serialVersionUID = 1L;
		public String name;
		public PlayingCard card;
		
		public String toString() { // useful for printing
			return card.toString()+"("+name+")";
		}
		public Play(String name, PlayingCard card) {
			this.name = name;
			this.card = card;
		}
	}

	
	public class Trick implements Serializable {
		private static final long serialVersionUID = 1L;
		
		// The plays in a trick
		List<Play> plays=new ArrayList<Play>();
		int points=0;
		String winner="";
		
		public void add(Play p) {
			plays.add(p);
		}
        
		public String toString() {
			return plays.toString() + "(Winner: "+winner+")";
		}
		
		public boolean isTrickDone(){
			Boolean done = false;
			if(plays.size()==4)
				done = true;
				
			return done;
		}
		
		public void computeWinner() {
			CardComparator cc = new CardComparator(plays.get(0).card.getSuit());
			Collections.sort(plays,cc);
			winner = plays.get(0).name;
			
			for(Play p: plays) {
				if (p.card.getSuit()==PlayingCard.Suit.HEART)
					points++; // hearts cost one point
				if (p.card.getSuit()==PlayingCard.Suit.SPADE && p.card.getRank()==PlayingCard.Rank.QUEEN)
					points+=13; // Queen of spades costs 13 points
			}

		}

	
	}// end trick class
	
	

	public class Hand implements Serializable {
		private static final long serialVersionUID = 1L;
		protected Trick currentTrick = new Trick(); // current trick
		Map<String,List<PlayingCard>> hands = new HashMap<String,List<PlayingCard>>();
		List< Trick > tricks = new ArrayList<Trick>();
		
		public Hand() {
			List<PlayingCard> deck = PlayingCard.getDeck();
			Collections.shuffle(deck);
			
			int pos=0;
			for(String p: players){
				hands.put(p, new ArrayList<PlayingCard>(deck.subList(pos, pos+13)));
				pos+=13;
				
				//Find player with 2 clubs, they play first
				Collections.sort(hands.get(p));
				if (hands.get(p).get(0).getRank()==PlayingCard.Rank.TWO && hands.get(p).get(0).getSuit() == PlayingCard.Suit.CLUB) 
					{
						turn = players.indexOf(p);
					}
			}

		}//end hand construct
		
		Boolean heartsCanLead = false;
		protected void validatePlay(String name, PlayingCard card) throws HeartsGameException 
		{
			if (name.compareTo(getTurn())!=0) {
				throw new HeartsGameException(name,card);
			}
			if (!hands.get(name).contains(card)) {
				throw new HeartsGameException(name,card);
			}
			if (tricks.size()==0 ) {
				// first hand, must play 2-of-Clubs
				if (currentTrick.plays.size()==0)
					if(card.getSuit()!=PlayingCard.Suit.CLUB && card.getRank()!=PlayingCard.Rank.TWO)
					{
					   throw new HeartsGameException(name,card);
					}
							
				  // other cards in first trick must not be point earning
				if(card.getSuit()==PlayingCard.Suit.HEART || (card.getSuit()==PlayingCard.Suit.SPADE 	 && card.getRank() == PlayingCard.Rank.QUEEN)) 
				  {
					throw new HeartsGameException(name,card);
				 }
			}
			if (currentTrick.plays.size()==0) {
				if (card.getSuit()==PlayingCard.Suit.HEART || (card.getSuit()==PlayingCard.Suit.SPADE	 && card.getRank() == PlayingCard.Rank.QUEEN) && heartsCanLead==false) {
					throw new HeartsGameException(name,card);
				} 
			} else {
				if ( card.getSuit()==PlayingCard.Suit.HEART || (card.getSuit()==PlayingCard.Suit.SPADE	 && card.getRank() == PlayingCard.Rank.QUEEN) ) {
					heartsCanLead = true;
				}
			}

			
			if (!currentTrick.plays.isEmpty()) { // this play is not the lead
			Play first = currentTrick.plays.get(0); // get the lead play
				if (card.getSuit()!=first.card.getSuit()) { //compare to suit
					for (PlayingCard c: hands.get(name)) {
						if (c.getSuit() == first.card.getSuit()) {
							throw new HeartsGameException(name,card);
						}	
					}
				}
			}
		}//end validate play
		
		protected synchronized void addPlay(String name, PlayingCard card) throws HeartsGameException 
		{
		
			try {
				validatePlay(name,card);
			} catch (HeartsGameException e) {
				scoreHand(e);
				HeartsGame.this.hand = new Hand();
				HeartsGame.this.notifyAll(); //wake other player threads
				throw e;
			}
			hands.get(name).remove(card);
			currentTrick.plays.add(new Play(name,card));
			updateTurn();
		}
		

		protected void updateTurn() {
		   turn = (turn+1)%4; // move to next index in list
		   
		   if (currentTrick.plays.size()==4) { // if the trick is complete
				currentTrick.computeWinner();
				turn = players.indexOf(currentTrick.winner);
				tricks.add(currentTrick); // add complete trick to tricks list
				currentTrick = new Trick(); // create a new trick for next play
			}
			
			if (tricks.size()==13) { // hand is over, 
				 scoreHand(null);    // update score,
				 hand = new Hand();  // make new hand
			}
			
			//activate other players threads
			HeartsGame.this.notifyAll();
		}

		Map<String,Integer> handScore = new HashMap<String,Integer>();
		private void scoreHand(HeartsGameException e) {
			for(String s: players) {
				handScore.put(s,0);	//initialize scores with 0
			}
			
			for(Trick t: tricks) {
				//System.out.println("Debug: "+t);
				handScore.put(t.winner,handScore.get(t.winner)+t.points);//changed from score to points field
			}
			//Shoot the moon happens once, so deal with it there
			//boolean shootTheMoon = false;
			for(String s: players) {
				//System.out.println("Debug: "+s);
				if(handScore.containsKey(s)){
					//System.out.println("Debug: contained "+s);
					if(handScore.get(s)==26){
						//shootTheMoon=true;
						
						for(String j: players) {
							if(j.equals(s)){
								handScore.put(j,0);	//Give shoot the moon's hand 0 points
							} else{
								handScore.put(j,26); //Give everyone else the 26 points
							}
						}
					}
				}
			}
			/*if (shootTheMoon) {
				// adjust everyone’s score
				for(String s: players) {
					
				}
			}*/
			if (e!=null) {//bad player, end game
				for(String s: players) {
				if (e.name.compareTo(s)==0)
						handScore.put(s,100);
				}
			}
			synchronized(HeartsGame.this) {
				for(String s: players) {
					if(handScore.containsKey(s))
						totalScore.put(s,totalScore.get(s)+handScore.get(s));
				}
			}
		}//end scoreHand
		
		
		protected synchronized void printHand() {
			System.out.println("Players: "+players);
			System.out.println("Hands:");
			for (String p: players) {
				System.out.printf("%10s: %s\n",p,hands.get(p).toString());
			}
			System.out.println("Plays: " + currentTrick);
			System.out.println(" Turn: "+getTurn());
		}
		
		public synchronized List<PlayingCard> getPlays() {
			List<PlayingCard> cards= new ArrayList<PlayingCard>();
			for(Play p: currentTrick.plays) {
				/* add p.card to cards */
				cards.add(p.card);
			}
			return cards;
		}


	}// end Hand
	
	
	protected synchronized String getTurn() {
		return players.get(turn);
	}


	public synchronized void addPlay(String name, PlayingCard card) throws HeartsGameException {
		hand.addPlay(name,card);
	}

	protected List<String> players = new ArrayList<String>();
	protected int turn=0; 
	// Sorted tree map
	private Map<String,Integer> totalScore = new TreeMap<String,Integer>();
	boolean gameOver = false;
	Hand hand=null;
	
	//accept player names?
	public static void main(String[] args) {
	
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "HeartsGame program ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		//end name output
		
		//Four player game, so four players
		String[] player_names = new String[4];
		player_names[0] = "Bob";
		player_names[1] = "Sandy";
		player_names[2] = "Alex";
		player_names[3] = "Catherine";
		
		//Create new game
		HeartsGame game = new HeartsGame(player_names);
		
		
		//Print game's scores
		game.printGameScores();
		
		//Check game status
		Boolean game_finished = game.isGameOver();
		
		System.out.printf("%10s: %s\n","Game Over",String.valueOf(game_finished));
		
		
		System.out.println("Test 3:");
		String[] array = {"James","Ben","David","Elizabeth"};
		List<PlayingCard> deck = PlayingCard.getDeck();
		Collections.shuffle(deck);
		Trick trick = game.new Trick();
		for(int i=0; i<4; i++) {
			trick.add(game.new Play(array[i],deck.get(i)));
		}
		System.out.println(trick);		
		trick.computeWinner();
		System.out.println(trick);
		
		
		System.out.println("Test 4:");
		game.hand.printHand();
		
		try{
		tests(game);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		
	}//end main
	

	public static void tests(HeartsGame game) throws HeartsGameException {
		String name = game.getTurn();
		System.out.println("Test 5: Play the 2♣:");
		 // get first card
		PlayingCard card = game.hand.hands.get(name).get(0); 
		game.addPlay(name, card); // does not throw!
		game.hand.printHand();
			
		System.out.println("Test 6: Play "+name+"'s low card:");
		name = game.getTurn();
		card = game.hand.hands.get(name).get(0); // get first card
		game.addPlay(name, card); // does not throw!
		game.hand.printHand();
			
		name = game.getTurn();
		System.out.println("Test 7: Play "+name+"'s high card to throw an exception:");
		card = game.hand.hands.get(name).get(12); // get not first card
		try {
			game.addPlay(name, card); // does not throw!
			game.hand.printHand();
		} catch (HeartsGameException e) {
			System.out.println(name+" threw an exception when playing "+card.toString()+"as expected");
		}
		System.out.println();
			
		 game.hand.printHand();
		name = game.getTurn();
		card = game.hand.hands.get(name).get(0); // get first card
		game.addPlay(name, card); // does not throw!
		name = game.getTurn();
		card = game.hand.hands.get(name).get(0); // get first card
		game.addPlay(name, card); // does not throw!
		name = game.getTurn();
		card = game.hand.hands.get(name).get(0); // get first card
		game.addPlay(name, card); // does not throw!
		name = game.getTurn();
		card = game.hand.hands.get(name).get(0); // get first card
		game.addPlay(name, card); // does not throw!
			
		System.out.println("Test 8: One full trick has been played!");
		game.hand.printHand();
	}


	
	public synchronized boolean isGameOver() {
		// figure out if game is over
		if (gameOver) return true;
		
		for(String p: players){
			if (totalScore.get(p) >= 100)
				gameOver = true;
		}
		
		return gameOver;
	}

	
	public HeartsGame(String[] players) {
		this.players.addAll(Arrays.asList(players));
		for(String p: players)
			totalScore.put(p, 0);
		
		//create a hand
		hand= new Hand();
	}

	
	public synchronized void printGameScores() {
		// loop over players and print each player & score on a 
		// separate line
		for(String s: players)
			System.out.printf("%10s: %d\n",s,totalScore.get(s));
			
	}

	
	public synchronized void awaitTurn (String name) {
		while (players.get(turn).compareTo(name)!=0 && !isGameOver()) {
			try { 
				wait();
			} catch (InterruptedException e) {
				// try again
			}			
		}
	}

	
	// Return a copy of the player’s hand
	public synchronized List<PlayingCard> getHand(String name) {
		return new ArrayList<PlayingCard>(hand.hands.get(name));
	}

	// Return a copy of all plays in the current trick
	public synchronized List<PlayingCard> getPlays() {
		return hand.getPlays();
	}


}