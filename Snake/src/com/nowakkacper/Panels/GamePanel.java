package com.nowakkacper.Panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class GamePanel extends JPanel {

    private final int PANEL_HEIGHT = 800;
    private final int PANEL_WIDTH = 800;
    private final int SIDE_LENGTH_OF_SNAKE = 40;
    private int[][] snakeArray;
    private int howLongIsSnake = 3;
    private int appleX = 3*SIDE_LENGTH_OF_SNAKE;
    private int appleY = 3*SIDE_LENGTH_OF_SNAKE;
    private JLabel label;
    private Timer labelTimer;
    private final Timer panelTimer;
    char direction = 'r';
    private int score = 0;
    private final int HOW_MANY_BODY_PARTS = (PANEL_HEIGHT*PANEL_WIDTH) / (SIDE_LENGTH_OF_SNAKE * SIDE_LENGTH_OF_SNAKE);

    public GamePanel() {
        //setting parameters of the frame
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setPreferredSize(new Dimension(PANEL_WIDTH+15, PANEL_HEIGHT+15));
        this.setBackground(Color.lightGray);

        createInfoLabel();

        createSnake();

        panelTimer = new Timer(100, event -> gameControl());

        createKeyBindings();

    }

    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        int j=0;
        while(j < PANEL_WIDTH){
            g2.setPaint(Color.black);
            g2.drawLine(j,0, j, 800);
            g2.drawLine(0,j, 800, j);
            j+= 40;
        }

        for(int i=howLongIsSnake-1; i>=0; i--){
            g2.setPaint(Color.green);
            g2.fillRect(snakeArray[i][0], snakeArray[i][1],
                    SIDE_LENGTH_OF_SNAKE, SIDE_LENGTH_OF_SNAKE);
            g2.setPaint(Color.black);
            g2.drawRect(snakeArray[i][0], snakeArray[i][1],
                    SIDE_LENGTH_OF_SNAKE, SIDE_LENGTH_OF_SNAKE);
        }

        g2.setPaint(Color.red);
        g2.fillOval(appleX, appleY, SIDE_LENGTH_OF_SNAKE, SIDE_LENGTH_OF_SNAKE);
        g2.setPaint(Color.black);
        g2.drawOval(appleX, appleY, SIDE_LENGTH_OF_SNAKE, SIDE_LENGTH_OF_SNAKE);
    }

    private void gameControl() {
        if(didSnakeAteApple()) generateApple();

        move();

        didSnakeHitTheWall();

        didSnakeByteHimself();

        repaint();
    }

    private void didSnakeByteHimself() {
        for(int i=howLongIsSnake; i>0; i--){
            if(snakeArray[i][0] == snakeArray[0][0] && snakeArray[i][1] == snakeArray[0][1])
                endGame();
        }
    }

    private boolean didSnakeAteApple() {
        if(snakeArray[0][0] == appleX && snakeArray[0][1] == appleY){
            howLongIsSnake++;
            score++;
            return true;
        }
        return false;
    }

    private void generateApple() {
        Random random = new Random();
        appleX = random.nextInt(PANEL_WIDTH) / SIDE_LENGTH_OF_SNAKE * SIDE_LENGTH_OF_SNAKE;
        appleY = random.nextInt(HOW_MANY_BODY_PARTS) / SIDE_LENGTH_OF_SNAKE * SIDE_LENGTH_OF_SNAKE;
    }

    private void didSnakeHitTheWall() {
        if(snakeArray[0][0] > PANEL_WIDTH ||
                snakeArray[0][0] < 0 ||
                snakeArray[0][1] > PANEL_HEIGHT ||
                snakeArray[0][1] < 0) endGame();
    }

    private void endGame() {
        panelTimer.stop();
        JOptionPane.showMessageDialog(this, "You lost, your score was: " + score);
        score = 0;
        howLongIsSnake = 3;
        createSnake();
        labelTimer.start();
        direction = 'r';
    }

    private void move() {
        for(int i=howLongIsSnake-1; i>=0; i--){
            if(i == 0){
                switch (direction){
                    case 'l':
                        snakeArray[i][0] = snakeArray[i+1][0]-SIDE_LENGTH_OF_SNAKE;
                        snakeArray[i][1] = snakeArray[i+1][1];
                        break;
                    case 'r':
                        snakeArray[i][0] = snakeArray[i+1][0]+SIDE_LENGTH_OF_SNAKE;
                        snakeArray[i][1] = snakeArray[i+1][1];
                        break;
                    case 'u':
                        snakeArray[i][0] = snakeArray[i+1][0];
                        snakeArray[i][1] = snakeArray[i+1][1]-SIDE_LENGTH_OF_SNAKE;
                        break;
                    case 'd':
                        snakeArray[i][0] = snakeArray[i+1][0];
                        snakeArray[i][1] = snakeArray[i+1][1]+SIDE_LENGTH_OF_SNAKE;
                }
                continue;
            }

            snakeArray[i][0] = snakeArray[i-1][0];
            snakeArray[i][1] = snakeArray[i-1][1];
        }
    }

    private void createSnake() {
        snakeArray = new int[HOW_MANY_BODY_PARTS][2];
        snakeArray[2][0] = 0;
        snakeArray[2][1] = 0;

        snakeArray[1][0] = SIDE_LENGTH_OF_SNAKE;
        snakeArray[1][1] = 0;

        snakeArray[0][0] = 2*SIDE_LENGTH_OF_SNAKE;
        snakeArray[0][1] = 0;
    }

    private void createKeyBindings() {
        Action spaceBarAction = new SpaceBarAction();
        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();
        Action upAction = new UpAction();
        Action downAction = new DownAction();

        this.getInputMap().put(KeyStroke.getKeyStroke(' '), "spaceBarAction");
        this.getActionMap().put("spaceBarAction", spaceBarAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", leftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", rightAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "upAction");
        this.getActionMap().put("upAction", upAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "downAction");
        this.getActionMap().put("downAction", downAction);
    }

    private void createInfoLabel() {
        label = new JLabel("To start press space.");
        this.add(label);
        labelTimer = new Timer(500, event -> label.setVisible(!label.isVisible()));
        labelTimer.start();
    }

    private class SpaceBarAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            labelTimer.stop();
            label.setVisible(false);
            panelTimer.start();
        }
    }

    private class LeftAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            if(direction != 'r') direction = 'l';
        }
    }

    private class RightAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            if(direction != 'l') direction = 'r';
        }
    }

    private class UpAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(direction != 'd') direction = 'u';
        }
    }

    private class DownAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(direction != 'u') direction = 'd';
        }
    }
}
