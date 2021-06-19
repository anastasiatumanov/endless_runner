package Engine.Core;

import Engine.Gfx.*;

/**
 * <h1>Driving class for the Game Loop</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class GameContainer implements Runnable
{
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;
	
	private Thread thread;
	private boolean isRunning = false;
	
	private int fps;
	private int tps;	//ticks per second
	
	private int width;
	private int height;
	private String title;
	
	private static float currFPS = 60;
	private int actualFPS = 0;
	
	/**
	 * <b><i>GameContainer</b></i><p>
	 * &nbsp&nbsp{@code public GameContainer(AbstractGame game, String title, int width, int height, }<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp
	 * {@code float fps)}<p>
	 * Create a driver class to implement the Game Loop backend operations
	 * @param game The template class to be used for the Game Loop
	 * @param title The title of the game
	 * @param width The width of the game window in pixels
	 * @param height The height of the game window in pixels
	 * @param fps The desired frame rate for the game, default is 60
	 */
	public GameContainer(AbstractGame game, String title, int width, int height, float fps)
	{
		this.game = game;
		this.width = width;
		this.height = height;
		this.title = title;
		this.currFPS = fps;
		
		//window = new Window(title, width, height);
	}
	
	/**
	 * <b><i>GetWindow</b></i><p>
	 * &nbsp&nbsp{@code public Window GetWindow()}<p>
	 * Retrieve the Window object the game is drawn in
	 * @return The Window Object the game is drawn in
	 */
	public Window GetWindow()
	{
		return window;
	}
	
	
	/**
	 * <b><i>GetWidth</b></i><p>
	 * &nbsp&nbsp{@code public int GetWidth()}<p>
	 * Retrieve the width of the game window
	 * @return The pixel width of the game window
	 */
	public int GetWidth()
	{
		return width;
	}
	
	/**
	 * <b><i>GetHeight</b></i><p>
	 * &nbsp&nbsp{@code public int GetHeight()}<p>
	 * Retrieve the height of the game window
	 * @return The pixel height of the game window
	 */
	public int GetHeight()
	{
		return height;
	}
	
	/**
	 * <b><i>GetTitle</b></i><p>
	 * &nbsp&nbsp{@code public String GetTitle()}<p>
	 * Retrieve the game title
	 * @return The title of the game
	 */
	public String GetTitle()
	{
		return title;
	}
	
	/**
	 * <b><i>GetTargetFPS</b></i><p>
	 * &nbsp&nbsp{@code public float GetTargetFPS()}<p>
	 * Retrieve the target game frame rate
	 * @return The target frame rate of the game
	 */
	public float GetTargetFPS()
	{
		return currFPS;
	}
	
	/**
	 * <b><i>GetMousePos</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetMousePos()}<p>
	 * Retrieve the current mouse position
	 * @return The current position of the mouse in Vector2F format
	 */
	public Vector2F GetMousePos()
	{
		if (input != null)
		{
			return input.GetMousePos();
		}
		
		return Vector2F.ZERO;
	}
	
	/**
	 * <b><i>GetActualFPS</b></i><p>
	 * &nbsp&nbsp{@code public int GetActualFPS()}<p>
	 * Retrieve the current game frame rate
	 * @return The current frame rate of the game
	 */
	public int GetActualFPS()
	{
		return actualFPS;
	}
	
	/**
	 * <b><i>Start</b></i><p>
	 * &nbsp&nbsp{@code public void Start()}<p>
	 * Begin the execution of the Game Loop and hence the program itself
	 */
	public void Start()
	{
		if (!isRunning)
		{
			//Initialize game components
			window = new Window(this);//title, width, height);
			renderer = new Renderer(this);
			input = new Input(this);
			
			//window.SetFullScreen(Window.NO_FULLSCREEN);
			window.SetVisible(true);
			
			thread = new Thread(this);
			thread.run();
		}
	}
	
	/**
	 * <b><i>Stop</b></i><p>
	 * &nbsp&nbsp{@code public void Stop()}<p>
	 * Stop the execution of the Game Loop and hence the program itself
	 */
	public void Stop()
	{
		if(isRunning)
		{
			isRunning = false;
		}
	}
	
	@Override
	public void run()
	{
		isRunning = true;
		
		final double GAME_HERTZ = currFPS;	//modify this to have Update run at different frequency than Draw
		final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
		final int MAX_UPDATES_BEFORE_RENDER = 5;
		
		double lastUpdateTime = System.nanoTime();
		double lastRenderTime = lastUpdateTime;
		
		final double TARGET_TIME_BETWEEN_RENDERS = 1000000000 / currFPS;
		
		int lastSecondTime = (int) (lastUpdateTime / 1000000000);
		double now = 0;
		int updateCount = 0;
		float delta = 0f;
		int frameCount = 0;
		boolean render = false;
		
		//Before the game loop begins, load all content
		LoadContent();
		
		while (isRunning)
		{
			now = System.nanoTime();
			updateCount = 0;
			render = false;
		
			//If too much time has passed, keep updating until we are caught up to a set maximum update count
			while (now - lastUpdateTime > TIME_BETWEEN_UPDATES && updateCount < MAX_UPDATES_BEFORE_RENDER)
			{
				//How much time has passed since the last update
				delta = (float)((now - lastUpdateTime)/1000000f);
				game.Update(this, delta);	//Update the game
				input.Update();				//Check for user input
				
				//Update update and render data
				lastUpdateTime += TIME_BETWEEN_UPDATES;
				++updateCount;
				render = true;
			}
			
			//If for some reason an update takes forever, we don't want to do an insane number of catchups.
      //However, if you were doing some sort of game that needed to keep EXACT time, you would get rid of this.
      if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
      {
         lastUpdateTime = now - TIME_BETWEEN_UPDATES;
      }

      //Render. To do so, we need to calculate interpolation for a smooth render.
      if (render)
      {
        Draw();
        ++frameCount;
        lastRenderTime = now;
      }
      
    
      //Calculate the actual frame rate
      int thisSecond = (int) (lastUpdateTime / 1000000000);
      if (thisSecond > lastSecondTime)
      {
          //System.out.println("FPS: " + frameCount);
          actualFPS = frameCount;
          frameCount = 0;
          lastSecondTime = thisSecond;
      }
    
      //Yield until it has been at least the target time between renders. This saves the CPU from hogging.
      while ( now - lastRenderTime < TARGET_TIME_BETWEEN_RENDERS && now - lastUpdateTime < TIME_BETWEEN_UPDATES)
      {
        //Since we have time to spare, allow the CPU to let another process take over
        Thread.yield();
            
				//This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
				//You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
				//FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
				Sleep(1); 
				
				now = System.nanoTime();
      }
		}
		
		//window.CleanUp();
		renderer.CleanUp();
	}
	
	private void Sleep(long milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void LoadContent()
	{
		game.LoadContent(this);
	}
	
	private void Update(double deltaTime)
	{
		game.Update(this, (float)deltaTime);
	}
	
	private void Draw()
	{
		renderer.Clear();
		game.Draw(this, renderer.gfx2D);
	}
}