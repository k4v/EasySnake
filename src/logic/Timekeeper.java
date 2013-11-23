package logic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 7:44 PM
 *
 * Game-wide timekeeper. Updates a dynamic list of updateables
 * on each frame increment.
 */

public class Timekeeper
{
    private static final int DEFAULT_INITIAL_SPEED = 10;    // Number of frames per second

    private List<IUpdatePerFrame> updatePerFrameList;      // List of objects to update per frame
    private int currentFPS;                                // Current number of frames per second

    long frameStartTime = 0;                               // System time when current frame was started

    boolean isRunning = false;                             // If time keeper is currently running.
                                                           // Only one game instance starts the timer;
                                                           // other instances will simply be added to the update list.

    public static Timekeeper INSTANCE = null;              // Singleton instance of Timekeeper

    private Timekeeper()
    {
        this.updatePerFrameList = new ArrayList<IUpdatePerFrame>();
        this.currentFPS = DEFAULT_INITIAL_SPEED;
        this.isRunning = false;
    }

    public static Timekeeper getInstance()
    {
        if(INSTANCE == null)
        {
            INSTANCE = new Timekeeper();
        }

        return INSTANCE;
    }

    protected final void startTimer(Game game)
    {
        updatePerFrameList.add(game);                      // Add this game instance to updateable list

        if(!isRunning)                                     // Start timer only if it wasn't started by another game instance
        {
            isRunning = true;
            frameStartTime = System.currentTimeMillis();

            while(currentFPS > 0)
            {
                if((System.currentTimeMillis() - frameStartTime)/1000.0f > (1.0f/currentFPS))
                {
                    for(IUpdatePerFrame iUpdateable : updatePerFrameList)
                    {
                        iUpdateable.update();
                    }

                    frameStartTime = System.currentTimeMillis();
                }
            }
        }
    }

    protected final void stopTimer()
    {
        currentFPS = 0;
        isRunning = false;
    }

    protected synchronized void updateTime(int newFPS)
    {
        this.currentFPS = newFPS;
    }

    public void add(IUpdatePerFrame objectToUpdate)
    {
        updatePerFrameList.add(objectToUpdate);
    }
}
