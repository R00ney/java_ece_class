// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.Naming;

import java.util.Date;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;  
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.HashMap;


public class GameServer extends java.rmi.server.UnicastRemoteObject implements GameServerInterface {
	private static final long serialVersionUID = 1L;
	Map<String, AccountInfo> map = new ConcurrentHashMap<String,AccountInfo>();
	List<String> waitingList= new LinkedList<String>();
	
	protected GameServer() throws RemoteException{
		super();
	}
	@Override
	public void register(GameClientInterface client) throws RemoteException, GameServerException {
		if(client == null){
			System.out.println("Debug:error client null");
			return;
		}
		
		Credentials c = client.getCredentials();
		
		if(!map.containsKey(c.getName())){
			map.put(c.getName(),new AccountInfo(c));
			System.out.println(c.getName() + " has registered.");
			
		}
		else{
			//account exists, validate password.
			AccountInfo info = map.get(c.getName());
			if(info.id.getPassword().compareTo(c.getPassword())!=0)
				throw new GameServerException("Password is inccorrect.");
			System.out.println(c.getName() + " has reconnected!");
		}
		
		synchronized(this){
			AccountInfo info = map.get(c.getName());
			info.setClient(client);

			
			if(!info.isGameActive())
				waitingList.add(c.getName());
			
			//if waiting list > 4, start game
			if(waitingList.size()>=4){
				//create game
				List<String> sublist = new LinkedList<String>(); 
				sublist.addAll(waitingList.subList(0,4));
				waitingList.removeAll(sublist);
				
				Map<String, GameClientInterface> clients = new HashMap<String,GameClientInterface>();
				for(String s: sublist){
					clients.put(s,map.get(s).getClient());
				}
				System.out.println("Start a new game with: " + clients.keySet());
				
				OnlineHeartsGame game = new OnlineHeartsGame(clients);
				for(String s: sublist)
					map.get(s).setGame(game);
			}
		}
			
		
		
		
	}
	public static void main(String[] args) throws Exception{
		GameServer server = new GameServer();
		Naming.rebind("OnlineHeartsGame", server);
		
		
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "GamerServer program ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		//end name output
		
		
		System.out.println("GameServer is running at rmi://" + InetAddress.getLocalHost().getHostAddress() + "/OnlineHeartsGame" );
	}
	
	public static class AccountInfo {
		public Credentials id;
		OnlineHeartsGame game = null;
		public AccountInfo(Credentials c){ id = c;}
		//Client
		private GameClientInterface client = null;
		public synchronized void setClient(GameClientInterface client){
			this.client = client;
			try{
				if (game!=null && !game.isGameOver()){
					this.client.gameIsReady(game);
					System.out.println(id.getName() + " found a game to play!");
				}
			} catch (RemoteException re){
				//can't reach client, probably temp,
				//swallow exception
				System.out.println(id.getName() + " may have logged out. Game is starting without them." );
			}
		}
		public synchronized GameClientInterface getClient() {
			return client;
		}
		public synchronized void setGame(OnlineHeartsGame game){
			this.game = game;
			try {
				this.client.gameIsReady(game);
			} catch(RemoteException re){
				System.out.println(id.getName() + " may have logged out. " + " Game is starting without them. ");	
			}
			
		}
		public synchronized OnlineHeartsGame getGame() {
			return game;
		}
		
		public synchronized boolean isGameActive() {
			try {
				return (game!=null) && !game.isGameOver();
			} catch (RemoteException e) {
				game=null; // for some reason is game cannot be reached so reset to null
				return false;
			}
		}

		
	};
	
}
