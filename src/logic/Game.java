package logic;

import ui.GameScreen;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 7:20 PM
 *
 * Instance of the game. Updated every frame and controls updates of other game components.
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
        this.gameDimensions = new int[]{gridWidth, gridHeight}; // Game co-ords. Different from pixel sizes
        this.keypressHandler = new KeypressHandler();           // Listens to keypress events
        this.gameScreen = new GameScreen(keypressHandler, gameDimensions);     // Game will be drawn here
    }

    public void startNewGame()
    {
        System.out.println("Starting new game");
        this.playerSnake = new Snake(this.gameDimensions); // Create Snake object here
        createDot();                                       // Create the first collectibe dot

        Timekeeper.getInstance().startTimer(this);         // Start timer (if it hasn't already). Adds this game instance to list of updateables
    }

    private void createDot()
    {
        Random random = new Random();
        do
        {
            dotPosition = new int[]{random.nextInt(this.gameDimensions[0]), random.nextInt(this.gameDimensions[1])};
        } while(playerSnake.checkExternalCollide(dotPosition)>=0);             // Create a new dot on the screen where the snake isn't already
    }

    @Override
    public void update()
    {
        if(!playerSnake.isDead())
        {
            playerSnake.updateSnake(keypressHandler.getLastPressedDirection(), dotPosition);  // First update snake position
            if(playerSnake.checkExternalCollide(dotPosition)>=0)
            {
                createDot();
            }
            gameScreen.drawWorld(playerSnake.getSnakeBlocks(), dotPosition);   // Then draw the updated world
        }
        else
        {
            Timekeeper.getInstance().stopTimer();                              // Timer stops here. So no more frame updates
            gameOverRoutines();                                                // What to do on death
        }
    }

    private void gameOverRoutines()
    {
        System.out.println("GAME OVER");

        try {
            Thread.sleep(1500);                                 // Small delay before the next game starts
            startNewGame();                                     // Re-initialize the snake and reset the timer
        } catch (InterruptedException e)
        {
            System.err.println("Not continuing game because of error "+e.getMessage());
            System.exit(1);
        }
    }
}
