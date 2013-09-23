package edu.ncsu.ngohara.mobilechatroomclient;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.ProgressBar;


public class SignInActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        
        Button signInButton = (Button) findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        
        ProgressBar progressBar1 = (ProgressBar) findViewById(R.id.progressBar1);
        progressBar1.setVisibility(ProgressBar.INVISIBLE);
        
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
    
    
}