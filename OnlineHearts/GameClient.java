// ECE 309 Lab 8 Online Hearts Game
// Neal O'Hara
// 11/9/13

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.Date;

public class GameClient extends UnicastRemoteObject implements GameClientInterface {
	
	Credentials id;
	NaiveHeartsPlayer player = null;
	
	public GameClient(String name, String password) throws RemoteException {
		super();
		//set credentials with name and password
		id = new Credentials(name, password);
	}
	@Override
	public Credentials getCredentials() throws RemoteException {
		return id;
	}
	@Override
	public void gameIsReady(HeartsGameInterface game) throws RemoteException{
		
		/*
		final HeartsGameInterface local_game = game;
		
		new Thread(new Runnable() {
			public void run() {
				try {
				System.out.println("My hand is:"		+local_game.getHand(id.getName()));
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HeartsGameException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
		*/
		
		player = new NaiveHeartsPlayer(id.getName(),game);
		new Thread(player).start();
	}
	
	
	@Override
	public void takeYourTurn() throws RemoteException{
		
		if(player==null)
			return;	
		synchronized(player) {
			player.notifyAll();
		}
		
	}
	
	//Either proved username and password on command line
	// or be prompted for them.
	public static void main(String[] args) throws RemoteException{
		
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "GamerServer program ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		//end name output
		
		//Initialize connection with rmi server
		
		GameServerInterface server = null;
		try{
			server = (GameServerInterface) Naming.lookup("rmi://localhost/OnlineHeartsGame");
		}catch(Exception e){
			e.printStackTrace();
			return;
		}
		
		
		//check for name and password,
		//prompt if not given
		if(args.length != 2){
			args = new String[2];
			Scanner terminalinput = new Scanner(System.in);
			System.out.println("Please provide Hearts Game Username:");
			args[0] = terminalinput.nextLine();
			System.out.println("Please provide Hearts Game Password:");
			args[1] = terminalinput.nextLine();
			System.out.println("Logging in...");
		}
		
		
		//Create GameClient
		GameClient client = new GameClient(args[0], args[1]);
		if(client == null){
			System.out.println("Debug:error client null");
			return;
		}
		if(client.getCredentials() == null){
			System.out.println("Debug: error cred = null");	
			return;
		}
		if(client.getCredentials().getName() ==null){
			System.out.println("Debug:error name = null");
			return;
		}
		
		try{
			server.register(client);
		}catch (Exception e){
			//e.printStackTrace();
			return;
		}
		
		
		
		
	}
}

