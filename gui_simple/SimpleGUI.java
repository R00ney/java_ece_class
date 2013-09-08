// folowing sample code
// Neal O'Hara 9/4/13
//

import javax.swing.*;

public class SimpleGUI {

    private JFrame window = new JFrame("Simple GUI Window");

    public SimpleGUI() {

        // TODO Auto-generated constructor stub

        window.setVisible(true);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setSize(800,600);

    }

    public static void main(String[] args) { 

        new SimpleGUI();

    }

}
