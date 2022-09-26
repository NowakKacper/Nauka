package NowakKacper.Gameplays;

import NowakKacper.GamePanels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gameplay implements ActionListener {

    private final GamePanel gamePanel;

    private JLabel label;
    private Timer labelTimer;
    private Timer panelTimer;
    private int ballXSpeed = 1;
    private int ballYSpeed = 0;
    private int score = 0;
    private int nextLevelBallYSpeed = -1;

    public Gameplay(GamePanel gamePanel){
        this.gamePanel = gamePanel;

        createInfoLabel();

        createPanelTimer();

        createKeyBindings();

        newMap(true);
    }

    public void gameControl() {
        didBallHitWall();

        moveBall();

        if(gamePanel.getBallYPosition() >= gamePanel.getPaddleYPosition() + 20) endGame();

        didBallHitTheBrick();

        if(isThereAnyBricks()) newMap(false);

        didBallHitPaddle();

        gamePanel.repaint();
    }

    private void createKeyBindings(){
        //Key bindings
        Action spaceBarAction = new SpaceBarAction();
        Action leftAction = new LeftAction();
        Action rightAction = new RightAction();

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke(' '), "spaceBarAction");
        gamePanel.getActionMap().put("spaceBarAction", spaceBarAction);

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "leftAction");
        gamePanel.getActionMap().put("leftAction", leftAction);

        gamePanel.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "rightAction");
        gamePanel.getActionMap().put("rightAction", rightAction);
    }

    //TODO add new option pane which will ask if player wants to enter score to the scoreboard
    private void endGame() {
        panelTimer.stop();
        JOptionPane.showMessageDialog(gamePanel, "You lost, your score was: " + score);
        int chosenOption = JOptionPane.showOptionDialog(gamePanel, "Would you like to play again?",
                null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
        if(chosenOption == 0) newMap(true);
    }

    //set parameters for new game/next level
    public void newMap(boolean isItNewGame){
        gamePanel.setPaddleXPosition(gamePanel.getPADDLE_STARTING_X_POSITION());
        gamePanel.setPaddleYPosition(gamePanel.getPADDLE_STARTING_Y_POSITION());

        gamePanel.setBallXPosition(gamePanel.getBALL_STARTING_X_POSITION());
        gamePanel.setBallYPosition(gamePanel.getBALL_STARTING_Y_POSITION());

        if(isItNewGame) {
            ballXSpeed = 1;
            score = 0;
        }
        else{
            ballXSpeed++;
            nextLevelBallYSpeed--;
        }

        ballYSpeed = 0;
        labelTimer.start();
        panelTimer.start();

        for(int i=0; i<gamePanel.getHowManyColumns()*gamePanel.getHowManyRows(); i++){
            gamePanel.setIsThisBricksStillExist(i, true);
        }
    }

    private void moveBall() {
        gamePanel.setBallXPosition(gamePanel.getBallXPosition() + ballXSpeed);

        gamePanel.setBallYPosition(gamePanel.getBallYPosition() + ballYSpeed);
    }

    private void didBallHitWall() {
        if(gamePanel.getBallXPosition() >= gamePanel.getPANEL_WIDTH() - 2*gamePanel.getRADIUS_OF_THE_BALL() ||
                gamePanel.getBallXPosition() <= 0){
            ballXSpeed *= -1;
        }

        if(gamePanel.getBallYPosition() >= gamePanel.getPANEL_HEIGHT() - 2*gamePanel.getRADIUS_OF_THE_BALL() ||
                gamePanel.getBallYPosition() <= 0){
            ballYSpeed *= -1;
        }
    }

    private boolean circleEquation(Dimension dimension){

        return Math.pow((dimension.width - (gamePanel.getBallXPosition() + gamePanel.getRADIUS_OF_THE_BALL())), 2) +
                Math.pow((dimension.height - (gamePanel.getBallYPosition() + gamePanel.getRADIUS_OF_THE_BALL())), 2) ==
                Math.pow(gamePanel.getRADIUS_OF_THE_BALL(), 2);

    }

    private void didBallHitTheBrick(){
        for(int i=0; i<gamePanel.getHowManyColumns()*gamePanel.getHowManyRows(); i++){
            if(gamePanel.getIsThisBricksStillExist(i)){

                Dimension dimension = gamePanel.getBricksCoordinates(i);

                //x coordinate of the point on the border of the brick
                int x = dimension.width;
                //y coordinate of the point on the border of the brick
                int y = dimension.height;

                for(int z=0; z<4*gamePanel.getSIDE_LENGTH_OF_THE_BRICK(); z++){
                    //using circle equation to check if some part of brick is also some part of circle
                    if(circleEquation(new Dimension(x, y))){

                        //checking which side of brick was hit by ball and changing speed accordingly
                        //TODO better corners
                        if(x >= dimension.width &&
                                x <= dimension.width + gamePanel.getSIDE_LENGTH_OF_THE_BRICK() &&
                                (y == dimension.height || y == dimension.height + gamePanel.getSIDE_LENGTH_OF_THE_BRICK()))
                            ballYSpeed *= -1;
                        if(y >= dimension.height &&
                                y <= dimension.height + gamePanel.getSIDE_LENGTH_OF_THE_BRICK() &&
                                (x == dimension.width || x == dimension.width + gamePanel.getSIDE_LENGTH_OF_THE_BRICK()))
                            ballXSpeed *= -1;

                        score++;
                        gamePanel.setIsThisBricksStillExist(i, false);

                        break;
                    }
                    //if some part of brick isn't also part of ball we are jumping to another part of brick
                    //and checking if this part is maybe also part of our ball
                    else if(x < dimension.width + gamePanel.getSIDE_LENGTH_OF_THE_BRICK() && y == dimension.height){
                        x++;
                    }
                    else if(y < dimension.height + gamePanel.getSIDE_LENGTH_OF_THE_BRICK() && x ==
                            dimension.width + gamePanel.getSIDE_LENGTH_OF_THE_BRICK()){
                        y++;
                    }
                    else if(x > dimension.width && y == dimension.height + gamePanel.getSIDE_LENGTH_OF_THE_BRICK()){
                        x--;
                    }
                    else if(y > dimension.height && x == dimension.width){
                        y--;
                    }
                }
                if(!gamePanel.getIsThisBricksStillExist(i)) break;
            }

        }
    }

    private void didBallHitPaddle(){
        for(int i=0; i<gamePanel.getPADDLE_HEIGHT(); i++){
            if(circleEquation(
                    new Dimension(gamePanel.getPaddleXPosition(), gamePanel.getPADDLE_STARTING_Y_POSITION()+i)) ||
                    circleEquation(new Dimension(gamePanel.getPaddleXPosition()+gamePanel.getPADDLE_WIDTH(),
                    gamePanel.getPADDLE_STARTING_Y_POSITION()+i))){
                ballXSpeed *= -1;
                return;
            }
        }

        for(int i=0; i<=gamePanel.getPADDLE_WIDTH(); i++){
            if(circleEquation(new Dimension(gamePanel.getPaddleXPosition()+i, gamePanel.getPaddleYPosition()))){
                ballYSpeed *= -1;
                if(i==0 || i==gamePanel.getPADDLE_WIDTH()) ballXSpeed *= -1;
                break;
            }
        }

    }

    //checking if player destroy all bricks
    private boolean isThereAnyBricks(){
        for(int i=0; i<gamePanel.getHowManyRows()*gamePanel.getHowManyColumns(); i++)
            if(gamePanel.getIsThisBricksStillExist(i)) return false;
        return true;
    }

    private void createInfoLabel(){
        //setting information about how to start
        label = new JLabel("To start press space.");
        gamePanel.add(label);
        labelTimer = new Timer(500, event -> label.setVisible(!label.isVisible()));
        labelTimer.start();
    }

    private void createPanelTimer(){
        panelTimer = new Timer(1, this);
        panelTimer.start();
    }

    //handling of space action
    public class SpaceBarAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            labelTimer.stop();
            label.setVisible(false);
            if(ballYSpeed == 0) ballYSpeed = nextLevelBallYSpeed;
        }
    }

    //handling of left button action
    public class LeftAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            if(gamePanel.getPaddleXPosition() >= 0)
                gamePanel.setPaddleXPosition(gamePanel.getPaddleXPosition() - gamePanel.getPADDLE_STEP());
        }
    }

    //handling of right button action
    public class RightAction extends AbstractAction{

        @Override
        public void actionPerformed(ActionEvent event) {
            if(gamePanel.getPaddleXPosition() + gamePanel.getPADDLE_WIDTH() <= gamePanel.getPANEL_WIDTH())
                gamePanel.setPaddleXPosition(gamePanel.getPaddleXPosition() + gamePanel.getPADDLE_STEP());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        gameControl();
    }

}
