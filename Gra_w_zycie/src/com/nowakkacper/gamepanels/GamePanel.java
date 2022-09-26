package com.nowakkacper.gamepanels;

import com.nowakkacper.gameplayes.Gameplay;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private final int howManyColumns;
    private final int howManyRows;
    private final int PANEL_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int PANEL_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-80;
    private final int SIDE_LENGTH_OF_THE_CELL = 40;
    private Gameplay gameplay;
    private int[][] cellCoordinates;
    private final int DISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl;
    private final int DISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL;
    private boolean[] currentGameBoard;
    private boolean[] futureGameBoard;
    private boolean isItLive = false;

    public GamePanel(){
        howManyColumns = PANEL_WIDTH / SIDE_LENGTH_OF_THE_CELL;
        howManyRows = PANEL_HEIGHT / SIDE_LENGTH_OF_THE_CELL;

        DISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl =
                (PANEL_WIDTH - howManyColumns * SIDE_LENGTH_OF_THE_CELL) / 2;
        DISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL =
                (PANEL_HEIGHT - howManyRows * SIDE_LENGTH_OF_THE_CELL) / 2;

        createCells();

        //setting parameters of the frame
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
    }

    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        drawLivingCells(g2);
    }

    private void drawLivingCells(Graphics2D g2) {
        for(int i=0; i<howManyRows*howManyColumns; i++){
            if(currentGameBoard[i]){
                g2.setPaint(Color.green);
                g2.drawRect(cellCoordinates[i][0], cellCoordinates[i][1],
                        SIDE_LENGTH_OF_THE_CELL, SIDE_LENGTH_OF_THE_CELL);
            }
            else {
                g2.setPaint(Color.lightGray);
            }
            g2.fillRect(cellCoordinates[i][0], cellCoordinates[i][1],
                    SIDE_LENGTH_OF_THE_CELL, SIDE_LENGTH_OF_THE_CELL);
            g2.setPaint(Color.black);
            g2.drawRect(cellCoordinates[i][0], cellCoordinates[i][1],
                    SIDE_LENGTH_OF_THE_CELL, SIDE_LENGTH_OF_THE_CELL);
        }
    }

    private void createCells() {
        cellCoordinates = new int[howManyRows*howManyColumns][2];

        for(int i=0; i<howManyRows*howManyColumns; i++){
            if(i % howManyColumns == 0){

                if(i == 0) {
                    cellCoordinates[i][0] = DISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl;
                    cellCoordinates[i][1] = DISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL;
                }
                else {
                    cellCoordinates[i][0] = (PANEL_WIDTH - howManyColumns* SIDE_LENGTH_OF_THE_CELL) / 2;
                    cellCoordinates[i][1] = cellCoordinates[i-1][1] + SIDE_LENGTH_OF_THE_CELL;
                }
                continue;
            }
            cellCoordinates[i][0] = cellCoordinates[i-1][0] + SIDE_LENGTH_OF_THE_CELL;
            cellCoordinates[i][1] = cellCoordinates[i-1][1];
        }

        currentGameBoard = new boolean[howManyColumns*howManyRows];
        futureGameBoard = new boolean[howManyColumns*howManyRows];

        for(int i=0; i<howManyRows*howManyColumns; i++){
            currentGameBoard[i] = false;
        }
    }

    public void start() {
        gameplay = new Gameplay(this);
    }

    public void copyFutureToPresets() {
        currentGameBoard = futureGameBoard.clone();
    }

    public void startingBoardCreator(int i) {
        currentGameBoard[i] = !currentGameBoard[i];
        this.repaint();
    }

    public void restart(){
        gameplay.pause();
        clearBoard();
        setIsItLive(false);
    }

    private void clearBoard() {
        for(int i=0; i<howManyRows*howManyColumns; i++){
            currentGameBoard[i] = false;
            futureGameBoard[i] = false;
        }

        this.repaint();
    }

    public boolean getCurrentGameBoardField(int whichField){
        return currentGameBoard[whichField];
    }

    public void setFutureGameBoardField(int whichField, boolean isItAlive){
        futureGameBoard[whichField] = isItAlive;
    }

    public int getHowManyColumns() {
        return howManyColumns;
    }

    public int getHowManyRow() {
        return howManyRows;
    }

    public int getSIDE_LENGTH_OF_THE_CELL() {
        return SIDE_LENGTH_OF_THE_CELL;
    }

    public int getDISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl() {
        return DISTANCE_BETWEEN_CELL_AND_LEFT_AND_RIGHT_WALl;
    }

    public int getDISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL() {
        return DISTANCE_BETWEEN_CELL_AND_TOP_AND_BOTTOM_WALL;
    }

    public boolean getIsItLive() {
        return isItLive;
    }

    public void setIsItLive(boolean isItLive) {
        this.isItLive = isItLive;
    }

    public boolean isEmpty() {
        for(boolean b : currentGameBoard)
            if(b) return false;

        return true;
    }

    public Gameplay getGameplay() {
        return gameplay;
    }
}
