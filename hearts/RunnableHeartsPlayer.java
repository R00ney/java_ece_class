//Hearts Game
//ECE 309 Lab 7
//Neal O'Hara
//10/26/13

public abstract class RunnableHeartsPlayer extends AbstractHeartsPlayer implements Runnable {
	RunnableHeartsPlayer(String name) {
		super(name);
	}
	
	HeartsGame game;	
	public void setGame(HeartsGame game) {
		this.game = game;
	}	
	public HeartsGame getGame() {
		return game;
	}

	public void run() {
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
		}	
	}
	
	public void awaitTurn() {
		game.awaitTurn(getName());
	}

	//public, not protected?
	public void takeTurn() throws HeartsGameException {		
		PlayingCard card = selectCard();	
		game.addPlay(name,card);
	}

	protected abstract PlayingCard selectCard();
	
}