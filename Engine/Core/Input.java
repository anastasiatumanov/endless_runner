package Engine.Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import Engine.Gfx.Vector2F;

/**
 * <h1>Logs user input from keyboard and mouse</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener
{
	public static final int MOUSE_LEFT = 1;
	public static final int MOUSE_MID = 2;
	public static final int MOUSE_RIGHT = 3;
	
	private GameContainer gc;
	
	//TODO: Tighten up size of arrays to exact number of buttons
	private static boolean[] kb = new boolean[256];
	private static boolean[] prevKb = new boolean[256];
	private static boolean[] mouse = new boolean[256];
	private static boolean[] prevMouse = new boolean[256];
	
	private static Vector2F mousePos = new Vector2F(0f,0f);
	
	/**
	 * <b><i>Input</b></i><p>
	 * &nbsp&nbsp{@code public Input(GameContainer gc)}<p>
	 * Create a class to track user input via keyboard and mouse input
	 * @param gc The driver class for the Game
	 */
	public Input(GameContainer gc)
	{
		this.gc = gc;
		gc.GetWindow().GetCanvas().addKeyListener(this);
		gc.GetWindow().GetCanvas().addMouseListener(this);
		gc.GetWindow().GetCanvas().addMouseMotionListener(this);
	}

	/**
	 * <b><i>Update</b></i><p>
	 * &nbsp&nbsp{@code public void Update()}<p>
	 * Update the previous states of both the mouse and keyboard.<br>
	 * Current states are tracked via listeners on the input devices.
	 */
	public void Update()
	{
		//System.out.println("KEY STATE 39: " + kb[39]);
		
		prevKb = kb.clone();
		prevMouse = mouse.clone();
	}
	
	/**
	 * <b><i>GetMousePos</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetMousePos()}<p>
	 * Retrieve the current location of the mouse cursor
	 * @return The current x,y coordinate of the mouse
	 */
	public static Vector2F GetMousePos()
	{
		return mousePos;
	}
	
	/**
	 * <b><i>IsKeyDown</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsKeyDown(int keyCode)}<p>
	 * Checks if a given key is currently down
	 * @param keyCode The id of the key to be tested<br>e.g. KeyEvent.VK_A for "A"
	 * @return True if the given key is currently down, false otherwise
	 */
	public static boolean IsKeyDown(int keyCode)
	{
		//if (kb[keyCode] == true)
		//{
		//	System.out.println("39 DOWN");
		//}
		
		return kb[keyCode];
	}
	
	/**
	 * <b><i>IsKeyPressed</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsKeyPressed(int keyCode)}<p>
	 * Checks if a given key was just pressed down by the user
	 * @param keyCode The id of the key to be tested<br>e.g. KeyEvent.VK_A for "A"
	 * @return True if the given key was just pressed down, false otherwise
	 */
	public static boolean IsKeyPressed(int keyCode)
	{
		/*
		System.out.println("PRESS TEST: " + keyCode + " || kb:" + kb[keyCode] + " || prevKb:" + prevKb[keyCode]);
		if (kb[keyCode] && !prevKb[keyCode])
		{
			System.out.println("PRESSED!!!!!");
		}
		*/
		return kb[keyCode] && !prevKb[keyCode];
	}
	
	/**
	 * <b><i>IsKeyReleased</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsKeyReleased(int keyCode)}<p>
	 * Checks if a given key was just released by the user
	 * @param keyCode The id of the key to be tested<br>e.g. KeyEvent.VK_A for "A"
	 * @return True if the given key was just released, false otherwise
	 */
	public static boolean IsKeyReleased(int keyCode)
	{
		/*
		System.out.println("RELEASE TEST: " + keyCode + " || kb:" + kb[keyCode] + " || prevKb:" + prevKb[keyCode]);
		if (!kb[keyCode] && prevKb[keyCode])
		{
			System.out.println("RELEASED!!!!!");
		}
		*/
		return !kb[keyCode] && prevKb[keyCode];
	}
	
	/**
	 * <b><i>IsMouseButtonDown</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsMouseButtonDown(int button)}<p>
	 * Checks if a given Mouse button is currently down
	 * @param button The id of the mouse button to be tested<br>e.g. MOUSE_LEFT
	 * @return True if the given button is currently down, false otherwise
	 */
	public static boolean IsMouseButtonDown(int button)
	{
		return mouse[button];
	}
	
	/**
	 * <b><i>IsMouseButtonPressed</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsMouseButtonPressed(int button)}<p>
	 * Checks if a given Mouse button was just pressed down by the user
	 * @param button The id of the mouse button to be tested<br>e.g. MOUSE_LEFT
	 * @return True if the given button was just pressed down, false otherwise
	 */
	public static boolean IsMouseButtonPressed(int button)
	{
		return mouse[button] && !prevMouse[button];
	}
	
	/**
	 * <b><i>IsMouseButtonReleased</b></i><p>
	 * &nbsp&nbsp{@code public static boolean IsMouseButtonReleased(int button)}<p>
	 * Checks if a given Mouse button was just released by the user
	 * @param button The id of the mouse button to be tested<br>e.g. MOUSE_LEFT
	 * @return True if the given button was just released, false otherwise
	 */
	public static boolean IsMouseButtonReleased(int button)
	{
		return !mouse[button] && prevMouse[button];
	}
	
	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mousePos.x = e.getX();
		mousePos.y = e.getY();
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseMoved(MouseEvent e)
	{
		mousePos.x = e.getX();
		mousePos.y = e.getY();
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseClicked(MouseEvent e)
	{
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mousePressed(MouseEvent e)
	{
		mouse[e.getButton()] = true;
		//System.out.println("MOUSE: " + e.getButton());
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void mouseReleased(MouseEvent e)
	{
		mouse[e.getButton()] = false;
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void keyPressed(KeyEvent e)
	{
		kb[e.getKeyCode()] = true;
		//System.out.println("KEY PRESSED: " + e.getKeyCode());
		//System.out.println("KEY STATE 39: " + kb[39]);
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void keyReleased(KeyEvent e)
	{
		kb[e.getKeyCode()] = false;
		//System.out.println("KEY RELEASED: " + e.getKeyCode());
		//System.out.println("KEY STATE 39: " + kb[39]);
	}

	/**
	 * <b><i>NOT CURRENTLY IN USE</b></i>
	 */
	@Override
	public void keyTyped(KeyEvent e)
	{
	}
}