package com.nowakkacper.MenuBars;

import com.nowakkacper.Panels.GamePanel;

import javax.swing.*;

public class GameMenuBar extends JMenuBar {

    private final GamePanel gamePanel;
    private final JMenuItem newGame;
    private final JMenuItem exit;
    private final JMenuItem aboutGame;

    public GameMenuBar(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");

        newGame = new JMenuItem("New game");
        exit = new JMenuItem("Exit");
        aboutGame = new JMenuItem("About game");

        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem easyMode = new JRadioButtonMenuItem("Easy");
        JRadioButtonMenuItem mediumMode = new JRadioButtonMenuItem("Medium");
        JRadioButtonMenuItem hardMode = new JRadioButtonMenuItem("Hard");
        easyMode.setSelected(true);

        group.add(easyMode);
        group.add(mediumMode);
        group.add(hardMode);

        settingsMenu.add(easyMode);
        settingsMenu.add(mediumMode);
        settingsMenu.add(hardMode);

        gameMenu.add(newGame);
        gameMenu.add(exit);
        helpMenu.add(aboutGame);

        this.add(gameMenu);
        this.add(settingsMenu);
        this.add(helpMenu);
    }
}
