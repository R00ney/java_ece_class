//server

import java.net.*;
import java.io.*;

public class ReciveMessages {
	public static void main(String[] args) {

try {
 ServerSocket ss = new ServerSocket(1234); // possible exception
 System.out.println("Running at"+InetAddress.getLocalHost().getHostAddress()+
 " and port 1234");
 while(true) {
 Socket s = ss.accept(); // blocks until connection made by client
 DataInputStream dis = new DataInputStream(s.getInputStream());
 String message = dis.readUTF(); // exception could happen here
 System.out.println(message);
}
} catch (Exception e) {
 System.out.println("Something went wrong!");
e.printStackTrace();
}
}


}
