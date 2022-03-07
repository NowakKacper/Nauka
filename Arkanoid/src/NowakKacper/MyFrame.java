package NowakKacper;

import javax.swing.*;

public class MyFrame extends JFrame {

    private MyPanel panel;
    private MyMenuBar menuBar;

    public MyFrame(){

        panel = new MyPanel();

        menuBar = new MyMenuBar(panel);
        this.setJMenuBar(menuBar);

        this.setTitle("Arkanoid");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
