package NowakKacper.GameWindows;

import NowakKacper.GameMenus.GameMenuBar;
import NowakKacper.GamePanels.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow(){
        GamePanel panel = new GamePanel();

        GameMenuBar menuBar = new GameMenuBar(panel);

        add(panel);
        setJMenuBar(menuBar);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Arkanoid");
        setSize(MainWindow.getMonitorSize());
        setVisible(true);
    }

    public static Dimension getMonitorSize(){
        return Toolkit.getDefaultToolkit().getScreenSize();
    }
}
