// Lab 5 Chat Room Server
// Neal O'Hara
// ngohara@ncsu.edu
//  9/28/13


import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;
import java.lang.Thread;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Collection;


public class ChatRoomServer implements Runnable{
	
	//Networking fields
	private Socket socket = null;
	private String serverIPAddress = "localhost";
	private int serverPort = 45000;
	private ServerSocket ss;
	private ObjectInputStream oInStream;
	private ObjectOutputStream oOutStream;
	
	//Other
	private String newLine = System.getProperty ( "line.separator" );
	private static ImageIcon icon = null;
	
	// ConcurrentHashMap fields
	ConcurrentHashMap<String,ObjectOutputStream> clients;
	
	/*------------------------------------------------------------------------*/
	
	// Each client thread we make enters here!
	public void run(){
	//local vars for each client's connection
	 Socket             s          = null; // initialize
	 ObjectOutputStream oos        = null; // local pointer
	 ObjectInputStream  ois        = null; // variables
	 String             clientName = null; // to zero

		 try {
			s = ss.accept(); // wait for a client to connect
			new Thread(this).start();//make a thread to connect the NEXT client

			 } 
		 catch(Exception e)
		 {
			System.out.println("Client connection failure: " + e); 
		 //now handle connection
		 if (s.isConnected()){

			try {s.close();}         // hang up
			catch(IOException ioe){} // already hung! 
			return; // *** kill this client thread *** 
			}//end if connected
			
		 }// end of catch by simply returning to its Thread object!

		 
		//init null
		Object firstMessage =null;
		//object streams and first message	
		try{
			ois = new ObjectInputStream (s.getInputStream());
			firstMessage = ois.readObject();//1st msg from client
			oos = new ObjectOutputStream(s.getOutputStream());
		} catch (Exception ioe){} //Didn't receive valid data, handled below
			
		// calculate user name
		if (firstMessage instanceof String)
		   clientName = ((String) firstMessage).trim().toUpperCase();
		else // this caller is not a ChatRoomClient! 
		{
			try{
				oos.writeObject("Invalid protocol. " // 1) send err msg
						   + "Are you calling the right address and port?");
				oos.close();                         // 2) hang up
			} catch (IOException ioe){} //nothing left to do
				System.out.println("Invalid 1st message received: " + firstMessage);
				return;                              // 3) kill this client thread.
		}
				
				
		/* add check for existing user name */
		// clientName has to not be null (from errors above)
		if (clients.containsKey(clientName))  {
		
		try{
		   // Sorry Charlie!
		   oos.writeObject("Duplicate name. Please rejoin with another name!");
		   oos.close(); // hang up
		   return;      // kill this client thread
		} catch (IOException ioe){} //nothing left to do
					
		} else if(clientName != null) {        // do "join processing"
		   try{
			   // Debug trace on server console
			   oos.writeObject("ACCEPT");
			   System.out.println(clientName + " is joining.");
			   // Tell the new client they're in!
			   oos.writeObject("Welcome to the chat room, " + clientName + "!");
			   // and tell everyone else that's already in the chat room
			   sendToAllClients("Welcome to " + clientName
							  + " who has just joined the chat room!");
			   // add name and socket to hash map
			   clients.put(clientName, oos); // key,object
			} catch (IOException ioe){} //nothing left to do
		}
				
				
		//handle chat messages from clients
		// Receive/Send Processing for this client
		try {
			while(true) {
			   //wait for MY client to say something
			   Object messageFromClient = ois.readObject(); 
			   if (messageFromClient instanceof String)
			   {
				 String outChat = clientName + " says: " + messageFromClient;
				 sendToAllClients(outChat);
			   }
			}//end while true
		} catch(IOException ioe) {
		   //Most likely, client left server
		   String closeChat = clientName + " is leaving the chat room.";
		   System.out.println(closeChat);
		   clients.remove(clientName);
		   sendToAllClients("Goodbye to " + clientName + " who has just left the chat room.");
		   //socket already closed
		   return; // kill this client thread.
		} catch(ClassNotFoundException cnfe) {}
		

		
	} // end of run
	
/*------------------------------------------------------------------------*/
	
	// sends messages via objects out to all known clients
	private synchronized void sendToAllClients(Object message){
	
		Collection<ObjectOutputStream> oosList = clients.values();
		ObjectOutputStream[] oosArray = oosList.toArray(new ObjectOutputStream[0]);
		for (ObjectOutputStream clientOOS : oosArray)
		{
			try {
			clientOOS.writeObject(message);
			System.out.println(message);
			}
			catch (IOException e) {} 
		}

	}
	
/*------------------------------------------------------------------------*/
	
	//Initializes a server and subsequent worker threads.
	public ChatRoomServer() throws IOException{
	    //create hashmap for storing clients connections
		clients = new ConcurrentHashMap<String,ObjectOutputStream>();
		
		ss = new ServerSocket(serverPort);
		System.out.println("Server Socket is up at "
	         + InetAddress.getLocalHost().getHostAddress()
                 + " on port " + ss.getLocalPort());
	
		//create new thread for server connection with client
		new Thread(this).start();//create and start a thread running in run()
	}// end server constructor

/*------------------------------------------------------------------------*/
	
	//Program's setup and handles starting events
	public static void main (String[] args) throws IOException {
	
		//Attempt to use custom Icon image
		URL icon_link = null;
		try {
			icon_link = new URL("https://github.com/R00ney/icon_image/blob/master/NGO.ico?raw=true");
		} catch (MalformedURLException murle) {
			System.out.println("Bad URL for icon image");
			System.out.println( murle );
		}
		icon = new ImageIcon(icon_link);
		
		
		//Start Program Output
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		String programName = "ChatRoomServer ";
		System.out.println(myname + newLine + programName + "Program");
		System.out.println("This session started " + new Date() + newLine);
		
		//Start server
		new ChatRoomServer();

		
	} //end main
	
	/*------------------------------------------------------------------------*/

}//end server class
