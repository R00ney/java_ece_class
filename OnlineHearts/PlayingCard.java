//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13



import java.util.*;
import java.io.Serializable;

public class PlayingCard implements Comparable<PlayingCard>, Serializable{

	public enum Suit {
		CLUB("♣"), DIAMOND("♦"), SPADE("♠"),  HEART("♥");
		
		Suit(String sym) {
			symbol = sym;
		}
		private String symbol;
		public String getSymbol() { return symbol; }
	};
	
	public enum Rank {
		TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), 
		  SEVEN("7"),EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), 
		  KING("K"), ACE("A");
			
		Rank(String sym) {
			symbol = sym;
		}
		private String symbol;
		public String getSymbol() { return symbol; }
	};

	
	public PlayingCard(Suit s, Rank r) {
		suit = s;
		rank = r;
	}
	
	Suit suit;
	Rank  rank;
	
	public Suit getSuit() { return suit; }
	public Rank getRank() { return rank; }
	
	
	
	public static void main(String[] args) {
	
	//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "PlayingCard program ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		//end name output

		//Create new playing card deck
		List<PlayingCard> deck = getDeck();
		
		Collections.shuffle(deck);

		Collections.sort(deck);
		
		System.out.println(deck);
		
	}//end main
	
	
	
			//Declares the comparison method for sorting playing cards
	public void sort(List<PlayingCard> deck){
		Collections.sort(deck, new Comparator<PlayingCard>() {
			public int compare(PlayingCard a, PlayingCard b) {
				if ( a.suit == b.suit ) {
					return a.rank.compareTo(b.rank);
				} 
				if ( a.suit == Suit.HEART ) {
					return -1;
				} else if ( b.suit == Suit.HEART ){
					return 1;
				} else {
					return a.suit.compareTo(b.suit);
				}
			}
		});
	
	}
	
	
	public String toString() {
		return rank.getSymbol() + suit.getSymbol();
	}
	
	
	public static List<PlayingCard> getDeck() {
		List<PlayingCard> deck = new ArrayList<PlayingCard>();
		for (Suit s: Suit.values()) {
			for (Rank r: Rank.values()) {
				deck.add(new PlayingCard(s,r));
			}
		}
		return deck;
	}



	@Override
	public int compareTo(PlayingCard arg0) {
		int ret = suit.compareTo(arg0.suit);
		if (ret==0) {
			return rank.compareTo(arg0.rank);
		}
		return ret;
	}
	
	
	//added lab 8
	public boolean equals(Object other) {
		if (other instanceof PlayingCard) {
			PlayingCard o = (PlayingCard)other;
			return suit==o.suit && rank==o.rank;
		}
		return super.equals(other);
	}


}
