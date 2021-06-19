package Engine.Core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

/**
 * <h1>Manages the drawing Canvas for the game</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> June 29, 2016<p>
 * @author Trevor Lane
 * @version 1.0 
 */
public class Window
{
	private JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
	private Graphics2D g;
	private BufferStrategy bs;
	
	//public static GraphicsDevice defaultDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
	//public static int screenResWidth = defaultDevice.getDisplayMode().getWidth();
	//public static int screenResHeight = defaultDevice.getDisplayMode().getHeight();
	
	private boolean fullScreenOn = false;
	private int fullScreenMode = 0;
	
	public static final int NO_FULLSCREEN = 0;
	public static final int MAXIMIZE = 1;
	public static final int FULLSCREEN = 2;
	
	/**
	 * <b><i>Window</b></i><p>
	 * &nbsp&nbsp{@code public Window(GameContainer gc)}<p>
	 * Create a class to track the drawing Canvas
	 * @param gc The driver class for the Game
	 */
	public Window(GameContainer gc)//String title, int width, int height)
	{
		canvas = new Canvas();
		//Dimension s = new Dimension(width, height);
		Dimension s = new Dimension(gc.GetWidth(), gc.GetHeight());
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		
		//frame = new JFrame(title);
		frame = new JFrame(gc.GetTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		frame.add(canvas, BorderLayout.CENTER);
		
		//Remove resizability
		frame.setResizable(false);
		
		//Set the frame to the same size as the canvas
		frame.pack();
		
		//centre the frame on the screen
		frame.setLocationRelativeTo(null);
		
		frame.setFocusable(false);
		frame.requestFocus();
		//frame.setVisible(true);
	
		//Set up double buffering
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = (Graphics2D)bs.getDrawGraphics();
	}
	
	private void SetupFullScreen()
	{
		switch (fullScreenMode)
		{
			case NO_FULLSCREEN:
				//System.out.println("No fullscreen");
				frame.setUndecorated(false);
				break;
			case MAXIMIZE:
				frame.setUndecorated(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				break;
			case FULLSCREEN:
				frame.setUndecorated(true);
				device.setFullScreenWindow(frame);
				break;
		}
	}
	
	private void SetFullScreen(int newFullScreenMode)
	{
		//if (newFullScreenMode <= 2)
		//{
		//	frame.dispose();
		//	
		//	fullScreenOn = true;
		//	fullScreenMode = newFullScreenMode;
		//	SetupFullScreen();
		//}
		//else
		//{
			System.err.println("Error" + newFullScreenMode + " is not Supported");
		//}
	}
	
	/**
	 * <b><i>CleanUp</b></i><p>
	 * &nbsp&nbsp{@code public void CleanUp()}<p>
	 * Deletes drawing objects from memory and flushes<br>
	 * the image buffer after the game's completion
	 */
	public void CleanUp()
	{
		g.dispose();
		bs.dispose();
		image.flush();
		frame.dispose();
	}

	/**
	 * <b><i>GetCanvas</b></i><p>
	 * &nbsp&nbsp{@code public Canvas GetCanvas()}<p>
	 * Retrieve the Canvas used for drawing
	 * @return The Canvas object used for drawing
	 */
	public Canvas GetCanvas()
	{
		return canvas;
	}

	/**
	 * <b><i>GetImage</b></i><p>
	 * &nbsp&nbsp{@code public BufferedImage GetImage()}<p>
	 * Retrieve the image for the current game frame
	 * @return The image object comprised of all draw<br>
	 * commands to be drawn to the Canvas next
	 */
	public BufferedImage GetImage()
	{
		return image;
	}
	
	/**
	 * <b><i>SetVisible</b></i><p>
	 * &nbsp&nbsp{@code public void SetVisible(boolean state)}<p>
	 * Set whether the game is visible or not<br>
	 * Called automatically when the Game Loop is Started
	 * @param state The true/false state of whether the game is visible
	 */
	public void SetVisible(boolean state)
	{
		frame.setVisible(state);
	}
}