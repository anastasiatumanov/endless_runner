package Engine.Gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import Engine.Core.Helper;

/**
 * <h1>Game Circle</h1>
 * <b>Creation Date:</b> Dec. 16, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class GameCircle
{
	private Vector2F centre = Vector2F.ZERO;
	private float rad = 1f;
	private Color borderColor = Helper.WHITE;
	private Color fillColor = Helper.WHITE;
	private float transparency = 1f;
	private float borderWidth = 1f;
	private boolean filled = false;
	
	/**
	 * <b><i>GameCircle</b></i><p>
	 * &nbsp&nbsp{@code public GameCircle(float x, float y, float rad)}<p>
	 * Create a basic circle with given dimensions and default draw properties
	 * @param x the x component of the cricle's centre
	 * @param y the y component of the cricle's centre
	 * @param rad the radius of the circle<p>
	 */
	public GameCircle(float x, float y, float rad)
	{
		centre = new Vector2F(x,y);
		SetRad(rad);
	}
	
	/**
	 * <b><i>GameCircle</b></i><p>
	 * &nbsp&nbsp{@code public GameCircle(float x, float y, float rad, float borderWidth, Color borderColor, float transparency)}<p>
	 * Create a circle with given data
	 * @param x the x component of the cricle's centre
	 * @param y the y component of the cricle's centre
	 * @param rad the radius of the circle
	 * @param borderWidth the pixel width of the circle's border
	 * @param borderColor the border colour of the circle
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameCircle(float x, float y, float rad, float borderWidth, Color borderColor, float transparency)
	{
		centre = new Vector2F(x,y);
		SetRad(rad);
		
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		SetTransparency(transparency); 
	}
	
	/**
	 * <b><i>GameCircle</b></i><p>
	 * &nbsp&nbsp{@code public GameCircle(float x, float y, float rad, float borderWidth, Color borderColor, Color fillColor, float transparency)}<p>
	 * Create a circle with given data
	 * @param x the x component of the cricle's centre
	 * @param y the y component of the cricle's centre
	 * @param rad the radius of the circle
	 * @param borderWidth the pixel width of the circle's border
	 * @param borderColor the border colour of the circle
	 * @param fillColor the colour the circle will be filled with when drawn
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameCircle(float x, float y, float rad, float borderWidth, Color borderColor, Color fillColor, float transparency)
	{
		centre = new Vector2F(x,y);
		SetRad(rad);
		
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		this.fillColor = fillColor;
		SetTransparency(transparency);
		this.filled = true;
	}
	
	/**
	 * <b><i>GetFilled</b></i><p>
	 * &nbsp&nbsp{@code public boolean GetFilled()}<p>
	 * Retrieve the current fill status of the shape
	 * @return true if the shape is to be filled in during draw, false otherwise
	 */
	public boolean GetFilled()
	{
		return filled;
	}
	
	/**
	 * <b><i>SetFilled</b></i><p>
	 * &nbsp&nbsp{@code public void SetFilled(boolean filled)}<p>
	 * Assigns the current fill status of the shape
	 * @param filled the boolean fill status to assign the shape to
	 */
	public void SetFilled(boolean filled)
	{
		this.filled = filled;
	}
	
	/**
	 * <b><i>SetFillColor</b></i><p>
	 * &nbsp&nbsp{@code public void SetFillColor(Color color)}<p>
	 * Assigns the current fill colour of the shape 
	 * @param color the colour to fill the shape in with during draw
	 */
	public void SetFillColor(Color color)
	{
		this.fillColor = color;
	}
	
	/**
	 * <b><i>GetFillColor</b></i><p>
	 * &nbsp&nbsp{@code public Color GetFillColor()}<p>
	 * Retrieve the current fill colour of the shape 
	 * @return The current colour the shape is to be filled in with during draw
	 */
	public Color GetFillColor()
	{
		return fillColor;	
	}
	
	/**
	 * <b><i>SetBorderColor</b></i><p>
	 * &nbsp&nbsp{@code public void SetBorderColor(Color color)}<p>
	 * Assigns the current border colour of the shape 
	 * @param color the colour to draw the border of the shape in.
	 */
	public void SetBorderColor(Color color)
	{
		this.borderColor = color;
	}
	
	/**
	 * <b><i>GetBorderColor</b></i><p>
	 * &nbsp&nbsp{@code public Color GetBorderColor()}<p>
	 * Retrieve the current border colour of the shape 
	 * @return The current colour of the border of the shape during draw
	 */
	public Color GetBorderColor()
	{
		return borderColor;	
	}
	
	/**
	 * <b><i>SetTransparency</b></i><p>
	 * &nbsp&nbsp{@code public void SetTransparency(float transparency)}<p>
	 * Assigns the current transparency level of the shape.  The value must
	 *  range from 0 to 1 where 0 is invisible and 1 is opaque 
	 * @param transparency the level of transparency to apply to the shape during draw
	 */
	public void SetTransparency(float transparency)
	{
		this.transparency = Math.max(0, Math.min(1,transparency));
	}
	
	/**
	 * <b><i>GetTransparency</b></i><p>
	 * &nbsp&nbsp{@code public float GetTransparency()}<p>
	 * Retrieve the current transparency level of the shape between 0 and 1.  Where 0 is invisible 1 is fully opaque. 
	 * @return A float representing the current transparency of the shape.
	 */
	public float GetTransparency()
	{
		return transparency;
	}
	
	/**
	 * <b><i>SetBorderWidth</b></i><p>
	 * &nbsp&nbsp{@code public void SetBorderWidth(float width)}<p>
	 * Assigns the current pixel width of the border for the shape 
	 * @param width the number of pixels wide the border of the shape is to be drawn with
	 */
	public void SetBorderWidth(float width)
	{
		this.borderWidth = Math.max(1f, width);
	}
	
	/**
	 * <b><i>GetBorderWidth</b></i><p>
	 * &nbsp&nbsp{@code public float GetBorderWidth()}<p>
	 * Retrieve the pixel width of the border lines for the shape 
	 * @return A float representing the pixel width for the border of the shape 
	 */
	public float GetBorderWidth()
	{
		return borderWidth;
	}
	
	/**
	 * <b><i>SetRad</b></i><p>
	 * &nbsp&nbsp{@code public void SetRad(float rad)}<p>
	 * Assigns the current radius of the shape to a value >= 0.1 
	 * @param rad the radius of the shape
	 */
	public void SetRad(float rad)
	{
		this.rad = Math.max(0.1f,rad);
	}
	
	/**
	 * <b><i>GetRad</b></i><p>
	 * &nbsp&nbsp{@code public float GetRad()}<p>
	 * Retrieve the radius of the shape 
	 * @return A float representing the current radius of the shape
	 */
	public float GetRad()
	{
		return rad;
	}
	
	/**
	 * <b><i>GetCentre</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetCentre()}<p>
	 * Retrieve the centre point of the shape 
	 * @return A Vector2F representing the current centre point of the shape
	 */
	public Vector2F GetCentre()
	{
		return centre;
	}
	
	/**
	 * <b><i>GetBoundingBox</b></i><p>
	 * &nbsp&nbsp{@code public Rectangle GetBoundingBox()}<p>
	 * Retrieve the bounding box surrounding the shape 
	 * @return A Rectangle representing a tightly fitted box around the shape
	 */
	public Rectangle GetBoundingBox()
	{
		return new Rectangle((int)(centre.x - rad), (int)(centre.y - rad), (int)(2 * rad), (int)(2 * rad));
	}
	
	/**
	 * <b><i>Draw</b></i><p>
	 * &nbsp&nbsp{@code public void Draw(Graphics2D gfx)}<p>
	 * Draws the shape to the screen with the current graphical settings applied
	 * @param gfx The Graphics2D object to draw to
	 */
	public void Draw(Graphics2D gfx)
	{
		if (filled)
		{
			Draw.FillEllipse(gfx, centre.x, centre.y, rad, rad, fillColor, transparency);
		}
		
		Draw.Ellipse(gfx, centre.x, centre.y, rad, rad, borderWidth, borderColor, transparency);
	}
	
	/**
	 * <b><i>DistToCentre</b></i><p>
	 * &nbsp&nbsp{@code public float DistToCentre(Vector2F pt)}<p>
	 * Draws the shape to the screen with the current graphical settings applied
	 * @param pt The point to be compared to the shape's centre
	 * @return the distance from the centre of the shape to the given point
	 */
	public float DistToCentre(Vector2F pt)
	{
		return (float)(Math.sqrt(Math.pow(pt.y - centre.y, 2) + Math.pow(pt.x - centre.x, 2)));
	}
	

	/**
	 * <b><i>Translate</b></i><p>
	 * &nbsp&nbsp{@code public void Translate(float deltaX, float deltaY)}<p>
	 * Moves the shape by a given amount in each of the x and y directions from its current location
	 * @param deltaX the amount of pixels to move the x component of the shape by
	 * @param deltaY the amount of pixels to move the y component of the shape by
	 */
	public void Translate(float deltaX, float deltaY)
	{
		centre.x += deltaX;
		centre.y += deltaY;
	}
	
	/**
	 * <b><i>TranslateTo</b></i><p>
	 * &nbsp&nbsp{@code public void TranslateTo(float x, float y)}<p>
	 * Moves the shape to a given x,y coordinate on the screen
	 * @param x The new x component of the shape's new location
	 * @param y The new y component of the shape's new location
	 */
	public void TranslateTo(float x, float y)
	{
		centre.x = x;
		centre.y = y;
	}
}
