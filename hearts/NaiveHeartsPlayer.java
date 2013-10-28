//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

import java.util.*;

public class NaiveHeartsPlayer extends RunnableHeartsPlayer {

	NaiveHeartsPlayer(String name, HeartsGame game) {
		super(name);
		setGame(game);
	}

	@Override
	protected PlayingCard selectCard() {
		List<PlayingCard> cards = game.getPlays();
		List<PlayingCard> hand = game.getHand(getName());
		Collections.sort(hand);
		if (cards.size()==0 || hand.size()==13) {
			// check for 2 of clubs/Not, because sorted, first card
			return hand.get(0);
		}
		//sort
		Collections.sort(hand,new CardComparator(cards.get(0).getSuit()));
		if (hand.get(0).getSuit()==cards.get(0).getSuit())
		   return hand.get(0);
		else
		   return hand.get(hand.size()-1);

		//return null;
	}
	
	public class CardComparator implements Comparator<PlayingCard> {
		PlayingCard.Suit leadingSuit;
		public CardComparator(PlayingCard.Suit s) {
			leadingSuit = s;
		}
		@Override
		public int compare(PlayingCard arg0, PlayingCard arg1) {
		// move cards to the front that match suit field
		// move low ranking cards to the front
/*========================================================================================*/
			if (arg0.getSuit()==arg1.getSuit())
				return -arg0.getRank().compareTo(arg1.getRank());
			if (arg0.getSuit()==leadingSuit)
				return -1; // arg0 should come first
			if (arg1.getSuit()==leadingSuit)
				return 1;  // arg1 should come first

			// otherwise, just use the normal Suit.compareTo() order.
			return arg0.getSuit().compareTo(arg1.getSuit());		
		}
	}

}
