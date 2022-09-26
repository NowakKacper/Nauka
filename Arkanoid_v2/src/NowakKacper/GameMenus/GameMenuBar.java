package NowakKacper.GameMenus;

import NowakKacper.GamePanels.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class GameMenuBar extends JMenuBar implements ActionListener {
    private final JMenuItem newGame;
    private final JMenuItem exit;
    private final JMenuItem aboutGame;
    private final GamePanel panel;

    public GameMenuBar(GamePanel panel){
        this.panel = panel;
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");
        JMenu helpMenu = new JMenu("Help");

        newGame = new JMenuItem("New game");
        exit = new JMenuItem("Exit");
        aboutGame = new JMenuItem("About game");

        newGame.addActionListener(this);
        exit.addActionListener(this);
        aboutGame.addActionListener(this);

        gameMenu.setMnemonic(KeyEvent.VK_G);
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        helpMenu.setMnemonic(KeyEvent.VK_H);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);
        aboutGame.setMnemonic(KeyEvent.VK_A);

        gameMenu.add(newGame);
        gameMenu.add(exit);
        helpMenu.add(aboutGame);

        this.add(gameMenu);
        this.add(settingsMenu);
        this.add(helpMenu);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == aboutGame){
            //TODO
            System.out.println("Opis gry");
        }

        if(event.getSource() == newGame){
            panel.getGameplay().newMap(true);
        }

        if(event.getSource() == exit){
            System.exit(0);
        }
    }
}
