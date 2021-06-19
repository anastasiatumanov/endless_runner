package Engine.Gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;

import Engine.Core.Helper;

/**
 * <h1>Game Polygon</h1>
 * <b>Creation Date:</b> Dec. 16, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class GamePolygon
{
	private Polygon poly;
	private Vector2F [] verts;
	private Color borderColor = Helper.WHITE;
	private Color fillColor = Helper.WHITE;
	private boolean filled = false;
	private float transparency = 1f;
	private float borderWidth = 2f;

	private Vector2F centre = Vector2F.ZERO;
	
	/**
	 * <b><i>GamePolygon</b></i><p>
	 * &nbsp&nbsp{@code public GamePolygon(Vector2F[] verts)}<p>
	 * Create a basic polygon with given vertices and default draw properties
	 * @param verts The collection of at least 2 ordered vertices that define the polygon
	 */
	public GamePolygon(Vector2F[] verts)
	{
		if (verts != null && verts.length >= 2)
		{
			this.verts = verts;
			CreatePoly();
			CalcCentre();
		}
	}
	
	/**
	 * <b><i>GamePolygon</b></i><p>
	 * &nbsp&nbsp{@code public GamePolygon(Vector2F[] verts, Color borderColor, float transparency)}<p>
	 * Create a basic polygon with given vertices and border draw properties
	 * @param verts The collection of at least 2 ordered vertices that define the polygon
	 * @param borderColor the border colour of the polygon
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GamePolygon(Vector2F[] verts, Color borderColor, float transparency)
	{
		if (verts != null && verts.length >= 2)
		{
			this.verts = verts;
			CreatePoly();
			this.borderColor = borderColor;
			this.transparency = Math.max(0, Math.min(1,transparency));
			CalcCentre();
		}
	}
	
	/**
	 * <b><i>GamePolygon</b></i><p>
	 * &nbsp&nbsp{@code public GamePolygon(Vector2F[] verts, Color borderColor, Color fillColor, float transparency)}<p>
	 * Create a basic polygon with given vertices and all draw data
	 * @param verts The collection of at least 2 ordered vertices that define the polygon
	 * @param borderColor the border colour of the polygon
	 * @param fillColor the colour the polygon will be filled with when drawn
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque
	 */
	public GamePolygon(Vector2F[] verts, Color borderColor, Color fillColor, float transparency)
	{
		if (verts != null && verts.length >= 2)
		{
			this.verts = verts;
			CreatePoly();
			this.borderColor = borderColor;
			this.fillColor = fillColor;
			filled = true;
			this.transparency = Math.max(0, Math.min(1,transparency));
			CalcCentre();
		}
	}
	
	private void CreatePoly()
	{
		poly = new Polygon();
		for (int i = 0; i < verts.length; i++)
		{
			poly.addPoint((int)verts[i].x, (int)verts[i].y);
		}
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
	 * <b><i>SetVertices</b></i><p>
	 * &nbsp&nbsp{@code public void SetVertices(Vector2F[] verts)}<p>
	 * Reassign the vertices of the polygon to a new collection of points
	 * @param verts The array of Vector2F points that make up the polygon
	 */
	public void SetVertices(Vector2F[] verts)
	{
		if (verts != null && verts.length >= 3)
		{
			this.verts = verts;
		}
	}
	
	/**
	 * <b><i>GetVertices</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F[] GetVertices()}<p>
	 * Retrieves the collection of vertices that make up the polygon
	 * @return An array of Vector2F points that make up the polygon
	 */
	public Vector2F[] GetVertices()
	{
		return verts;
	}
	
	/**
	 * <b><i>GetBoundingBox</b></i><p>
	 * &nbsp&nbsp{@code public Rectangle GetBoundingBox()}<p>
	 * Retrieve the bounding box surrounding the shape 
	 * @return A Rectangle representing a tightly fitted box around the shape
	 */
	public Rectangle GetBoundingBox()
	{
		return poly.getBounds();
	}
	
	/**
	 * <b><i>GetPoly</b></i><p>
	 * &nbsp&nbsp{@code public Polygon GetPoly()}<p>
	 * Retrieve the polygon shape of the GamePolygon object 
	 * @return The polygon object that maintains all the shape properties
	 */
	public Polygon GetPoly()
	{
		return poly;
	}
	
	/**
	 * <b><i>Draw</b></i><p>
	 * &nbsp&nbsp{@code public void Draw(Graphics2D gfx)}<p>
	 * Draws the shape to the screen with the current graphical settings applied
	 * @param gfx The Graphics2D object to draw to
	 */
	public void Draw(Graphics2D gfx)
	{
		//Store old elements
		Color curColour = gfx.getColor();
		
		if (filled)
		{
			//Draw the filled poly
			gfx.setColor(Helper.GetColor(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), (int)(transparency*255)));
			gfx.fill(poly);
		}
		
		//Draw the border
		if (verts != null && verts.length >= 3)
		{		
			for (int i = 0; i < verts.length-1; i++)
			{
				Draw.Line(gfx, verts[i].x, verts[i].y, verts[i+1].x, verts[i+1].y, borderWidth, borderColor, transparency);
			}
		}
		//Draw the closing line
		Draw.Line(gfx, verts[verts.length - 1].x, verts[verts.length - 1].y, verts[0].x, verts[0].y, borderWidth, borderColor, transparency);
		
		//Reset elements
		gfx.setColor(curColour);
	}
	
	private void CalcCentre()
	{
		if (verts != null && verts.length >= 3)
		{
			Rectangle boundingBox = poly.getBounds();
			centre = new Vector2F(boundingBox.x + (boundingBox.width * 0.5f),boundingBox.y + (boundingBox.height * 0.5f));
		}
		else
		{
			centre = Vector2F.ZERO;
		}
	}
	
	/**
	 * <b><i>Rotate</b></i><p>
	 * &nbsp&nbsp{@code public void Rotate(double angleInDegrees)}<p>
	 * Rotates the shape by a specified angle
	 * @param angleInDegrees The angle to rotate the shape
	 */
	public void Rotate(double angleInDegrees)
	{
		if (verts != null && verts.length >= 3)
		{
			double s = Math.sin(Math.toRadians(angleInDegrees));
			double c = Math.cos(Math.toRadians(angleInDegrees));
			
			poly.reset();
			for (int i = 0; i < verts.length; i++)
			{
				verts[i] = RotatePoint(angleInDegrees, verts[i], s, c);
				poly.addPoint((int)verts[i].x, (int)verts[i].y);
			}
		}
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
	
	/**
	 * <b><i>Scale</b></i><p>
	 * &nbsp&nbsp{@code public void Scale(float scaler)}<p>
	 * Scales the shape by a specified amount
	 * @param scaler The multiplier to apply to the shape size
	 */
	public void Scale(float scaler)
	{
		if (verts != null && verts.length >= 3)
		{
			poly.reset();
			for (int i = 0; i < verts.length; i++)
			{
				verts[i] = ScalePoint(scaler, verts[i]);
				poly.addPoint((int)verts[i].x, (int)verts[i].y);
			}
		}
	}
	
	private Vector2F ScalePoint(float scaler, Vector2F pt)
	{
		//Offset to origin for rotation around centre of polygon
		pt.x -= centre.x;
		pt.y -= centre.y;
		
		//Scale
		pt.x *= scaler;
		pt.y *= scaler;
		
		//Translate polygon back
		pt.x += centre.x;
		pt.y += centre.y;
		
		return pt;
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
		if (verts != null && verts.length >= 3)
		{
			poly.reset();
			for (int i = 0; i < verts.length; i++)
			{
				verts[i].x += deltaX;
				verts[i].y += deltaY;
				poly.addPoint((int)verts[i].x, (int)verts[i].y);
			}
			
			CalcCentre();
		}
	}
	
	/**
	 * <b><i>TranslateTo</b></i><p>
	 * &nbsp&nbsp{@code public void TranslateTo(float x, float y)}<p>
	 * Moves the shape to a given x,y coordinate representing the top 
	 * left of the shape's bounding box on the screen
	 * @param pt The coordinate of the shape's new location
	 */
	public void TranslateTo(Vector2F pt)
	{
		Vector2F curPt = new Vector2F(poly.getBounds().x, poly.getBounds().y);
		
		if (!curPt.Equals(pt))
		{
			Translate(pt.x - curPt.x, pt.y - curPt.y);
		}
	}
}