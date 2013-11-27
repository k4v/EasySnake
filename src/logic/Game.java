package logic;

import logic.framework.IUpdatePerFrame;
import logic.framework.KeypressHandler;
import logic.framework.Timekeeper;
import props.GameProperties;
import ui.GameScreen;

import java.util.Arrays;
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
    GameScreen gameScreen;                                      // Game UI. Has to be created here to use the same keypress handler

    Snake playerSnake;                                          // Snake instance
    WorldState worldState;                                      // World state (dot position, score)

    GameProperties gameProperties;                              // Game Properties :P

    KeypressHandler keypressHandler;

    public Game(GameProperties gameProperties)
    {
        this.gameProperties = gameProperties;
        this.keypressHandler = new KeypressHandler();           // Listens to keypress events
        this.gameScreen = new GameScreen(keypressHandler, this.gameProperties);     // Game will be drawn here
    }

    public void startNewGame()
    {
        System.out.println("Starting new game");
        this.worldState = new WorldState();
        this.playerSnake = new Snake(this.gameProperties);      // Create Snake object here
        createDot();                                            // Create the first collectibe dot

        Timekeeper.getInstance().startTimer(this, gameProperties.getGameSpeed());   // Start timer (if it hasn't already). Adds this game instance to list of updateables
    }

    private void createDot()
    {
        int[] dotPosition;
        Random random = new Random();
        do
        {
            dotPosition = new int[]{random.nextInt(gameProperties.getGridWidth()), random.nextInt(gameProperties.getGridHeight())};
        } while(playerSnake.checkBodyCollide(dotPosition)>=0 || checkMazeCollide(dotPosition));  // Create a new dot on the screen where the snake or maze isn't already

        worldState.setDot(dotPosition);
    }

    private boolean checkMazeCollide(int[] checkPosition)
    {
        for(int[] blockPosition : gameProperties.getMazeBlocks())
        {
            if(Arrays.equals(blockPosition, checkPosition))
            {
                return true;
            }
        }

        return false;
    }

    @Override
    public void update()
    {
        int[] dotPosition = worldState.getDotPosition();
        if(!playerSnake.isDead())
        {
            playerSnake.updateSnake(keypressHandler.getLastPressedDirection(), dotPosition);  // First update snake position
            if(playerSnake.checkHeadCollide(dotPosition)>=0)
            {
                worldState.setScore(worldState.getScore()+1);
                createDot();
            }

            // Check for collision with maze
            for(int[] mazeBlock : gameProperties.getMazeBlocks())
            {
                if(playerSnake.checkHeadCollide(mazeBlock)>=0)
                {
                    playerSnake.killSnake();
                    return;
                }
            }
            gameScreen.drawWorld(playerSnake.getSnakeBlocks(), worldState);   // Then draw the updated world
        }
        else
        {
            Timekeeper.getInstance().stopTimer();               // Timer stops here. So no more frame updates
            gameOverRoutines();                                 // What to do on death
        }
    }

    private void gameOverRoutines()
    {
        System.out.println("GAME OVER");
        gameScreen.drawWorld(null, null);                       // Null parameters to indicate "game over"

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
