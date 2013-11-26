// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface GameServerInterface extends Remote {
	
	void register(GameClientInterface client) throws RemoteException, GameServerException;
	
	
	
	
}
