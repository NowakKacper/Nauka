package NowakKacper.GamePanels;

import NowakKacper.GameWindows.MainWindow;
import NowakKacper.Gameplays.Gameplay;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{

    private static final int DISTANCE_BETWEEN_GROUND_AND_PADDLE = 150;
    private static final int DISTANCE_BETWEEN_BOTTOM_BRICK_ROW_AND_PADDLE = 400;
    private static final int DISTANCE_BETWEEN_BALL_AND_PADDLE = 100;
    private static final int DISTANCE_BETWEEN_BRICKS_AND_WALLS = 40;

    private final int PADDLE_STEP = 10;
    private final int RADIUS_OF_THE_BALL = 10;
    private final int SIDE_LENGTH_OF_THE_BRICK = 40;
    private final int PADDLE_WIDTH = 150;
    private final int PADDLE_HEIGHT = 20;
    private final int PANEL_WIDTH = MainWindow.getMonitorSize().width;
    private final int PANEL_HEIGHT = MainWindow.getMonitorSize().height;
    private final Gameplay gameplay;
    private final int PADDLE_STARTING_X_POSITION = (PANEL_WIDTH / 2) - (PADDLE_WIDTH / 2);
    private final int PADDLE_STARTING_Y_POSITION = PANEL_HEIGHT - DISTANCE_BETWEEN_GROUND_AND_PADDLE - PADDLE_HEIGHT;
    private final int BALL_STARTING_X_POSITION = PANEL_WIDTH / 2 - RADIUS_OF_THE_BALL;
    private final int BALL_STARTING_Y_POSITION = PANEL_HEIGHT - DISTANCE_BETWEEN_GROUND_AND_PADDLE -
            PADDLE_HEIGHT - DISTANCE_BETWEEN_BALL_AND_PADDLE;

    private int[][] bricksCoordinates;
    private final int howManyRows;
    private final int howManyColumns;
    private boolean[] areThoseBricksStillExist;
    private int paddleXPosition;
    private int paddleYPosition;
    private int ballXPosition;
    private int ballYPosition;

    public GamePanel(){
        howManyColumns = (PANEL_WIDTH - 2* DISTANCE_BETWEEN_BRICKS_AND_WALLS) / SIDE_LENGTH_OF_THE_BRICK;
        howManyRows = (PANEL_HEIGHT - DISTANCE_BETWEEN_BRICKS_AND_WALLS - DISTANCE_BETWEEN_BOTTOM_BRICK_ROW_AND_PADDLE -
                DISTANCE_BETWEEN_GROUND_AND_PADDLE - PADDLE_HEIGHT) / SIDE_LENGTH_OF_THE_BRICK;

        createBricks();

        gameplay = new Gameplay(this);

        //setting parameters of the frame
        this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.lightGray);
    }

    //painting board
    @Override
    public void paint(Graphics g){
        super.paint(g);

        Graphics2D g2 = (Graphics2D) g;

        drawExistingBricks(g2);

        drawBall(g2);

        drawPaddle(g2);
    }

    private void createBricks(){
        bricksCoordinates = new int[howManyRows*howManyColumns][2];

        for(int i=0; i<howManyRows*howManyColumns; i++){
            if(i % howManyColumns == 0){
                bricksCoordinates[i][0] = (PANEL_WIDTH - howManyColumns*SIDE_LENGTH_OF_THE_BRICK) / 2;
                if(i == 0) bricksCoordinates[i][1] = DISTANCE_BETWEEN_BRICKS_AND_WALLS;
                else bricksCoordinates[i][1] = bricksCoordinates[i-1][1] + SIDE_LENGTH_OF_THE_BRICK;
                continue;
            }
            bricksCoordinates[i][0] = bricksCoordinates[i-1][0] + SIDE_LENGTH_OF_THE_BRICK;
            bricksCoordinates[i][1] = bricksCoordinates[i-1][1];
        }

        areThoseBricksStillExist = new boolean[howManyColumns*howManyRows];

        for(int i=0; i<howManyRows*howManyColumns; i++){
            areThoseBricksStillExist[i] = true;
        }
    }

    private void drawExistingBricks(Graphics2D g2) {
        for(int i=0; i<howManyRows*howManyColumns; i++){
            if(areThoseBricksStillExist[i]){
                g2.setPaint(Color.green);
                g2.drawRect(bricksCoordinates[i][0], bricksCoordinates[i][1],
                        SIDE_LENGTH_OF_THE_BRICK, SIDE_LENGTH_OF_THE_BRICK);
                g2.fillRect(bricksCoordinates[i][0], bricksCoordinates[i][1],
                        SIDE_LENGTH_OF_THE_BRICK, SIDE_LENGTH_OF_THE_BRICK);
                g2.setPaint(Color.black);
                g2.drawRect(bricksCoordinates[i][0], bricksCoordinates[i][1],
                        SIDE_LENGTH_OF_THE_BRICK, SIDE_LENGTH_OF_THE_BRICK);
            }
        }
    }

    private void drawBall(Graphics2D g2){
        //drawing ball
        g2.setPaint(Color.red);
        g2.drawOval(ballXPosition, ballYPosition, 2*RADIUS_OF_THE_BALL, 2*RADIUS_OF_THE_BALL);
        g2.fillOval(ballXPosition, ballYPosition, 2*RADIUS_OF_THE_BALL, 2*RADIUS_OF_THE_BALL);
        g2.setPaint(Color.black);
        g2.drawOval(ballXPosition, ballYPosition, 2*RADIUS_OF_THE_BALL, 2*RADIUS_OF_THE_BALL);
    }

    private void drawPaddle(Graphics2D g2) {
        //drawing paddle
        g2.setPaint(Color.orange);
        g2.drawRect(paddleXPosition, paddleYPosition, PADDLE_WIDTH, PADDLE_HEIGHT);
        g2.fillRect(paddleXPosition, paddleYPosition, PADDLE_WIDTH, PADDLE_HEIGHT);
        g2.setPaint(Color.black);
        g2.drawRect(paddleXPosition, paddleYPosition, PADDLE_WIDTH, PADDLE_HEIGHT);
    }

    public Dimension getBricksCoordinates(int whichBrick) {
        return new Dimension(bricksCoordinates[whichBrick][0], bricksCoordinates[whichBrick][1]);
    }

    public Gameplay getGameplay() {
        return gameplay;
    }

    public int getPADDLE_STEP() {
        return PADDLE_STEP;
    }

    public int getPADDLE_STARTING_X_POSITION() {
        return PADDLE_STARTING_X_POSITION;
    }

    public int getPADDLE_STARTING_Y_POSITION() {
        return PADDLE_STARTING_Y_POSITION;
    }

    public int getBALL_STARTING_X_POSITION() {
        return BALL_STARTING_X_POSITION;
    }

    public int getBALL_STARTING_Y_POSITION() {
        return BALL_STARTING_Y_POSITION;
    }

    public int getRADIUS_OF_THE_BALL() {
        return RADIUS_OF_THE_BALL;
    }

    public int getSIDE_LENGTH_OF_THE_BRICK() {
        return SIDE_LENGTH_OF_THE_BRICK;
    }

    public int getPADDLE_WIDTH() {
        return PADDLE_WIDTH;
    }

    public int getPADDLE_HEIGHT() {
        return PADDLE_HEIGHT;
    }

    public int getPANEL_WIDTH() {
        return PANEL_WIDTH;
    }

    public int getPANEL_HEIGHT() {
        return PANEL_HEIGHT;
    }

    public int getHowManyRows() {
        return howManyRows;
    }

    public int getHowManyColumns() {
        return howManyColumns;
    }

    public int getPaddleXPosition() {
        return paddleXPosition;
    }

    public void setPaddleXPosition(int paddleXPosition) {
        this.paddleXPosition = paddleXPosition;
    }

    public int getPaddleYPosition() {
        return paddleYPosition;
    }

    public void setPaddleYPosition(int paddleYPosition) {
        this.paddleYPosition = paddleYPosition;
    }

    public int getBallXPosition() {
        return ballXPosition;
    }

    public void setBallXPosition(int ballXPosition) {
        this.ballXPosition = ballXPosition;
    }

    public int getBallYPosition() {
        return ballYPosition;
    }

    public void setBallYPosition(int ballYPosition) {
        this.ballYPosition = ballYPosition;
    }

    public boolean getIsThisBricksStillExist(int whichBrick) {
        return areThoseBricksStillExist[whichBrick];
    }

    public void setIsThisBricksStillExist(int whichBrick, boolean isThisBricksStillExist) {
        this.areThoseBricksStillExist[whichBrick] = isThisBricksStillExist;
    }

}
