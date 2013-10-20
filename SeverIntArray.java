import java.io.*;
import java.net.*;


public class ServerIntArray{

	public static void main(String[] args) {

    	int port=3000;
   	 

   	 
    	try {   
    	System.out.println("Debug; waiting for connect");	 
        	ServerSocket ss = new ServerSocket(port);	// possible exception    

        	while(true) {
   	 
            	Socket s = ss.accept(); // blocks until connection made by client
   	 System.out.println("Debug: made connection");
   	 
            	ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            	Object message = ois.readObject();  // exception could happen here
            	System.out.println(message.toString());
   	 
   	 System.out.println("Debug: Recieved");
            	ObjectOutputStream oos = new     ObjectOutputStream(s.getOutputStream());

            	int sum = 1;
   	 oos.writeObject(sum);
   	 System.out.println("Debug: Sent");
   	 
        	}   	 
    	} catch (Exception e) {
        	System.out.println("Something went wrong!");
        	e.printStackTrace();
    	}
	}

}

