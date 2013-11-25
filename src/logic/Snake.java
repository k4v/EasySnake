package logic;

import main.GameProperties;

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

    protected Snake(GameProperties gameProperties)
    {
        this.movableDimensions = new int[]{gameProperties.getGridWidth(), gameProperties.getGridHeight()};
        snakeBlocks = new ArrayList<int[]>(gameProperties.getSnakeLength());
        isDead = false;

        for(int i=0; i<gameProperties.getSnakeLength(); i++)
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
        }
        else
        {
            snakeBlocks.add(1, currentHeadPosition);
            snakeBlocks.remove(snakeBlocks.size()-1);
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
