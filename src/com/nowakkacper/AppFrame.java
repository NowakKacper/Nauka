package com.nowakkacper;

import javax.swing.*;

public class AppFrame extends JFrame {

    public AppFrame(){
        AppPanel panel = new AppPanel(this);

        this.add(panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Frequency Calculator");
        this.setSize(650, 450);
        this.setVisible(true);
    }

}
