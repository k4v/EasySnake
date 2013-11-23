package logic;

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
    private static final int DEFAULT_INITIAL_LENGTH = 15;

    private int[] movableDimensions;

    private List<int[]> snakeBlocks;
    private boolean isDead;

    Direction currentDirection = Direction.RIGHT;

    protected Snake(int[] gameDimensions)
    {
        this.movableDimensions = gameDimensions;
        snakeBlocks = new ArrayList<int[]>(DEFAULT_INITIAL_LENGTH);
        isDead = false;

        for(int i=0; i<DEFAULT_INITIAL_LENGTH; i++)
        {
            int[] thisPosition = new int[]{(movableDimensions[0]/2)-i, (movableDimensions[1]/2)};
            snakeBlocks.add(thisPosition);
        }
    }

    public boolean isDead()
    {
        return isDead;
    }

    protected void updateSnake(Direction moveDirection, int[] dotPosition)
    {
        if(moveDirection == null)
        {
            moveDirection = currentDirection;
        }

        int[] currentHeadPosition = snakeBlocks.get(0).clone();
        updateSnakeHead(moveDirection);

        if(Arrays.equals(dotPosition, snakeBlocks.get(0)))
        {
            snakeBlocks.add(1, currentHeadPosition);

            for(int i=snakeBlocks.size()-1; i>=2; i--)
            {
                int[] newBlockPosition = snakeBlocks.get(i-1).clone();
                snakeBlocks.set(i, newBlockPosition);
            }
        }
        else
        {
            for(int i=snakeBlocks.size()-1; i>=2; i--)
            {
                int[] newBlockPosition = snakeBlocks.get(i-1).clone();
                snakeBlocks.set(i, newBlockPosition);
            }

            snakeBlocks.set(1, currentHeadPosition);
        }

        if(checkInternalCollide(snakeBlocks.get(0))>=0)
        {
            isDead = true;
        }
    }

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

    protected int checkInternalCollide(int[] checkPosition)
    {
        return checkCollide(checkPosition, 1);
    }

    protected int checkExternalCollide(int[] checkPosition)
    {
        return checkCollide(checkPosition, 0);
    }

    private int checkCollide(int[] checkPosition, int fromBlockNumber)
    {
        for(int i=fromBlockNumber; i<snakeBlocks.size(); i++)
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
