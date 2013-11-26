//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

import java.rmi.RemoteException;
import java.util.Date;

public abstract class RunnableHeartsPlayer extends AbstractHeartsPlayer implements Runnable {
	RunnableHeartsPlayer(String name) {
		super(name);
	}
	
	HeartsGameInterface game;	
	public void setGame(HeartsGameInterface game) {
		this.game = game;
	}	
	public HeartsGameInterface getGame() {
		return game;
	}

	public void run() {
		try {
			while(true) {
				if(game.isGameOver())
					break;
		
				awaitTurn();
				if(game.isGameOver())
					break;
				try {
					takeTurn();
				} catch (HeartsGameException e) {
					System.out.println(getName()		+" got an exception!"); e.printStackTrace();		
				}
			}
			System.out.println("Final scores: "+game.getScores());
		} catch (RemoteException e1) {
			System.out.println("Can't reach server." 		+" Try playing again later.");
		} catch (HeartsGameException hge){
			//do nothing, report error
			System.out.println("Debug: getScores threw HGE");
			hge.printStackTrace();
		}
/*
		while(!game.isGameOver()) {
			awaitTurn();
			// game could end while waiting for our next turn
			if (game.isGameOver())
				return;
				
			try {
					takeTurn();
			} catch (HeartsGameException e) {
				System.out.println(e.getMessage());  
				e.printStackTrace();
			}	
		}*/	
	}
	
	public void awaitTurn() throws RemoteException {
		synchronized(this) {
			while(!game.isGameOver()) {
				if(game.isMyTurn(getName()))
					return;
				else
				try {
						wait(1000);
				} catch (InterruptedException e) {
						// Do nothing, just try again
				}
			} 	
		}
	}


	//public, not protected?
	public void takeTurn() throws HeartsGameException, RemoteException {		
		
		PlayingCard card = selectCard();
		//System.out.println("Taking turn " + new Date());
		String name = getName();
		game.addPlay(name,card);
	}

	protected abstract PlayingCard selectCard() throws RemoteException, HeartsGameException;
	
}