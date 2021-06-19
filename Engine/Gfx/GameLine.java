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
 * <h1>Game Line</h1>
 * <b>Creation Date:</b> Dec. 16, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class GameLine
{
	private Vector2F pt1 = Vector2F.ZERO;
	private Vector2F pt2 = Vector2F.ZERO;
	private Color color = Helper.WHITE;
	private float width = 1f;
	private float transparency = 1f;
	private Vector2F centre = Vector2F.ZERO;
	
	/**
	 * <b><i>GameLine</b></i><p>
	 * &nbsp&nbsp{@code public GameLine(float x1, float y1, float x2, float y2)}<p>
	 * Create a basic line with the given end points
	 * @param x1 The x component of the first end point in the line
	 * @param y1 The y component of the first end point in the line
	 * @param x2 The x component of the second end point in the line
	 * @param y2 The y component of the Second end point in the line
	 */
	public GameLine(float x1, float y1, float x2, float y2)
	{
		pt1 = new Vector2F(x1,y1);
		pt2 = new Vector2F(x2,y2);
		
		SetCentre();
	}
	
	/**
	 * <b><i>GameLine</b></i><p>
	 * &nbsp&nbsp{@code public GameLine(float x1, float y1, float x2, float y2, float width, Color color, float transparency)}<p>
	 * Create a basic line with the given end points and drawing data
	 * @param x1 The x component of the first end point in the line
	 * @param y1 The y component of the first end point in the line
	 * @param x2 The x component of the second end point in the line
	 * @param y2 The y component of the Second end point in the line
	 * @param width the pixel width of the line
	 * @param color	the colour of the line
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameLine(float x1, float y1, float x2, float y2, float width, Color color, float transparency)
	{
		pt1 = new Vector2F(x1,y1);
		pt2 = new Vector2F(x2,y2);

		this.width = width;
		this.color = color;
		SetTransparency(transparency);
		
		SetCentre();
	}
	
	/**
	 * <b><i>GameLine</b></i><p>
	 * &nbsp&nbsp{@code public GameLine(Vector2F pt1, Vector2F pt2)}<p>
	 * Create a basic line with the given end points
	 * @param pt1 The first end point in the line
	 * @param pt2 The second end point in the line
	 */
	public GameLine(Vector2F pt1, Vector2F pt2)
	{
		this.pt1 = pt1;
		this.pt2 = pt2;
		
		SetCentre();
	}
	
	/**
	 * 
	 * <b><i>GameLine</b></i><p>
	 * &nbsp&nbsp{@code public GameLine(Vector2F pt1, Vector2F pt2)}<p>
	 * Create a basic line with the given end points and drawing data
	 * @param pt1 The first end point in the line
	 * @param pt2 The second end point in the line
	 * @param width the pixel width of the line
	 * @param color	the colour of the line
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GameLine(Vector2F pt1, Vector2F pt2, float width, Color color, float transparency)
	{
		this.pt1 = pt1;
		this.pt2 = pt2;
		
		this.width = width;
		this.color = color;
		SetTransparency(transparency);
		
		SetCentre();
	}
	
	private void SetCentre()
	{
		Rectangle boundingBox = GetBoundingBox();
		centre = new Vector2F(boundingBox.x + (boundingBox.width * 0.5f), boundingBox.y + (boundingBox.height * 0.5f));
	}
	
	/**
	 * <b><i>GetBoundingBox</b></i><p>
	 * &nbsp&nbsp{@code public Rectangle GetBoundingBox()}<p>
	 * Retrieve the bounding box surrounding the shape 
	 * @return A Rectangle representing a tightly fitted box around the shape
	 */
	public Rectangle GetBoundingBox()
	{
		return new Rectangle((int)Math.min(pt1.x, pt2.x), 
							 (int)Math.min(pt1.y, pt2.y), 
							 (int)(Math.max(pt1.x, pt2.x) - Math.min(pt1.x, pt2.x)),
							 (int)(Math.max(pt1.y, pt2.y) - Math.min(pt1.y, pt2.y)));
	}

	/**
	 * <b><i>SetColor</b></i><p>
	 * &nbsp&nbsp{@code public void SetColor(Color color)}<p>
	 * Assigns the current colour of the shape 
	 * @param color the colour to draw the shape in.
	 */
	public void SetColor(Color color)
	{
		this.color = color;
	}
	
	/**
	 * <b><i>GetColor</b></i><p>
	 * &nbsp&nbsp{@code public Color GetColor()}<p>
	 * Retrieve the current colour of the shape 
	 * @return The current colour of the shape during draw
	 */
	public Color GetColor()
	{
		return color;
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
	 * <b><i>SetWidth</b></i><p>
	 * &nbsp&nbsp{@code public void SetWidth(float width)}<p>
	 * Sets the pixel width of the shape 
	 * @param width The number of pixels wide the line is to be drawn with
	 */
	public void SetWidth(float width)
	{
		this.width = Math.max(1f, width);
	}
	
	/**
	 * <b><i>GetWidth</b></i><p>
	 * &nbsp&nbsp{@code public float GetWidth()}<p>
	 * Retrieves the pixel width of the shape 
	 * @return A float value reperesenting the pixel width of the line to be drawn
	 */
	public float GetWidth()
	{
		return width;
	}
	
	/**
	 * <b><i>SetPt1</b></i><p>
	 * &nbsp&nbsp{@code public void SetPt1(float x, float y)}<p>
	 * Sets the coordinates of the first point in the line 
	 * @param x The x component of the new coordinate
	 * @param y The y component of the new coordinate
	 */
	public void SetPt1(float x, float y)
	{
		pt1.x = x;
		pt1.y = y;
		
		SetCentre();
	}
	
	/**
	 * <b><i>SetPt2</b></i><p>
	 * &nbsp&nbsp{@code public void SetPt2(float x, float y)}<p>
	 * Sets the coordinates of the second point in the line 
	 * @param x The x component of the new coordinate
	 * @param y The y component of the new coordinate
	 */
	public void SetPt2(float x, float y)
	{
		pt2.x = x;
		pt2.y = y;
		
		SetCentre();
	}
	
	/**
	 * <b><i>SetPts</b></i><p>
	 * &nbsp&nbsp{@code public void SetPts(float x1, float y1, float x2, float y2)}<p>
	 * Sets the coordinates of both end points of the line 
	 * @param x1 The x component of the new coordinate for the first end point
	 * @param y1 The y component of the new coordinate for the first end point
	 * @param x2 The x component of the new coordinate for the second end point
	 * @param y2 The y component of the new coordinate for the second end point
	 */
	public void SetPts(float x1, float y1, float x2, float y2)
	{
		pt1.x = x1;
		pt1.y = y1;
		pt2.x = x2;
		pt2.y = y2;
		
		SetCentre();
	}
	
	/**
	 * <b><i>GetPt1</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetPt1()}<p>
	 * Retrieves the coordinate of the first end point of the line
	 * @return The coordinate of the first end point of the line
	 */
	public Vector2F GetPt1()
	{
		return pt1;
	}
	
	/**
	 * <b><i>GetPt2</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetPt2()}<p>
	 * Retrieves the coordinate of the second end point of the line
	 * @return The coordinate of the second end point of the line
	 */
	public Vector2F GetPt2()
	{
		return pt2;
	}
	
	/**
	 * <b><i>Draw</b></i><p>
	 * &nbsp&nbsp{@code public void Draw(Graphics2D gfx)}<p>
	 * Draws the shape to the screen with the current graphical settings applied
	 * @param gfx The Graphics2D object to draw to
	 */
	public void Draw(Graphics2D gfx)
	{
		Draw.Line(gfx, pt1.x, pt1.y, pt2.x, pt2.y, width, color, transparency);
	}
	
	/**
	 * <b><i>Length</b></i><p>
	 * &nbsp&nbsp{@code public float Length()}<p>
	 * Calculates the length of the line
	 * @return A float representing the length of the line
	 */
	public float Length()
	{
		return (float)(Math.sqrt(Math.pow(pt2.x - pt1.x, 2) + Math.pow(pt2.y - pt1.y, 2)));
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
		pt1.x += deltaX;
		pt1.y += deltaY;
		pt2.x += deltaX;
		pt2.y += deltaY;
		
		SetCentre();
	}
	
	/**
	 * <b><i>Rotate</b></i><p>
	 * &nbsp&nbsp{@code public void Rotate(double angleInDegrees)}<p>
	 * Rotates the shape by a specified angle
	 * @param angleInDegrees The angle to rotate the shape
	 */
	public void Rotate(double angleInDegrees)
	{
		double s = Math.sin(Math.toRadians(angleInDegrees));
		double c = Math.cos(Math.toRadians(angleInDegrees));
		
		pt1 = RotatePoint(angleInDegrees, pt1, s, c);
		pt2 = RotatePoint(angleInDegrees, pt2, s, c);
	}
	
	private Vector2F RotatePoint(double angleInDegrees, Vector2F pt, double sin, double cos)
	{
		//Offset to origin for rotation around centre of polygon
		pt.x -= centre.x;
		pt.y -= centre.y;
		
		//Rotate
		pt.x = (float)(pt.x * cos + pt.y * sin);
		pt.y = (float)(pt.x * -sin + pt.y * cos);
		
		//Translate polygon back
		pt.x += centre.x;
		pt.y += centre.y;
		
		return pt;
	}
}