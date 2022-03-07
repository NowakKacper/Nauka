package NowakKacper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPanel extends JPanel implements ActionListener {

    private static final int PANEL_WIDTH = 500;
    private static final int PANEL_HEIGHT = 500;

    private final Image[] brick = new Image[13];
    private final int[][] brickCoordinates = new int[13][2];
    private final Image ball;
    private final Image paddle;
    private int paddleX = 200;
    private final int paddleY = 400;
    private int ballX = 234;
    private int ballY = 350;
    private int ballXSpeed = 1;
    private int ballYSpeed = 0;
    private int nextLevelBallYSpeed = -1;
    private final JLabel label;
    private final Timer labelTimer;
    private final Timer panelTimer;
    private int score = 0;

    public MyPanel(){
        brickCoordinates[0][0] = 40; //x coordinate for brick on first row
        brickCoordinates[0][1] = 50; //y coordinate for brick on first row
        brickCoordinates[7][0] = 70; //x coordinate for brick on second row
        brickCoordinates[7][1] = 110; //y coordinate for brick on second row
        //placing bricks on the board
        for(int i=1; i<13; i++){
            if(i<7){
                brickCoordinates[i][0] = brickCoordinates[i-1][0] + 60;
                brickCoordinates[i][1] = 50;
            }
            else if(i>7){
                brickCoordinates[i][0] = brickCoordinates[i-1][0] + 60;
                brickCoordinates[i][1] = 110;
            }
        }

        //creating bricks, ball and paddle
        for(int i=0; i<13; i++){
            brick[i] = new ImageIcon("brick.png").getImage();
        }
        ball = new ImageIcon("ball.png").getImage();
        paddle = new ImageIcon("paddle.png").getImage();

        //Key bindings
        Action spaceBarAction = new SpaceBarAction();
        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();

        this.getInputMap().put(KeyStroke.getKeyStroke(' '), "spaceBarAction");
        this.getActionMap().put("spaceBarAction", spaceBarAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        this.getActionMap().put("leftAction", leftAction);

        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        this.getActionMap().put("rightAction", rightAction);

        //setting information about how to start
        label = new JLabel("To start press space.");
        this.add(label);
        labelTimer = new Timer(500, event -> label.setVisible(!label.isVisible()));
        labelTimer.start();

        //setting parameters of the frame
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
        panelTimer = new Timer(1, this);
        panelTimer.start();
    }

    //painting board
    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        int brickX = 40;
        int brickY = 50;
        for(int i=0; i<13; i++){
            if(i == 7){
                brickX = 70;
                brickY = 110;
            }
            if(brick[i] != null){
                g2.drawImage(brick[i], brickX, brickY, null);
            }
            brickX += 60;
        }
        g2.drawImage(paddle, paddleX, paddleY, null);
        g2.drawImage(ball, ballX, ballY, null);

    }

    //checking if ball hit brick
    private void hitTheBrick(){
        for(int i=0; i<13; i++){
            if(brick[i] != null){
                if(whereBallHitTheBrick(brickCoordinates[i][0], brickCoordinates[i][1])){
                    brick[i] = null;
                    score++;
                }
            }
        }
    }

    //check where ball touch the brick and change accordingly ball speed
    private boolean whereBallHitTheBrick(int x, int y){
        int x1 = x;
        int y1 = y;

        //condition that i<240 is set because one side of square is 60 so whole square has circuit equal 240
        for(int i=0; i<240; i++){
            //using circle equation to check if some part of brick is also some part of circle
            if(Math.pow((x1 - (ballX + 16)), 2) + Math.pow((y1 - (ballY + 16)), 2) == 16*16){
                //checking which side of brick was hit by ball and changing speed accordingly
                if(x1 > x && x1 < x + 60 && y1 == y) ballYSpeed *= -1;
                else if(y1 > y && y1 < y + 60 && x1 == x + 60) ballXSpeed *= -1;
                else if(x1 > x && x1 < x + 60 && y1 == y + 60) ballYSpeed *= -1;
                else if(y1 > y && y1 < y + 60 && x1 == x) ballXSpeed *= -1;
                else if((x1 == x && y1 == y) || (x1 == (x + 60) && y1 == y) ||
                        (x1 == (x + 60) && y1 == (y + 60)) || (x1 == x && y1 == (y + 60))){
                    ballXSpeed *= -1;
                    ballYSpeed *= -1;
                }
                return true;
            }
            //if some part of brick isn't also a part of ball we are jumping to another part of brick
            //and checking if this part is maybe also a part of our ball
            else if(x1 < x + 60 && y1 == y){
                x1++;
            }
            else if(y1 < y + 60 && x1 == x + 60){
                y1++;
            }
            else if(x1 > x && y1 == y + 60){
                x1--;
            }
            else if(y1 > y && x1 == x){
                y1--;
            }
        }
        return false;
    }

    //checking where ball hit the paddle
    private void whereBallHitThePaddle(){
        for(int i=0; i<1; i++){
            //bottom side of ball
            if(ballX + 11 >= paddleX
                    && ballX + 21 <= paddleX + 100
                    && ballY + 32 == paddleY){
                ballYSpeed *= -1;
                break;
            }
            //left side of ball
            if(((ballY + 11 >= paddleY && ballY + 11 <= paddleY + 20)
                    || (ballY + 21 >= paddleY && ballY + 21 <= paddleY + 20))
                    && ballX == paddleX + 100){
                ballXSpeed *= -1;
                break;
            }
            //right side of ball
            if(((ballY + 11 >= paddleY && ballY + 11 <= paddleY + 20)
                    || (ballY + 21 >= paddleY && ballY + 21 <= paddleY + 20))
                    && ballX + 32 == paddleX){
                ballXSpeed *= -1;
                break;
            }
            //left corner of ball
            if(((ballX + 6 >= paddleX + 89 && ballX + 6 <= paddleX + 100)
                    && ballY + 26 == paddleY)
                    ||
                    ((ballY + 26 >= paddleY && ballY + 26 <= paddleY + 9)
                            && ballX + 6 == paddleX + 100)){
                ballYSpeed *= -1;
                break;
            }
            //right corner of ball
            if(((ballX + 26 >= paddleX && ballX + 26 <= paddleX + 11)
                    && ballY + 26 == paddleY)
                    ||
                    ((ballY + 26 >= paddleY && ballY + 26 <= paddleY + 9)
                            && ballX == paddleX)){
                ballYSpeed *= -1;
                break;
            }
        }
    }

    //telling what program have to do when player lose the game
    private void endGame(){
        panelTimer.stop();
        JOptionPane.showMessageDialog(this, "You lost, your score was: " + score);
        int chosenOption = JOptionPane.showOptionDialog(this, "Would you like to play again?",
                null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
        if(chosenOption == 0) newGame(true);
    }

    //creating new game/next level
    public void newGame(boolean isItNewGame){
        paddleX = 200;
        ballX = 234;
        ballY = 350;
        if(isItNewGame) {
            ballXSpeed = 1;
            score = 0;
        }
        ballYSpeed = 0;
        labelTimer.start();
        panelTimer.start();

        for(int i=0; i<13; i++){
            brick[i] = new ImageIcon("brick.png").getImage();
        }
    }

    //preparing parameters to next level
    private void nextLevel(){
        newGame(false);
        ballXSpeed++;
        nextLevelBallYSpeed--;
    }

    //checking if player destroy all bricks
    private boolean isThereAnyBricks(){
        for(Image image : brick) if(image != null) return false;
        return true;
    }

    //ball control and checking certain moments in the game
    @Override
    public void actionPerformed(ActionEvent e) {
        //changing ball move directory when wall was hit
        if(ballX >= PANEL_WIDTH - ball.getWidth(null) || ballX <= 0){
            ballXSpeed *= -1;
        }

        if(ballY >= PANEL_HEIGHT - ball.getHeight(null) || ballY <= 0){
            ballYSpeed *= -1;
        }

        //moving the ball
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        //checking certain moments in game
        if(ballY >= paddleY + 20) endGame();
        hitTheBrick();
        if(isThereAnyBricks()) nextLevel();
        whereBallHitThePaddle();
        repaint();
    }

    //handling of space action
    public class SpaceBarAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            labelTimer.stop();
            label.setVisible(false);
            if(ballYSpeed == 0) ballYSpeed = nextLevelBallYSpeed;
        }
    }

    //handling of left button action
    public class LeftAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(paddleX >= 0) paddleX -= 10;
        }
    }

    //handling of right button action
    public class RightAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(paddleX + 100 <= PANEL_WIDTH) paddleX += 10;
        }
    }
}

