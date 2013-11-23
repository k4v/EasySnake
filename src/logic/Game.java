package logic;

import ui.GameScreen;

import javax.swing.SwingUtilities;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 7:20 PM
 *
 * Instance of the game. Controls its timekeeper.
 */

public class Game implements IUpdatePerFrame
{
    GameScreen gameScreen;                                 // Game UI. Has to be created here to use the same keypress handler

    Snake playerSnake;                                     // Snake instance
    int[] dotPosition;                                     // Position of the collectible dot
    int[] gameDimensions;                                  // Grid dimensioins for this game

    KeypressHandler keypressHandler;

    public Game(int gridWidth, int gridHeight)
    {
        this.gameDimensions = new int[]{gridWidth, gridHeight};
        this.playerSnake = new Snake(this.gameDimensions);
        this.keypressHandler = new KeypressHandler();

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run() {
                gameScreen = new GameScreen(keypressHandler, gameDimensions);
            }
        });
    }

    public void startGame()
    {
        Timekeeper.getInstance().startTimer(this);         // Start timer (if it hasn't already). Adds this game instance to list of updateables
    }

    @Override
    public void update()
    {
        if(!playerSnake.isDead())
        {
            playerSnake.updateSnake(keypressHandler.getLastPressedDirection());
            gameScreen.drawSnake(playerSnake.getSnakeBlocks());
        }
        else
        {
            Timekeeper.getInstance().stopTimer();
            gameOverRoutines();
        }
    }

    private void gameOverRoutines()
    {

    }
}
