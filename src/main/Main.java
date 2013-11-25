package main;

import logic.Game;

/**
 * Created with IntelliJ IDEA.
 * User: karthikv
 * Date: 11/21/13
 * Time: 7:14 PM
 *
 * Main class to initialize game and wait for completion.
 * Includes cleanup routines
 */

public class Main
{
    public static void main(String[] args) throws Exception
    {
        Game game = new Game(GameProperties.getPropertiesFromFile("levels/contrast.properties"));
        game.startNewGame();

    }
}
