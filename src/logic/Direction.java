package logic;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 8:12 PM
 *
 * Indicates directions that the snake can move
 */

public enum Direction
{
    UP (-1),
    DOWN (1),
    LEFT (-2),
    RIGHT (2);

    private int value;

    private Direction(int value)
    {
        this.value = value;
    }

    public int getDirection()
    {
        return value;
    }
}
