package edu.ncsu.ngohara.mobilechatroomclient;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.AsyncTask;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.DataInputStream;



public class SignInActivity extends Activity implements OnClickListener {

private Socket socket;	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
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
    	ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setVisibility(ProgressBar.VISIBLE);
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setEnabled(false);
     
        
    }
    
    
    
    
    
    // worker thread handeling chat client
    public class ConnectionWorker extends AsyncTask<String,Void,String> {
        
    	//Conection worker main
    	@Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub
        	
        	new SocketConnect().execute();
            return null;
        }

        @Override
        //clean and reset sign in window
        protected void onPostExecute(String val) {
        	ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
            progressBar1.setVisibility(ProgressBar.INVISIBLE);
            Button signInButton = (Button) findViewById(R.id.signInButton);
            signInButton.setEnabled(true);
            
            if(val.equals("Connected")){
            	System.out.println("Successfully logged in.");
            
            } else {
            	System.out.println("Error: Could not log in.");
                TextView errorTextField = (TextView) findViewById(R.id.errorTextField);
                errorTextField.setText("Error, Could not Log in.");
                errorTextField.setVisibility(TextView.VISIBLE);
            }
        }//end on PostExecute
    }//end connection worker

    
    
  //to make socket connection and check username
    private class SocketConnect extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String             serverAddress="10.139.75.144";
			int                serverPort=1234;
			try {
				// socket is declared in parent class 
				socket = new Socket(serverAddress,serverPort);
				DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
				dos.writeUTF(params[0]);
			}
			catch (Exception e)
			{
				String t2 = "Not connected!" + e.toString();
				return t2; // w/o connecting to server!                                                                                                                                               
			}

			return "Connected";
			
			
			
			/*
			 			//Check Server response for ACCEPT for name ok,
			//with ACCEPT, Chat Window visible, Sign In invisible,
			final String r_string = r.toString();
			if( r_string.equals( "ACCEPT") ){
				Runnable openChat = new Runnable() {
					public void run(){
						chatTextArea.setText(""); //clear!
						signInWindow.setVisible(false);
						chatWindow.setVisible(true);
						username.setEditable(false);
					}
				};
				SwingUtilities.invokeLater(openChat);
			}
			//if not, then keep sign in window open, and alert
			// user of duplicate name
			else {
//				System.out.println(r_string);
				Runnable report_error = new Runnable(){
					public void run() {
						username.setEditable(true);
						status.setText( r_string );
						status.setForeground(Color.RED);
						signInWindow.setSize(500, 110);
					}
				};
				SwingUtilities.invokeLater(report_error);
			}
			  */
			 
		}//end sockect do in Background 
    }// end socket connect
    
}
