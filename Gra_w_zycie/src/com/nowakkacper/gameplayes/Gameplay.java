package com.nowakkacper.gameplayes;

import com.nowakkacper.gamepanels.GamePanel;

import javax.swing.*;

public class Gameplay {
    private final GamePanel gamePanel;
    private Timer panelTimer;

    public Gameplay(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        createPanelTimer();
    }

    public void pause(){
        panelTimer.stop();
    }

    public void resume(){
        panelTimer.start();
    }

    private void createPanelTimer() {
        panelTimer = new Timer(500, event -> {
            lifeControl();
            gamePanel.repaint();
        });
        panelTimer.start();
    }

    private void lifeControl() {
        for(int i=0; i<gamePanel.getHowManyRow()*gamePanel.getHowManyColumns(); i++){
            int n = howManyAliveNeighbours(i);

            if(gamePanel.getCurrentGameBoardField(i)){
                if(n == 2 || n == 3) gamePanel.setFutureGameBoardField(i, true);
                else gamePanel.setFutureGameBoardField(i, false);
            }
            else {
                if(n == 3)
                    gamePanel.setFutureGameBoardField(i, true);
            }
        }

        gamePanel.copyFutureToPresets();

        if(gamePanel.isEmpty()) {
            panelTimer.stop();
        }
    }

    private byte howManyAliveNeighbours(int i) {
        byte howManyAliveNeighbours = 0;

        if(i == 0)
            return topLeftCorner(howManyAliveNeighbours, i);

        else if(i == gamePanel.getHowManyColumns()-1)
            return topRightCorner(howManyAliveNeighbours, i);

        else if(i == (gamePanel.getHowManyRow()-1)* gamePanel.getHowManyColumns())
            return bottomLeftCorner(howManyAliveNeighbours, i);

        else if(i == gamePanel.getHowManyRow()*gamePanel.getHowManyColumns()-1)
            return bottomRightCorner(howManyAliveNeighbours, i);

        else if(i > 0 && i < gamePanel.getHowManyColumns()-1)
            return topEdge(howManyAliveNeighbours, i);

        else if(i > (gamePanel.getHowManyRow()-1)* gamePanel.getHowManyColumns() &&
                i < gamePanel.getHowManyRow()*gamePanel.getHowManyColumns()-1)
            return bottomEdge(howManyAliveNeighbours, i);

        else if(i%gamePanel.getHowManyColumns() == 0)
            return leftEdge(howManyAliveNeighbours, i);

        else if(i% gamePanel.getHowManyColumns() == gamePanel.getHowManyColumns()-1)
            return rightEdge(howManyAliveNeighbours, i);

        else return centerPartOfBoard(howManyAliveNeighbours, i);
    }

    private byte rightEdge(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        return n;
    }

    private byte leftEdge(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()+1))
            n++;

        return n;
    }

    private byte bottomEdge(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        return n;
    }

    private byte topEdge(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()+1))
            n++;

        return n;
    }

    private byte centerPartOfBoard(byte n, int i){

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()+1))
            n++;

        return n;
    }

    private byte topLeftCorner(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()+1))
            n++;

        return n;
    }

    private byte bottomRightCorner(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        return n;
    }

    private byte bottomLeftCorner(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()))
            n++;

        if(gamePanel.getCurrentGameBoardField(i-gamePanel.getHowManyColumns()+1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+1))
            n++;

        return n;
    }

    private byte topRightCorner(byte n, int i) {
        if(gamePanel.getCurrentGameBoardField(i-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()-1))
            n++;

        if(gamePanel.getCurrentGameBoardField(i+gamePanel.getHowManyColumns()))
            n++;

        return n;
    }

}
