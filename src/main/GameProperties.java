package main;

import java.io.FileReader;
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

    /**
     * List of default values for supported game properties
     */
    private static final int DEFAULT_GAME_SPEED   = 20;
    private static final int DEFAULT_GRID_WIDTH   = 75;
    private static final int DEFAULT_GRID_HEIGHT  = 75;
    private static final int DEFAULT_SNAKE_LENGTH = 15;
    private static final int DEFAULT_SCREEN_COLOR = 0xffffff;
    private static final int DEFAULT_SNAKE_COLOR  = 0x000000;

    Properties gameProperties = new Properties();          // Store for game properties

    /**
     * Static instance of default game properties
     */
    private static final GameProperties DEFAULT_GAME_PROPERTIES = new GameProperties(
            DEFAULT_GAME_SPEED,
            DEFAULT_GRID_WIDTH,
            DEFAULT_GRID_HEIGHT,
            DEFAULT_SNAKE_LENGTH,
            DEFAULT_SCREEN_COLOR,
            DEFAULT_SNAKE_COLOR);

    private GameProperties(Integer gameSpeed, Integer gridWidth, Integer gridHeight,
                           Integer snakeLength, Integer screenColor, Integer snakeColor)
    {
        gameProperties.put(GAME_SPEED_PROP, gameSpeed);
        gameProperties.put(GRID_WIDTH_PROP, gridWidth);
        gameProperties.put(GRID_HEIGHT_PROP, gridHeight);
        gameProperties.put(SNAKE_LENGTH_PROP, snakeLength);
        gameProperties.put(SCREEN_COLOR_PROP, screenColor);
        gameProperties.put(SNAKE_COLOR_PROP, snakeColor);
    }

    // Return static instance of default game properties
    public static GameProperties getDefaultGameProperties()
    {
        return DEFAULT_GAME_PROPERTIES;
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
            GameProperties gameProperties = DEFAULT_GAME_PROPERTIES;
            Properties fileProperties = new Properties();
            fileProperties.load(new FileReader(fileName));

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
}
