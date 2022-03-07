package NowakKacper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MyMenuBar extends JMenuBar implements ActionListener {

    private final JMenuItem newGame;
    private final JMenuItem exit;
    private final MyPanel panel;

    public MyMenuBar(MyPanel panel){
        this.panel = panel;
        JMenu gameMenu = new JMenu("Game");
        JMenu settingsMenu = new JMenu("Settings");

        newGame = new JMenuItem("New Game");
        exit = new JMenuItem("Exit");

        newGame.addActionListener(this);
        exit.addActionListener(this);

        gameMenu.setMnemonic(KeyEvent.VK_G);
        settingsMenu.setMnemonic(KeyEvent.VK_S);
        newGame.setMnemonic(KeyEvent.VK_N);
        exit.setMnemonic(KeyEvent.VK_E);

        gameMenu.add(newGame);
        gameMenu.add(exit);

        this.add(gameMenu);
        this.add(settingsMenu);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == newGame){
            panel.newGame(true);
        }

        if(event.getSource() == exit){
            System.exit(0);
        }
    }
}