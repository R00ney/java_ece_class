//client

import java.net.*;
import java.io.*;

public class SendMessage {
	public static void main(String[] args) {

 String IPaddress;
 
if(args.length > 0) { 
	IPaddress = args[0]; 
} else { 
	IPaddress = "localhost"; 
}

int port = 1234;
String message = "From Neal, if_then Succes!";

try {
// This could cause an exception
 Socket s = new Socket(IPaddress,port); // connect to server at IPaddress & port
// make a DataOutputStream to format data over the Socket (network stream)
 DataOutputStream dos = new DataOutputStream(s.getOutputStream());
dos.writeUTF(message); // this could cause an exception
dos.close(); // hang up on the server
} catch (Exception e) {
 System.out.println("Something went wrong! Are you running the server?");
 e.printStackTrace();
}

}
}
