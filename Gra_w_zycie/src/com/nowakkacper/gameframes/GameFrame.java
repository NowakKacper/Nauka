package com.nowakkacper.gameframes;

import com.nowakkacper.MouseControler.MouseComponent;
import com.nowakkacper.gamepanels.GamePanel;
import com.nowakkacper.menubars.GameMenuBar;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(){
        GamePanel panel = new GamePanel();

        GameMenuBar menuBar = new GameMenuBar(panel);

        add(panel);
        add(new MouseComponent(panel));
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("The game of life");
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        setVisible(true);
    }

}
