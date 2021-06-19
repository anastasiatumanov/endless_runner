package Engine.Core;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * <h1>Internal Class to Setup and Draw to the Canvas</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1
 */
public class Renderer
{
	public Graphics2D gfx2D;
	private BufferedImage image = null;
	private GameContainer gc = null;
	private BufferStrategy bs;
	
	/**
	 * <b><i>Renderer</b></i><p>
	 * &nbsp&nbsp{@code public Renderer(GameContainer gc)}<p>
	 * Create a class to track graphics object to be drawn to
	 * @param gc The driver class for the Game
	 */
	public Renderer(GameContainer gc)
	{
		this.gc = gc;
		image = new BufferedImage(gc.GetWidth(), gc.GetHeight(), BufferedImage.TYPE_INT_RGB);
		gfx2D = (Graphics2D) image.getGraphics();
		
		//Set up double buffering
		//gc.GetWindow().GetCanvas().createBufferStrategy(2);
		//bs = gc.GetWindow().GetCanvas().getBufferStrategy();
		//gfx2D = (Graphics2D)bs.getDrawGraphics();
	}
	
	/**
	 * <b><i>Clear</b></i><p>
	 * &nbsp&nbsp{@code public void Clear()}<p>
	 * Clears the screen for the next frame render
	 */
	public void Clear()
	{
		Graphics g2 = gc.GetWindow().GetCanvas().getGraphics();
		//Graphics g2 = bs.getDrawGraphics();
		
		if (image != null)
		{
			g2.drawImage(image, 0, 0, null);
			
			//bs.show();
		}
		g2.dispose();
		
		Draw();
	}
	
	private void Draw()
	{
		//System.out.println("gc: " + gc.GetWidth() + ", " + gc.GetHeight() + " || Canvas: " + gc.GetWindow().GetCanvas().getWidth() + ", " + gc.GetWindow().GetCanvas().getHeight());
		gfx2D.clearRect(0, 0, gc.GetWidth(), gc.GetHeight());
	}

	/**
	 * <b><i>CleanUp</b></i><p>
	 * &nbsp&nbsp{@code public void CleanUp()}<p>
	 * Removes the graphics object from memory and flushes the image buffer
	 */
	public void CleanUp()
	{
		gfx2D.dispose();
		//bs.dispose();
		image.flush();
	}
}
