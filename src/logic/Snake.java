package logic;

import props.GameProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 7:20 PM
 */

public class Snake
{
    private int[] movableDimensions;

    private List<int[]> snakeBlocks;
    private boolean isDead;

    Direction currentDirection = Direction.RIGHT;

    // Initialize snake using game properties
    protected Snake(GameProperties gameProperties)
    {
        this.movableDimensions = new int[]{gameProperties.getGridWidth(), gameProperties.getGridHeight()};   // Set grid in which snake can move
        snakeBlocks = new ArrayList<int[]>(gameProperties.getSnakeLength());   // Set list of blocks of the snake
        isDead = false;                                                        // Specifies if snake is dead

        for(int i=0; i<gameProperties.getSnakeLength(); i++)                   // Position each block of the snake
        {
            int[] thisPosition = new int[]{(movableDimensions[0]/2)-i, (movableDimensions[1]/2)};
            snakeBlocks.add(thisPosition);
        }
    }

    public boolean isDead()
    {
        return isDead;
    }

    /**
     * Update the snake position. Uses the current location and direction information to move the snake one frame.
     * Also checks for collision with a dot
     *
     * @param moveDirection
     * @param dotPosition
     */
    protected void updateSnake(Direction moveDirection, int[] dotPosition)
    {
        if(moveDirection == null)
        {
            // No new input
            moveDirection = currentDirection;
        }

        int[] currentHeadPosition = snakeBlocks.get(0).clone();
        updateSnakeHead(moveDirection);

        if(Arrays.equals(dotPosition, snakeBlocks.get(0)))
        {
            // Snake eats dot, length++
            snakeBlocks.add(1, currentHeadPosition);
        }
        else
        {
            // Move snake 1 block
            snakeBlocks.add(1, currentHeadPosition);
            snakeBlocks.remove(snakeBlocks.size()-1);
        }

        // Check if snake has collided with itself
        for(int i=1; i<snakeBlocks.size(); i++)
        {
            if(checkHeadCollide(snakeBlocks.get(i))>=0)
            isDead = true;
        }
    }

    /**
     * Move snake head in the given direction
     * @param moveDirection Direction in which to move
     */
    private void updateSnakeHead(Direction moveDirection)
    {
        if(Math.abs(currentDirection.getDirection()) != Math.abs(moveDirection.getDirection()))
        {
            currentDirection = moveDirection;
        }

        switch(currentDirection)
        {
            case LEFT:
                snakeBlocks.get(0)[0]=(snakeBlocks.get(0)[0] -1+movableDimensions[0])%movableDimensions[0];
                break;
            case RIGHT:
                snakeBlocks.get(0)[0]=(snakeBlocks.get(0)[0] +1+movableDimensions[0])%movableDimensions[0];
                break;
            case UP:
                snakeBlocks.get(0)[1]=(snakeBlocks.get(0)[1] -1+movableDimensions[0])%movableDimensions[1];
                break;
            case DOWN:
                snakeBlocks.get(0)[1]=(snakeBlocks.get(0)[1] +1+movableDimensions[0])%movableDimensions[1];
                break;
        }
    }

    // Kill snake. Required for collisions with maze
    protected void killSnake()
    {
        isDead = true;
    }

    /**
     * Check snake head has collided with anything
     * @return 0 if collision occured. -1 if no collision
     */
    protected int checkHeadCollide(int[] checkPosition)
    {
        if (Arrays.equals(snakeBlocks.get(0), checkPosition))
        {
            return 0;
        }

        return -1;
    }

    /**
     * Check collisions of any portion of snake with a point.
     * @param checkPosition External point with which to check collision
     * @return
     */
    protected int checkBodyCollide(int[] checkPosition)
    {
        for(int i=1; i<snakeBlocks.size(); i++)
        {
            if (Arrays.equals(checkPosition, snakeBlocks.get(i)))
            {
                return i;
            }
        }

        return -1;
    }

    protected List<int[]> getSnakeBlocks()
    {
        return snakeBlocks;
    }
}
