package logic;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 * Date: 11/23/13
 * Time: 1:44 PM
 *
 * State of non-playable displayables in the game
 */

public class WorldState
{
    private int[] dotPosition;
    private int[] specialDotPosition;

    private int gameScore;

    public WorldState()
    {
        gameScore = 0;
    }

    public int[] getDotPosition()
    {
        return dotPosition.clone();
    }

    protected void setDot(int[] dotPosition)
    {
        this.dotPosition = dotPosition;
    }

    public int[] getSpecialDotPosition()
    {
        return specialDotPosition.clone();
    }

    protected void setSpecialDot(int[] specialDotPosition)
    {
        this.specialDotPosition = specialDotPosition;
    }

    protected void setScore(int score)
    {
        this.gameScore = score;
    }

    public int getScore()
    {
        return gameScore;
    }
}
