package ui;

import logic.KeypressHandler;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 * Date: 11/22/13
 * Time: 12:38 PM
 *
 * Draws the entire Snake game. Converts the snake co-ords into screen co-ords
 */

public class GameScreen extends JFrame
{
    public static final int SCREEN_GRID_MULTIPLIER = 8;

    GamePanel playPanel;

    public GameScreen(KeypressHandler keyPressHandler, int[] gameDimensions)
    {
        super("Easy Snake");
        Dimension screenArea = new Dimension(gameDimensions[0]*SCREEN_GRID_MULTIPLIER, gameDimensions[1]*SCREEN_GRID_MULTIPLIER);
        playPanel = new GamePanel(screenArea);

        //Initialization of the Swing frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.addKeyListener(keyPressHandler);
        this.add(playPanel);

        // Make game screen visible
        this.pack();
        this.setVisible(true);
    }

    public void drawWorld(List<int[]> snakeBlocks, int[] dotPosition)
    {
        playPanel.setWorld(snakeBlocks, dotPosition);
        playPanel.repaint();
    }
}

class GamePanel extends JPanel
{
    Dimension gameDimensions;
    List<int[]> snakeBlocks = null;
    int[] dotPosition;

    public GamePanel(Dimension gameDimensions)
    {
        this.gameDimensions = gameDimensions;
        this.setBackground(new Color(153, 255, 47));
    }

    public Dimension getPreferredSize()
    {
        return gameDimensions;
    }

    public void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        if(snakeBlocks!=null)
        {
            for(int[] snakeBlock : snakeBlocks)
            {
                graphics.fillRect(
                        snakeBlock[0] * GameScreen.SCREEN_GRID_MULTIPLIER, snakeBlock[1] * GameScreen.SCREEN_GRID_MULTIPLIER,
                        GameScreen.SCREEN_GRID_MULTIPLIER, GameScreen.SCREEN_GRID_MULTIPLIER);
            }

            graphics.fillRect(
                    dotPosition[0] * GameScreen.SCREEN_GRID_MULTIPLIER, dotPosition[1] * GameScreen.SCREEN_GRID_MULTIPLIER,
                    GameScreen.SCREEN_GRID_MULTIPLIER, GameScreen.SCREEN_GRID_MULTIPLIER);
        }
    }

    void setWorld(List<int[]> snakeBlocks, int[] dotPosition)
    {
        this.snakeBlocks = snakeBlocks;
        this.dotPosition = dotPosition;
    }
}
