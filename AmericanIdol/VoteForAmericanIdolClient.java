// Lab 2 American Idol Server
// Neal O'Hara
// ngohara @ ngohara@ncsu.edu
// 9/7/13

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class VoteForAmericanIdolClient extends JApplet implements ActionListener
{
	// class variables
	JRadioButton contestant1 = new JRadioButton();
	JRadioButton contestant2 = new JRadioButton();
	JRadioButton contestant3 = new JRadioButton();
	JRadioButton contestant4 = new JRadioButton();
	JRadioButton contestant5 = new JRadioButton();
	
	ButtonGroup contestants = new ButtonGroup();
	JButton submitButton = new JButton("Submit");
	JLabel msgLabel = new JLabel("Select a contestant and press Submit");
	
	JPanel panel = new JPanel();
	

	//builds GUI
	public void init() { //the main for web apps
		
		String newLine = System.getProperty ( "line.separator" );
		String myname = "Neal O'Hara" + newLine + "ngohara";
		
		//Start Program Output
		System.out.println(myname + newLine + "VoteForAmericanIdolsServer Program");
		System.out.println("This session started " + new Date() + newLine);
		
			// Build the GUI
		panel.add(msgLabel);
		panel.add(contestant1);
		panel.add(contestant2);
		panel.add(contestant3);
		panel.add(contestant4);
		panel.add(contestant5);
		panel.add(submitButton);
		add(panel); // call JApplet to add to browser's window

		contestants.add(contestant1);
		contestants.add(contestant2);
		contestants.add(contestant3);
		contestants.add(contestant4);
		contestants.add(contestant5);
	
		submitButton.addActionListener(this); //get submnit button clicked
		
	} //end init()
	
	
	//Code that services GUI here
	public void actionPerformed(ActionEvent ae) {
	
	System.out.println("Debug: Submit Pushed");
	
	} //end actionPerformed

//No Main, because browser starts code

} // end class Vote...Client
