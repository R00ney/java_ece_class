// folowing sample code
// Neal O'Hara 9/4/13
//

import javax.swing.*;

public class SimpleMenu {

   private JFrame window = new JFrame("Simple GUI Window");
   private JMenuBar bar = new JMenuBar(); // menu bar
   private JMenu file = new JMenu("File");    // file menu
   private JMenu edit = new JMenu("Edit");  // edit menu
   private JMenuItem open = new JMenuItem("Open");   // open menu item
   private JMenuItem save = new JMenuItem("Save");   // save menu item

   public SimpleMenu() {

      // add menus to menu bar

      bar.add(file);  
      bar.add(edit);

      // add menu item to menu

      file.add(open);
      file.add(save);
      window.setJMenuBar(bar);

      window.setVisible(true);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setSize(800,600);

   }

   public static void main(String[] args) {

      new SimpleMenu();

   }

}
