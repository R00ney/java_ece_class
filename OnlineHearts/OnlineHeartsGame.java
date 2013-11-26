// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.HashMap;


public class OnlineHeartsGame extends UnicastRemoteObject implements HeartsGameInterface {
	private static final long serialVersionUID = 1L;		
	Map<String, GameClientInterface> clientMap = new ConcurrentHashMap<String, GameClientInterface>();
	HeartsGame game = null;
	
	
	protected OnlineHeartsGame(Map<String,GameClientInterface> clients) throws RemoteException {
		super();
		
		clientMap = new HashMap<String,GameClientInterface>(clients);
		game = new HeartsGame(clientMap.keySet().toArray(new String[0]));
		
	}
	
	
	@Override
	public boolean isGameOver() throws RemoteException{
		
		return game.isGameOver();
	}
	
	@Override
	public boolean isMyTurn(String name) throws RemoteException{
		String current_turn = game.getTurn();
		if(name.equals(current_turn))
			return true;
		else
			return false;
	}
	
	@Override
	public Map<String, Integer> getScores() throws RemoteException, HeartsGameException{
		
		return game.getScore();
	}
	
	@Override
	public List<HeartsGame.Play> getPlays() throws RemoteException, HeartsGameException {
		
		return game.getCurrentPlays();
	}
	
	@Override
	public List<PlayingCard> getHand(String name) throws RemoteException, HeartsGameException{
		
		return game.getHand(name);
	}
	
	@Override
	public void addPlay(String name, PlayingCard card) throws HeartsGameException, RemoteException{
		
		game.addPlay(name,card);
		GameClientInterface client = this.clientMap.get(game.getTurn());
		try {
			client.takeYourTurn(); 
		} catch (RemoteException e) {
			// Client is offline, nothing to do but hope they 
			// come back soon!
		}

	}
	
	
}
