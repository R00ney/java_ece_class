// Lab 2 American Idol Server
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/3/13


import java.util.*;
import java.net.*;
import java.util.Date;


public class VoteForAmericanIdolServer{
	
	public static void main  (String[] args) throws Exception 
	{
		int port_num = 2222;
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		
		String[] contestants = {"Contestants Fall 2013",
                        "A.B.Clemson(ABC)",
	                   "Boop E",
	                   "SUZIE Sumpter",
	                   "Mary Beth Butterworth",
	                   "Big Bubba Barnhill"};
	    
	    ArrayList<String> voters = new ArrayList<String>();
	    int[] voteTotals = new int[contestants.length];
	    
	    /*--------------------------------------------------------------------*/               
	                   
		//Start Program Output
		System.out.println(myname + newLine + "VoteForAmericanIdolsServer Program");
		System.out.println("This session started " + new Date() + newLine);
		
		if(args.length == 0){
			System.out.println("A port number may optionally be provided as a command line parameter." + newLine + "If the optional port number is not provided, port 2222 is used by default." + newLine + "If you wish to use a specific port, please restart this program with the port number specified as a parameter");	
		} else if (args.length > 1) {
			System.out.println("Only one parameter, the optional port number, is allowed." + newLine + "If the optional port number is not provided, port 2222 is used by default." + newLine + "Please retry with one or fewer parameters.");
			return;
		} else {
			port_num = Integer.parseInt(args[0]);
			
			if( (port_num < 1000) || (port_num > 9999) ){
				System.out.println("The provide port number is out of range." + newLine + "Please retry the program with a port number bewteen 1000 - 9999");
				return;
			}
			
			System.out.println("Port " + String.valueOf(port_num) + " will be used.");
				
		}//end if args
		
		//Start Server's socket
		DatagramSocket receiveSocket = new DatagramSocket(port_num);
		
		System.out.println("VoteForAmericanIdolServer is up at "
	         + InetAddress.getLocalHost().getHostAddress()
                 + " on port " + receiveSocket.getLocalPort());
        
                 
        //Start Server's buffer
        byte[] receiveBuffer = new byte[8192];
        
        DatagramPacket receivePacket = new 	DatagramPacket(receiveBuffer,receiveBuffer.length);
        
        while(true){
        
        	receiveSocket.receive(receivePacket); //wait for packet to come
        	
        	String newVote = new String(receiveBuffer, 0, receivePacket.getLength());
        	
        	String senderAddress = ((InetSocketAddress) receivePacket.getSocketAddress()).getHostName();
        	
        	if(voters.contains(senderAddress)){
        		System.out.println(senderAddress + " has already voted.");
        		continue; //skip this recipient
        	}
        	else {
        		voters.add(senderAddress);
        		System.out.println("Voters so far: " + voters);
        		//show address for debug
        	}
        	
        	int index = Integer.parseInt(newVote); 
        	voteTotals[index]++;
        	
        	for(int i=1; i< contestants.length; i++) {
        		System.out.print(contestants[i] + " " + voteTotals[i] + ", ");
        	}
        	System.out.println(); //for newline
        	
        
        }//end while true
	
	}//End main	
	
}//End Class Vote_..._Server
