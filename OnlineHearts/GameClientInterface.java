// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13

import java.rmi.Remote;
import java.rmi.RemoteException;

//decribes the remote methods that can be used by GameClient
public interface GameClientInterface extends Remote{
	Credentials getCredentials() throws RemoteException;
	void gameIsReady(HeartsGameInterface game) throws RemoteException;
	void takeYourTurn() throws RemoteException;
}
