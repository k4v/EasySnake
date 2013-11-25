package logic.framework;

import logic.Direction;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 * Date: 11/22/13
 * Time: 11:22 AM
 *
 * Event listener to listen for key presses. Only stores last key presses.
 */

public class KeypressHandler implements KeyListener
{
    private Direction lastPressedDirection = null;
    @Override
    public void keyTyped(KeyEvent e)
    {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(lastPressedDirection == null)
        {
            switch(e.getKeyCode())
            {
                case KeyEvent.VK_LEFT:
                    setNextDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    setNextDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    setNextDirection(Direction.UP);
                    break;
                case KeyEvent.VK_DOWN:
                    setNextDirection(Direction.DOWN);
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Do nothing
    }

    public Direction getLastPressedDirection()
    {
        Direction lastDirection = lastPressedDirection;
        setNextDirection(null);

        return lastDirection;
    }

    private synchronized void setNextDirection(Direction setDirection)
    {
        this.lastPressedDirection = setDirection;

    }
}
