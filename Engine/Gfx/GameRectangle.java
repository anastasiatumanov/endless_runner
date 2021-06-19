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
 * <h1>Game Rectangle</h1>
 * <b>Creation Date:</b> Dec. 16, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class GameRectangle
{
	private Rectangle rec = new Rectangle(0,0,1,1);
	private float top = 0f;
	private float right = 1f;
	private float bottom = 1f;
	private float left = 0f;
	private Vector2F centre = Vector2F.ZERO;
	
	private Color borderColor = Helper.WHITE;
	private Color fillColor = Helper.WHITE;
	private float transparency = 1f;
	private float borderWidth = 1f;
	private boolean filled = false;
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(int x, int y, int width, int height)}<p>
	 * Create a basic Rectangle with the given dimensions
	 * @param x The x component of the top left corner of the rectangle
	 * @param y The y component of the top left corner of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 */
	public GameRectangle(int x, int y, int width, int height)
	{
		rec = new Rectangle(x,y,width,height);
		SetDimension();
	}
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(Rectangle rec)}<p>
	 * Create a basic Rectangle with the given dimensions
	 * @param rec The rectangle that will be used to determine the dimensions
	 */
	public GameRectangle(Rectangle rec)
	{
		this.rec = new Rectangle(rec.x,rec.y,rec.width,rec.height);
		SetDimension();
	}
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(int x, int y, int width, int height, float borderWidth, Color borderColor, float transparency)}<p>
	 * Create a Rectangle with the given dimensions and drawing data
	 * @param x The x component of the top left corner of the rectangle
	 * @param y The y component of the top left corner of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param borderWidth the pixel width of the rectangle's border
	 * @param borderColor the border colour of the rectangle
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameRectangle(int x, int y, int width, int height, float borderWidth, Color borderColor, float transparency)
	{
		rec = new Rectangle(x,y,width,height);
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		SetTransparency(transparency);
		SetDimension();
	}
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(Rectangle rec, float borderWidth, Color borderColor, float transparency)}<p>
	 * Create a Rectangle with the given dimensions and drawing data
	 * @param rec The rectangle that will be used to determine the dimensions
	 * @param borderWidth the pixel width of the rectangle's border
	 * @param borderColor the border colour of the rectangle
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameRectangle(Rectangle rec, float borderWidth, Color borderColor, float transparency)
	{
		this.rec = new Rectangle(rec.x,rec.y,rec.width,rec.height);
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		SetTransparency(transparency);
		SetDimension();
	}
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(int x, int y, int width, int height, float borderWidth, Color borderColor, Color fillColor, float transparency)}<p>
	 * Create a Rectangle with the given dimensions and drawing data
	 * @param x The x component of the top left corner of the rectangle
	 * @param y The y component of the top left corner of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param borderWidth the pixel width of the rectangle's border
	 * @param borderColor the border colour of the rectangle
	 * @param fillColor the colour the rectangle will be filled with when drawn
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameRectangle(int x, int y, int width, int height, float borderWidth, Color borderColor, Color fillColor, float transparency)
	{
		rec = new Rectangle(x,y,width,height);
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		this.fillColor = fillColor;
		SetTransparency(transparency);
		filled = true;
		SetDimension();
	}
	
	/**
	 * <b><i>GameRectangle</b></i><p>
	 * &nbsp&nbsp{@code public GameRectangle(Rectangle rec, float borderWidth, Color borderColor, Color fillColor, float transparency)}<p>
	 * Create a Rectangle with the given dimensions and drawing data
	 * @param rec The rectangle that will be used to determine the dimensions
	 * @param borderWidth the pixel width of the rectangle's border
	 * @param borderColor the border colour of the rectangle
	 * @param fillColor the colour the rectangle will be filled with when drawn
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameRectangle(Rectangle rec, float borderWidth, Color borderColor, Color fillColor, float transparency)
	{
		this.rec = new Rectangle(rec.x,rec.y,rec.width,rec.height);
		SetBorderWidth(borderWidth);
		this.borderColor = borderColor;
		this.fillColor = fillColor;
		SetTransparency(transparency);
		filled = true;
		SetDimension();
	}
	
	private void SetDimension()
	{
		centre = new Vector2F(rec.x + (rec.width * 0.5f), rec.y + (rec.height * 0.5f));
		top = rec.y;
		right = rec.x + rec.width;
		bottom = rec.y + rec.height;
		left = rec.x;
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
	 * <b><i>GetTop</b></i><p>
	 * &nbsp&nbsp{@code public float GetTop()}<p>
	 * Retrieve the location of the top wall of the shape
	 * @return A float representing the top wall of the shape, its y value
	 */
	public float GetTop()
	{
		return top;
	}
	
	/**
	 * <b><i>GetRight</b></i><p>
	 * &nbsp&nbsp{@code public float GetRight()}<p>
	 * Retrieve the location of the right wall of the shape
	 * @return A float representing the right wall of the shape, its x + width value
	 */
	public float GetRight()
	{
		return right;
	}
	
	/**
	 * <b><i>GetBottom</b></i><p>
	 * &nbsp&nbsp{@code public float GetBottom()}<p>
	 * Retrieve the location of the bottom wall of the shape
	 * @return A float representing the bottom wall of the shape, its y + height value
	 */
	public float GetBottom()
	{
		return bottom;
	}
	
	/**
	 * <b><i>GetLeft</b></i><p>
	 * &nbsp&nbsp{@code public float GetLeft()}<p>
	 * Retrieve the location of the left wall of the shape
	 * @return A float representing the left wall of the shape, its x value
	 */
	public float GetLeft()
	{
		return left;
	}
	
	/**
	 * <b><i>GetCentre</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetCentre()}<p>
	 * Retrieve the central coordinate of the shape
	 * @return the coordinate of the centre of the shape
	 */
	public Vector2F GetCentre()
	{
		return centre;
	}
	
	/**
	 * <b><i>GetRec</b></i><p>
	 * &nbsp&nbsp{@code public Rectangle GetRec()}<p>
	 * Retrieve the rectangle dimensions of the object
	 * @return The rectangle representing the object's rectangle dimensions
	 */
	public Rectangle GetRec()
	{
		return rec;
	}
	
	/**
	 * <b><i>SetWidth</b></i><p>
	 * &nbsp&nbsp{@code public void SetWidth(float width)}<p>
	 * Set the width dimension of the rectangle to a new value minimized at 1
	 * @param width The width to assign the rectangle to, minimized at 1
	 */
	public void SetWidth(float width)
	{
		rec.width = Math.max(1,(int)width);
		SetDimension();
	}
	
	/**
	 * <b><i>SetHeight</b></i><p>
	 * &nbsp&nbsp{@code public void SetHeight(float height)}<p>
	 * Set the height dimension of the rectangle to a new value minimized at 1
	 * @param height The width to assign the rectangle to, minimized at 1
	 */
	public void SetHeight(float height)
	{
		rec.height = Math.max(1,(int)height);
		SetDimension();
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
		rec.x += deltaX;
		rec.y += deltaY;
		SetDimension();
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
		rec.x = (int)x;
		rec.y = (int)y;
		SetDimension();
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
			Draw.FillRect(gfx, rec.x,rec.y,rec.width,rec.height, fillColor, transparency);
		}
		
		Draw.Rect(gfx, rec.x,rec.y,rec.width,rec.height, borderWidth, borderColor, transparency);
	}
}