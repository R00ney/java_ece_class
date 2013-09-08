// folowing sample code
// Neal O'Hara 9/4/13
//

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class SimpleListener implements ActionListener { /* This class implements an ActionListener */

   private JFrame window = new 	JFrame("Simple GUI Window");
   private JMenuBar bar = new JMenuBar();
   private JMenu file = new JMenu("File");
   private JMenu edit = new JMenu("Edit");
   private JMenuItem open = new JMenuItem("Open");
   private JMenuItem save = new JMenuItem("Save");
   private JButton button_l = new JButton("left");
   private JButton button_r = new JButton("right");
   private JButton button_u = new JButton("up");
   private JButton button_d = new JButton("down");
   private String[] phone = {"1","2","3","4","5","6","7","8","9","#","0","*"};
   private JButton[] nums = new JButton[phone.length];
   
   public SimpleListener() {

      int i = 0;
      for(String s: phone){
      	JButton b = new JButton(s);
      	window.add(b);
      	nums[i] = b;
      	i++;
      }
   
      bar.add(file);
      bar.add(edit);
      
      file.add(open);
      file.add(save);
      window.setJMenuBar(bar);
      
      window.setLayout(new BorderLayout());
      window.add(button_l, BorderLayout.WEST);
      window.add(button_r, BorderLayout.EAST);
      window.add(button_u, BorderLayout.NORTH);
      window.add(button_d, BorderLayout.SOUTH);
      
      
      window.setVisible(true);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(800,600);

      open.addActionListener(this);  /* #2 Associate menu item with this ActionListener */
      save.addActionListener(this);  /* #2 Associate menu item with this ActionListener */
   }

  @Override
  public void actionPerformed(ActionEvent e) {

     if (e.getSource() == open) 
          System.out.println("User clicked on open");

     if (e.getSource() == save) 
          System.out.println("User clicked on save");

  }

   public static void main(String[] args) { 
      new SimpleListener();
   }

}
