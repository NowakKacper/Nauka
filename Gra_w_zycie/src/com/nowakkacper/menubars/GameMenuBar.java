package com.nowakkacper.menubars;

import com.nowakkacper.gamepanels.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameMenuBar extends JMenuBar implements ActionListener {
    private final JMenuItem newGame;
    private final JMenuItem exit;
    private final JMenuItem aboutGame;
    private final JMenuItem startGame;
    private final JMenuItem stopGame;
    private final GamePanel gamePanel;
    private int p=1;

    public GameMenuBar(GamePanel gamePanel){
        this.gamePanel = gamePanel;
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");

        startGame = new JMenuItem("Start game");
        stopGame = new JMenuItem("Stop game");
        newGame = new JMenuItem("New game");
        exit = new JMenuItem("Exit");
        aboutGame = new JMenuItem("About game");

        startGame.addActionListener(this);
        stopGame.addActionListener(this);
        newGame.addActionListener(this);
        exit.addActionListener(this);
        aboutGame.addActionListener(this);

        gameMenu.setMnemonic(KeyEvent.VK_G);
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        startGame.setMnemonic(KeyEvent.VK_S);
        stopGame.setMnemonic(KeyEvent.VK_S);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);
        aboutGame.setMnemonic(KeyEvent.VK_A);

        gameMenu.add(startGame);
        gameMenu.add(stopGame);
        gameMenu.add(newGame);
        gameMenu.add(exit);
        helpMenu.add(aboutGame);

        this.add(gameMenu);
        this.add(settingsMenu);
        this.add(helpMenu);

        stopGame.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == startGame){
            startGame.setEnabled(false);
            stopGame.setEnabled(true);

            gamePanel.setIsItLive(true);
            if(p == 1) {
                p=2;
                gamePanel.start();
            }
            else gamePanel.getGameplay().resume();

            return;
        }

        if(event.getSource() == stopGame){
            stopGame.setEnabled(false);
            startGame.setEnabled(true);

            gamePanel.getGameplay().pause();
            return;
        }

        if(event.getSource() == aboutGame){
            JOptionPane.showMessageDialog(null,
                    "Point which cells are alive and then press start in menu bar." +
                            "\n        After that you can watch how magic is happening.");
            return;
        }

        if(event.getSource() == newGame){
            startGame.setEnabled(true);
            stopGame.setEnabled(false);
            gamePanel.restart();
            return;
        }

        if(event.getSource() == exit){
            System.exit(0);
        }
    }
}
