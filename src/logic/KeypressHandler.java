package logic;

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
    private Direction lastPressedDirection = Direction.RIGHT;

    @Override
    public void keyTyped(KeyEvent e)
    {
        // Do nothing
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch(e.getKeyCode())
        {
            case KeyEvent.VK_LEFT:
                lastPressedDirection = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                lastPressedDirection = Direction.RIGHT;
                break;
            case KeyEvent.VK_UP:
                lastPressedDirection = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                lastPressedDirection = Direction.DOWN;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Do nothing
    }

    public Direction getLastPressedDirection()
    {
        return lastPressedDirection;
    }
}
