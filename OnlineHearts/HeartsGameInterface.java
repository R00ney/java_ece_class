// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
import java.util.List;


public interface HeartsGameInterface extends Remote {
	//Game Status
	boolean isGameOver() throws RemoteException;
	boolean isMyTurn(String name) throws RemoteException;
	Map<String, Integer> getScores() throws RemoteException, HeartsGameException;
	
	//Game API
	List<HeartsGame.Play> getPlays() throws RemoteException,HeartsGameException;
	List<PlayingCard> getHand(String name) throws RemoteException,HeartsGameException;
	void addPlay(String name, PlayingCard card) throws HeartsGameException, RemoteException;
}
