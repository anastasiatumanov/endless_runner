package Engine.Core;

import java.awt.Graphics2D;

/**
 * <h1>Template Class for the Game Loop</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public abstract class AbstractGame
{
	
	/**
	 * <b><i>LoadContent</i></b><p>
	 * &nbsp&nbsp{@code public abstract void LoadContent(GameContainer gc)}<p>
	 * Load all the Game Media and set up and associated data
	 * @param gc The driver class for the Game
	 */
	public abstract void LoadContent(GameContainer gc);
	
	/**
	 * <b><i>Update</i></b><p>
	 * &nbsp&nbsp{@code public abstract void Update(GameContainer gc, float deltaTime)}<p>
	 * Update all game related data including user input, game objects, etc.
	 * @param gc The driver class for the Game
	 * @param deltaTime The amount of time since the last time the game was updated
	 */
	public abstract void Update(GameContainer gc, float deltaTime);
	
	/**
	 * <b><i>Draw</i></b><p>
	 * &nbsp&nbsp{@code public abstract void Draw(GameContainer gc, Graphics2D gfx)}<p>
	 * Draw all game objects relevant to the current state of the game
	 * @param gc The driver class for the Game
	 * @param gfx The graphics object to which everything will be drawn
	 */
	public abstract void Draw(GameContainer gc, Graphics2D gfx);

}