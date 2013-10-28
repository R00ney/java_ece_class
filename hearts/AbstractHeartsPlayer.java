//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13


public abstract class AbstractHeartsPlayer {

	//public, not private?
	public String name;

	public String getName() {
		return name;
	}
	
	AbstractHeartsPlayer(String name) {
		this.name = name;
	}

	public abstract void setGame(HeartsGame game);
	public abstract HeartsGame getGame();


	//public abstract boolean isMyTurn();	
	public abstract void awaitTurn();
	public abstract void takeTurn() throws HeartsGameException;
}
