package com.nowakkacper.Window;

import com.nowakkacper.MenuBars.GameMenuBar;
import com.nowakkacper.Panels.GamePanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        GamePanel panel = new GamePanel();

        GameMenuBar menuBar = new GameMenuBar(panel);

        add(panel);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Snake");
        setSize(800+15, 800+15);
        setVisible(true);
        setResizable(false);
        setLocationRelativeTo(null);
    }

}
