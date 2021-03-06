//Lab 4
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu

package edu.ncsu.ngohara.mobilechatroomclient;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.EditText;
import android.content.Intent;

import android.os.AsyncTask;
import java.net.Socket;

import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.Date;


public class SignInActivity extends Activity implements OnClickListener {

public Socket socket;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
      //Start Program Output
      		String newLine = System.getProperty ( "line.separator" );
      		String myname = "Neal O'Hara" + newLine + "ngohara";
      		String programName = "ChatRoomClient ";
      		System.out.println(myname + newLine + programName + "Program");
      		System.out.println("This session started " + new Date() + newLine);
      		
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        
        ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setVisibility(ProgressBar.INVISIBLE);
        
        TextView errorTextField = (TextView) findViewById(R.id.errorTextField);
        errorTextField.setVisibility(TextView.INVISIBLE);
        
        
    }

 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }
    
    public void onClick(View arg0){
    	System.out.println("Got a click");

     
        //create username
        EditText usernameEdit = (EditText) findViewById(R.id.usernameEdit);
		String username_text = usernameEdit.getText().toString();
		
		//make sure username is functional
		if(username_text.equals("")){
			TextView errorTextField = (TextView) findViewById(R.id.errorTextField);
            errorTextField.setText("Please enter a username...");
            errorTextField.setVisibility(TextView.VISIBLE);
			
		} else {
	    	ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
	        progressBar1.setVisibility(ProgressBar.VISIBLE);
	        Button signInButton = (Button) findViewById(R.id.signInButton);
	        signInButton.setEnabled(false);
		        
			ChatRoomApplication app = (ChatRoomApplication) getApplication();
			app.username_text = username_text;
			
	        //start connection thread, which creates socket and moves to chat activity
			new ConnectionWorker().execute();
		}
    }
    
    
    
    
    // worker thread handeling chat client
    public class ConnectionWorker extends AsyncTask<String,Void,String> {
        
    	//Conection worker main
    	@Override
        protected String doInBackground(String... arg0) {
        	       	
    		ObjectOutputStream oOutStream = null;
    		ObjectInputStream oInStream = null;
    		
    		ChatRoomApplication app = (ChatRoomApplication) getApplication();
    		app.socket = socket;
    		app.oOutStream = oOutStream;
    		app.oInStream = oInStream;
    		
    		String username_text = app.username_text;
        	
    		String  serverAddress = app.serverAddress;
			int     serverPort= app.serverPort;
			try {
				// socket is declared in parent class 
				socket = new Socket(serverAddress,serverPort);
			}
			catch (Exception e)
			{
				String t2 = "Not connected!" + e.toString();
				return t2; // w/o connecting to server!                                                                                                                                               
			}
			
			
			//connect to DataOutStream dos or report error
			// Pass name to server to test username ok
			//create object streams (in/out) or report error
			Object reply_object = null;
			try{
				oOutStream = new ObjectOutputStream(socket.getOutputStream());
				//write the username to server
				oOutStream.writeObject(username_text);
				
				oInStream = new ObjectInputStream(socket.getInputStream());
				//wait and get reply
				reply_object = oInStream.readObject();
			} catch (Exception e) {
				//connection error
				System.out.println("Could not connect with object streams");
				System.out.println(e );
				String objectio_bust = "Could not connect with object streams" + e.toString();
				return objectio_bust;
			}
			//save vars for later use
			app.socket = socket;
    		app.oOutStream = oOutStream;
    		app.oInStream = oInStream;
    		
			//Check Server response for ACCEPT for name ok,
			//with ACCEPT, Chat Window visible, Sign In invisible,
			final String r_string = reply_object.toString();
			if( r_string.equals( "ACCEPT") ){
				//do nothing, connected and name appropriate
				System.out.println("Username Accepted");
			}
			//if not, then keep sign in window open, and alert
			// user of duplicate name
			else {
				System.out.println(r_string);
				return "Username Not Accepted";
		
			}
			
			//Everything worked ok, so return Connected
			return "Connected";
        }//end doInBackground

        @Override
        //clean and reset sign in window
        protected void onPostExecute(String val) {
        	ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
            progressBar1.setVisibility(ProgressBar.INVISIBLE);
            Button signInButton = (Button) findViewById(R.id.signInButton);
            signInButton.setEnabled(true);
            
            if(val.equals("Connected")){
            	System.out.println("Successfully logged in.");
            	
            	Intent chatRoom = new Intent(SignInActivity.this,ChatRoomActivity.class);
            	startActivity(chatRoom);
            	
            
            } else if(val.equals("Username Not Accepted")){
            	System.out.println("Error: Invalid Username");
                TextView errorTextField = (TextView) findViewById(R.id.errorTextField);
                errorTextField.setText("Duplicate Username, Please Choose Another.");
                errorTextField.setVisibility(TextView.VISIBLE);
        	}
            else {
            	System.out.println("Error: Could not log in.");
                TextView errorTextField = (TextView) findViewById(R.id.errorTextField);
                errorTextField.setText("Error, Could not Log in.");
                errorTextField.setVisibility(TextView.VISIBLE);
            }
        }//end on PostExecute
    }//end connection worker

    
 
}//end signInActivity
