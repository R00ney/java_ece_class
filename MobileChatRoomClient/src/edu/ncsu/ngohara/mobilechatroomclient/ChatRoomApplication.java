package edu.ncsu.ngohara.mobilechatroomclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import android.app.Application;

public class ChatRoomApplication extends Application {
	
	public Socket socket;	
	public ObjectOutputStream oOutStream;
	public ObjectInputStream oInStream ;
	public String username_text;
	public String  serverAddress="192.168.7.101";
	public int     serverPort=45000;
	
}
