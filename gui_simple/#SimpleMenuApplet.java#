import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimpleMenuApplet extends JApplet implements ActionListener {

   private JMenuBar bar = new JMenuBar();
   private JMenu file = new JMenu("File");
   private JMenu edit = new JMenu("Edit");
   private JMenuItem open = new JMenuItem("Open");
   private JMenuItem save = new JMenuItem("Save");

   public void init () {  //browswer calls here for applet startup

      bar.add(file);
      bar.add(edit);

      file.add(open);
      file.add(save);
      setJMenuBar(bar);

      setSize(800,600);

      open.addActionListener(this);
      save.addActionListener(this);
   }

   @Override
   public void actionPerformed(ActionEvent e) {
      System.out.println("Menu item clicked");
   }
}
