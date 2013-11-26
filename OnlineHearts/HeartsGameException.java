//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13


public class HeartsGameException extends Exception {
	private static final long serialVersionUID = 1L;
	String name;
	PlayingCard card;
	public HeartsGameException(String name, PlayingCard card) {
		this.name = name;
		this.card = card;
	}
	public String getMessage() {
		return "Player " + name + " triggered an exception when playing " + card.toString()+".";
	}
}
