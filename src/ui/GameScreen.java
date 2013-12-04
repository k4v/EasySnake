package ui;

import logic.WorldState;
import logic.framework.KeypressHandler;
import props.GameProperties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
    GamePanel playPanel;
    JLabel scoreLabel;

    public GameScreen(KeypressHandler keyPressHandler, GameProperties gameProperties)
    {
        super("Easy Snake");


        playPanel = new GamePanel(gameProperties);

        scoreLabel = new JLabel("Score: 0", null, SwingConstants.LEFT);

        //Initialization of the Swing frame
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new FlowLayout());
        this.addKeyListener(keyPressHandler);
        this.add(playPanel);
        this.add(scoreLabel);

        // Make game screen visible
        this.pack();
        this.setVisible(true);
    }

    public void drawWorld(List<int[]> snakeBlocks, WorldState worldState)
    {
        if(worldState!=null)
        {
            scoreLabel.setText("Score: "+worldState.getScore());
            this.pack();
        }

        playPanel.setWorld(snakeBlocks, worldState);
        playPanel.repaint();
    }
}

class GamePanel extends JPanel
{
    private static final int SCREEN_GRID_MULTIPLIER = 8;

    GameProperties gameProperties;
    Dimension gameDimensions;
    List<int[]> snakeBlocks = null;
    WorldState worldState;

    public GamePanel(GameProperties gameProperties)
    {
        this.gameProperties = gameProperties;

        gameDimensions = new Dimension(gameProperties.getGridWidth()*SCREEN_GRID_MULTIPLIER,
                gameProperties.getGridHeight()*SCREEN_GRID_MULTIPLIER);

        this.setBackground(new Color(gameProperties.getScreenColor(), false));
    }

    public Dimension getPreferredSize()
    {
        return this.gameDimensions;
    }

    public void paintComponent(Graphics graphics)
    {
        graphics.setColor(new Color(gameProperties.getSnakeColor()));
        super.paintComponent(graphics);
        if(snakeBlocks!=null)
        {
            for(int[] snakeBlock : snakeBlocks)
            {
                graphics.fillRect(
                        snakeBlock[0] * SCREEN_GRID_MULTIPLIER, snakeBlock[1] * SCREEN_GRID_MULTIPLIER,
                        SCREEN_GRID_MULTIPLIER, SCREEN_GRID_MULTIPLIER);
            }

            graphics.fillRect(
                    worldState.getDotPosition()[0] * SCREEN_GRID_MULTIPLIER, worldState.getDotPosition()[1] * SCREEN_GRID_MULTIPLIER,
                    SCREEN_GRID_MULTIPLIER, SCREEN_GRID_MULTIPLIER);

            if(gameProperties.getMazeBlocks().size()>0)
            {
                graphics.setColor(new Color(gameProperties.getMazeColor()));
                for(int[] mazeBlock : gameProperties.getMazeBlocks())
                {
                    graphics.drawRect(
                            mazeBlock[0] * SCREEN_GRID_MULTIPLIER, mazeBlock[1] * SCREEN_GRID_MULTIPLIER,
                            SCREEN_GRID_MULTIPLIER, SCREEN_GRID_MULTIPLIER);
                }
            }
        }
        else
        {
            graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
            graphics.drawString("GAME OVER", (gameDimensions.width/2)-80, (gameDimensions.height)/2);
        }
    }

    void setWorld(List<int[]> snakeBlocks, WorldState worldState)
    {
        this.snakeBlocks = snakeBlocks;
        this.worldState = worldState;
    }
}
