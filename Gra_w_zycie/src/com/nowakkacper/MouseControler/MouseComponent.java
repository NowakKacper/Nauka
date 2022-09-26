package com.nowakkacper.MouseControler;

import com.nowakkacper.gamepanels.GamePanel;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseComponent extends JComponent {
    private final GamePanel gamePanel;

    public MouseComponent(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        addMouseListener(new MouseHandler());
        //addMouseMotionListener(new MouseMotionHander());
    }

    private int find(int x, int y){

        int whichRow = (y - gamePanel.getDISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL())
                / gamePanel.getSIDE_LENGTH_OF_THE_CELL();
        int whichColumn = (x - gamePanel.getDISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl())
                / gamePanel.getSIDE_LENGTH_OF_THE_CELL();
        return whichRow * gamePanel.getHowManyColumns() + whichColumn;
    }

    private class MouseHandler extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent event){
            if(!gamePanel.getIsItLive()) {
                int index = find(event.getX(), event.getY());
                gamePanel.startingBoardCreator(index);
            }
        }
    }
/*
    private class MouseMotionHander implements MouseMotionListener {

        @Override
        public void mouseDragged(MouseEvent event) {
            if(current != null){
                int x = event.getX();
                int y = event.getY();

                //Przeciągnięcie aktualnego kwadratu w celu wyśrodkowania go w punkcie (x,y).
                current.setFrame(x - SIDE_LENGTH/2, y - SIDE_LENGTH/2, SIDE_LENGTH, SIDE_LENGTH);
                repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent event) {}
    }*/
}
