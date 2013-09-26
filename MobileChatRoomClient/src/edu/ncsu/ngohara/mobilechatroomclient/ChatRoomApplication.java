package edu.ncsu.ngohara.mobilechatroomclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import android.app.Application;

public class ChatRoomApplication extends Application {
	
	public Socket socket;	
	public ObjectOutputStream oOutStream;
	public ObjectInputStream oInStream ;
}
