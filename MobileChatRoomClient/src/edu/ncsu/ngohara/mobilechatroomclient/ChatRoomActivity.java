//Lab 4
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
package edu.ncsu.ngohara.mobilechatroomclient;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;


import java.io.IOException;
import java.util.Date;
import android.os.AsyncTask;

public class ChatRoomActivity extends Activity implements OnClickListener{

	public String newLine = System.getProperty ( "line.separator" );
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//handles rotating screen
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat_room);
		ChatRoomApplication app = (ChatRoomApplication)getApplication();
		if (app.socket==null)
			finish();
		
        Button sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(this);
        
		//logged in messages
		TextView chatTextArea = (TextView) findViewById(R.id.chatTextArea);
		chatTextArea.append(app.username_text + " has logged in.");
		Date d = new Date();
		chatTextArea.append("Entered chat room at "+d.toString()+ newLine);
		
		System.out.println("Successfully Changed to Chat.");
		
		//handle display server messages
		Runnable r = new Runnable() {
			public void run() {
				ChatRoomApplication app = 
		(ChatRoomApplication)getApplication();
				try {
					while (true) {
					// get messages from server and display them
						final String s = (String) app.oInStream.readObject();
						final TextView chatTextArea = (TextView) findViewById(R.id.chatTextArea);
						chatTextArea.post(new Runnable() {
							public void run() {
								chatTextArea.append(s + newLine);
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(r).start(); // start the run method in another thread
		
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chat_room, menu);
		return true;
	}
	

	//handles sending and printing text to chat window
	public void onClick(View arg0){

		System.out.println("Send Button Clicked");

		
		//handles sendding messages
		TextView editMessage = (TextView) findViewById(R.id.editMessage);
		//System.out.println("Debug message: " + editMessage.getText().toString() );
		
		//check for blank
		if(editMessage.getText().toString().equals("")){
			//do nothing for blank send
		} else {
			Button sendButton = (Button) findViewById(R.id.sendButton);
			sendButton.setEnabled(false);
			new SendMessage().execute(editMessage.getText().toString());
		}

	}
	
	public class SendMessage extends AsyncTask<String,Void,Boolean> {
		@Override
		protected Boolean doInBackground(String... params) {
			// Runs on worker thread
			try {

				//System.out.println("Sending Message ...");
				//System.out.println(params[0]);
				ChatRoomApplication app = (ChatRoomApplication) getApplication();
				app.oOutStream.writeObject(params[0]); // message is passed as argument to AsyncTask
			} catch (Exception e){
				System.out.println("Debug: Failed Send:" + e.toString());
				return false;
			}
			
			return true;
		}
		@Override
		protected void onPostExecute(Boolean val) {	
		// Runs on UI thread	
			Button sendButton = (Button) findViewById(R.id.sendButton);
			sendButton.setEnabled(true);
			if (val) {			
			TextView editMessage = (TextView) 
			findViewById(R.id.editMessage);
				editMessage.setText("");
			}
			else {
				TextView chatTextArea = (TextView) findViewById(R.id.chatTextArea);
				chatTextArea.append("Message Failed to Send" + newLine);
			}
		}// post execute
		
	}//end async
	
	// handles logging out and closing the chat message app
	public void onDestroy() {
		super.onDestroy();
		ChatRoomApplication app = (ChatRoomApplication)getApplication();
		if (app.socket != null){
	        try {
	        	app.socket.close();
	        	if (app.oOutStream != null){
	        		app.oOutStream.close();
	        	}
	        } catch (IOException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   // Reset the socket
		   app.socket = null;
		   app.oInStream = null;
		   app.oOutStream = null;
	     }
	}

}
