package props;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Karthik
 * Date: 11/23/13
 * Time: 2:03 PM
 *
 * Specifies initialization properties for the game
 */

public class GameProperties
{
    /**
     * List of supported game properties
     */
    private static final String GAME_SPEED_PROP   = "game.speed";
    private static final String GRID_WIDTH_PROP   = "grid.width";
    private static final String GRID_HEIGHT_PROP  = "grid.height";
    private static final String SNAKE_LENGTH_PROP = "snake.length";
    private static final String SCREEN_COLOR_PROP = "screen.color";
    private static final String SNAKE_COLOR_PROP  = "snake.color";
    private static final String MAZE_COLOR_PROP   = "maze.color";
    private static final String MAZE_BLOCKS_PROP  = "maze.blocks";

    /**
     * List of default values for supported game properties
     */
    private static final int DEFAULT_GAME_SPEED   = 20;
    private static final int DEFAULT_GRID_WIDTH   = 75;
    private static final int DEFAULT_GRID_HEIGHT  = 75;
    private static final int DEFAULT_SNAKE_LENGTH = 15;
    private static final int DEFAULT_SCREEN_COLOR = 0xffffff;
    private static final int DEFAULT_SNAKE_COLOR  = 0x000000;
    private static final int DEFAULT_MAZE_COLOR   = 0xffffff;
    private static final List<int[]> DEFAULT_MAZE_BLOCKS  = new ArrayList<int[]>(0);

    private static final GameProperties DEFAULT_GAME_PROPERTIES = getDefaultGameProperties();

    Properties gameProperties = new Properties();          // Store for game properties
    List<int[]> mazeBlocks = new ArrayList<int[]>();       // List of blocks for the maze in the level

    private GameProperties()
    {
    }

    // Return static instance of default game properties
    public static GameProperties getDefaultGameProperties()
    {
        GameProperties defaultGameProperties = new GameProperties();
        defaultGameProperties.gameProperties.put(GAME_SPEED_PROP, DEFAULT_GAME_SPEED);
        defaultGameProperties.gameProperties.put(GRID_WIDTH_PROP, DEFAULT_GRID_WIDTH);
        defaultGameProperties.gameProperties.put(GRID_HEIGHT_PROP, DEFAULT_GRID_HEIGHT);
        defaultGameProperties.gameProperties.put(SNAKE_LENGTH_PROP, DEFAULT_SNAKE_LENGTH);
        defaultGameProperties.gameProperties.put(SCREEN_COLOR_PROP, DEFAULT_SCREEN_COLOR);
        defaultGameProperties.gameProperties.put(SNAKE_COLOR_PROP, DEFAULT_SNAKE_COLOR);
        defaultGameProperties.gameProperties.put(MAZE_COLOR_PROP, DEFAULT_MAZE_COLOR);
        defaultGameProperties.mazeBlocks = DEFAULT_MAZE_BLOCKS;

        return defaultGameProperties;
    }

    /**
     * Read game parameters from properties file. Use default parameters
     * where invalid value in file
     * @param fileName Name of property file
     * @return Instance of GameProperties loaded with values from property file
     */
    public static GameProperties getPropertiesFromFile(String fileName)
    {
        try
        {
            GameProperties gameProperties = new GameProperties();
            Properties fileProperties = new Properties();
            fileProperties.load(new FileReader(fileName));

            //Read the integer values from the properties file
            for (Object propKey : DEFAULT_GAME_PROPERTIES.gameProperties.keySet())
            {
                try
                {
                    String propString = (String)fileProperties.get(propKey);
                    int intBase = propString.startsWith("0x") ? 16 : 10;                         // Checking if the prop value is a hex string
                    Integer propValue = Integer.parseInt(propString.replace("0x",""), intBase);  // Removing "0x" part of hex integer strings
                    gameProperties.gameProperties.put(propKey, propValue);

                    System.out.println("Setting "+propKey+" to "+propValue);
                } catch (Exception e)
                {
                    System.out.println("Setting default value for "+propKey);
                    gameProperties.gameProperties.put(propKey, DEFAULT_GAME_PROPERTIES.gameProperties.get(propKey));
                }
            }

            /**
             * Save set of maze blocks from file
             * The property value is expected to be in the format x1:y1,x2:y2,...
             */
            try
            {
                String mazeBlocksProp = fileProperties.getProperty(MAZE_BLOCKS_PROP, "");
                System.out.println("Maze blocks: "+mazeBlocksProp);
                for(String mazeBlockString : mazeBlocksProp.split(","))
                {
                    String[] mazeBlock = mazeBlockString.split(":");
                    gameProperties.mazeBlocks.add(new int[]
                            {Integer.parseInt(mazeBlock[0]), Integer.parseInt(mazeBlock[1])});
                }
            } catch (Exception e)
            {
                gameProperties.mazeBlocks = DEFAULT_MAZE_BLOCKS;
            }

            return gameProperties;

        } catch(Exception e)
        {
            System.err.println("Error reading properties from file: Using default properties.");
            e.printStackTrace();
            return DEFAULT_GAME_PROPERTIES;
        }
    }

    /**
     * Getters for each of the available properties
     */

    public Integer getGameSpeed()
    {
        return (Integer)gameProperties.get(GAME_SPEED_PROP);
    }

    public Integer getGridWidth()
    {
        return (Integer)gameProperties.get(GRID_WIDTH_PROP);
    }

    public Integer getGridHeight()
    {
        return (Integer)gameProperties.get(GRID_HEIGHT_PROP);
    }

    public Integer getSnakeLength()
    {
        return (Integer)gameProperties.get(SNAKE_LENGTH_PROP);
    }

    public Integer getSnakeColor()
    {
        return (Integer)gameProperties.get(SNAKE_COLOR_PROP);
    }

    public Integer getScreenColor()
    {
        return (Integer)gameProperties.get(SCREEN_COLOR_PROP);
    }

    public Integer getMazeColor()
    {
        return (Integer)gameProperties.get(MAZE_COLOR_PROP);
    }

    public List<int[]> getMazeBlocks()
    {
        return mazeBlocks; // The values in the GameProperties object are only read during game initialization
                           // so it doesn't matter if the variables are mutable
    }
}
