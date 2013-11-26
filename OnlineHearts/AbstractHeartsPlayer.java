//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

import java.rmi.RemoteException;

public abstract class AbstractHeartsPlayer {

	//public, not private?
	private String name;

	public String getName() {
		return name;
	}
	
	AbstractHeartsPlayer(String name) {
		this.name = name;
	}

	public abstract void setGame(HeartsGameInterface game);
	public abstract HeartsGameInterface getGame();


	//public abstract boolean isMyTurn();	
	public abstract void awaitTurn() throws RemoteException;
	public abstract void takeTurn() throws HeartsGameException, RemoteException;
}
