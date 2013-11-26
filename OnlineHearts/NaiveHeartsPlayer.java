//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

import java.util.*;

//can use the get plays methods for smarter players
//
import java.rmi.RemoteException;

public class NaiveHeartsPlayer extends RunnableHeartsPlayer {
	
	Boolean debug_valid = false; //System parameter for debug statements
	
	NaiveHeartsPlayer(String name, HeartsGameInterface game) {
		super(name);
		setGame(game);
	}

	@Override
	protected PlayingCard selectCard() throws RemoteException, HeartsGameException{
		List<HeartsGame.Play> plays_cards = game.getPlays();
		List<PlayingCard> cards = new ArrayList<PlayingCard>();
		for(HeartsGame.Play p: plays_cards){
			cards.add(p.getCard());	
		}
		List<PlayingCard> hand = game.getHand(getName());
		Collections.sort(hand);
		
		if(debug_valid)
			System.out.println(getName() + " from hand " + hand);
		
		if (cards.size()==0 || hand.size()==13) {
			// check for 2 of clubs/Not, because sorted, first card
			if(debug_valid) System.out.println("selects card: " + hand.get(0));
			return hand.get(0);
		}
		//sort
		Collections.sort(hand,new CardComparator(cards.get(0).getSuit()));
		
		if (hand.get(0).getSuit()==cards.get(0).getSuit()){
		   if(debug_valid) System.out.println("selects card: " + hand.get(0));
		   return hand.get(0);
		  }
		else{
			if(debug_valid) System.out.println("selects card: " + hand.get(hand.size()-1));
		   return hand.get(hand.size()-1);
			}
		//return null;
	}
	
	
	public class CardComparator implements Comparator<PlayingCard> {
		PlayingCard.Suit suit;
		public CardComparator(PlayingCard.Suit s) {
			suit = s;
		}
		@Override
		public int compare(PlayingCard arg0, PlayingCard arg1) {
		
		// move cards to the front that match suit field
			if ((arg0.getSuit()==suit) && (arg1.getSuit()==suit)){ //if both match suit, then do lowere card thing
					return arg0.getRank().compareTo(arg1.getRank());
			}
			
			if ((arg0.getSuit()==suit))
				return -1; // arg0 matches suit, comes before non-suit match
			if (arg1.getSuit()==suit)
				return 1;  // arg1 matches suit, comes before non-suit match
		
		// if suits the same
		// move low ranking cards to the front
		if((arg0.getSuit()== arg1.getSuit())){
			return arg0.getRank().compareTo(arg1.getRank());
		}
		else //suit are not the same, so sort by suit
			return arg0.getSuit().compareTo(arg1.getSuit());
		}
	}


}
