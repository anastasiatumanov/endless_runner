package Engine.Gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import Engine.Core.Helper;
//import com.sun.javafx.geom.Point2D;

/**
 * <h1>Draw shapes, strings and images</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class Draw 
{
	/**
	 * <b><i>Dot</i></b><p>
	 * &nbsp&nbsp{@code public static void Dot(Graphics2D gfx, float x, float y, Color colour, float transparency)}<p>
	 * Draw a single dot to the screen as a given location
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the first point
	 * @param y The y coordinate of the first point
	 * @param colour The colour the line is to be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Dot(Graphics2D gfx, float x, float y, Color colour, float transparency)
	{
		Line(gfx,x,y,x,y,1,colour,transparency);
	}
	
	/**
	 * <b><i>Line</i></b><p>
	 * &nbsp&nbsp{@code public static void Line(Graphics2D gfx, float x1, float y1, float x2, float y2,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float lineWidth, Color colour, float transparency)}<p>
	 * Draw a straight line between two points at a given width and colour
	 * @param gfx The Graphics2D object to draw to
	 * @param x1 The x coordinate of the first point
	 * @param y1 The y coordinate of the first point
	 * @param x2 The x coordinate of the second point
	 * @param y2 The y coordinate of the second point
	 * @param lineWidth The width of the line in pixels
	 * @param colour The colour the line is to be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Line(Graphics2D gfx, 
							float x1, float y1, float x2, float y2, 
							float lineWidth, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Stroke curStroke = gfx.getStroke();
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setStroke(new BasicStroke(lineWidth));
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the line
		gfx.draw(new Line2D.Float(x1,y1,x2,y2));
		
		//Reset elements
		gfx.setStroke(curStroke);
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>Rect</i></b><p>
	 * &nbsp&nbsp{@code public static void Rect(Graphics2D gfx, float x, float y, float width,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp
	 * {@code float height, float lineWidth, Color colour)}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp
	 * {@code float transparency)}<p>
	 * Draw an axis aligned rectangle at a given location and size
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the top left corner of the rectangle
	 * @param y The y coordinate of the top left corner of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param lineWidth The width of the lines that make up the four sides of the rectangle
	 * @param colour The colour the side lines are to be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Rect(Graphics2D gfx, 
							float x, float y, float width, float height, 
							float lineWidth, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Stroke curStroke = gfx.getStroke();
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setStroke(new BasicStroke(lineWidth));
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the Rectangle
		gfx.draw(new Rectangle2D.Float(x, y, width, height));
		
		//Reset elements
		gfx.setStroke(curStroke);
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>FillRect</i></b><p>
	 * &nbsp&nbsp{@code public static void FillRect(Graphics2D gfx, float x, float y, float width,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float height, Color colour, float transparency)}<p>
	 * Draw a filled in axis aligned rectangle at a given location and size
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the top left corner of the rectangle
	 * @param y The y coordinate of the top left corner of the rectangle
	 * @param width The width of the rectangle
	 * @param height The height of the rectangle
	 * @param colour The fill colour of the rectangle
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void FillRect(Graphics2D gfx, 
								float x, float y, float width, float height, 
								Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the Rectangle
		gfx.fill(new Rectangle2D.Float(x, y, width, height));
		
		//Reset elements
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>Ellipse</i></b><p>
	 * &nbsp&nbsp{@code public static void Ellipse(Graphics2D gfx, float x, float y, float radH,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code  float radV, float lineWidth, Color colour,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float transparency)}<p>
	 * Draw an ellipse centred at (x,y) with given horizontal and vertical radii
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the centre of the ellipse
	 * @param y The y coordinate of the centre of the ellipse
	 * @param radH The horizontal radius of the ellipse
	 * @param radV The vertical radius of the ellipse
	 * @param lineWidth The width of the line that makes up the edge of the ellipse
	 * @param colour The colour the edge will be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Ellipse(Graphics2D gfx, 
							   float x, float y, float radH, float radV, 
							   float lineWidth, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Stroke curStroke = gfx.getStroke();
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setStroke(new BasicStroke(lineWidth));
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the Ellipse
		gfx.draw(new Ellipse2D.Float(x - radH, y - radV, radH * 2, radV * 2));
		
		//Reset elements
		gfx.setStroke(curStroke);
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>FillEllipse</i></b><p>
	 * &nbsp&nbsp{@code public static void FillEllipse(Graphics2D gfx, float x, float y, float radH,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp
	 * {@code float radV, Color colour, float transparency)}<p>
	 * Draw a filled ellipse centred at (x,y) with given horizontal and vertical radii
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the centre of the ellipse
	 * @param y The y coordinate of the centre of the ellipse
	 * @param radH The horizontal radius of the ellipse
	 * @param radV The vertical radius of the ellipse
	 * @param colour The fill colour of the ellipse
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void FillEllipse(Graphics2D gfx, 
								   float x, float y, float radH, float radV, 
								   Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the Ellipse
		gfx.fill(new Ellipse2D.Float(x - radH, y - radV, radH * 2, radV * 2));
		
		//Reset elements
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>Arc</i></b><p>
	 * &nbsp&nbsp{@code public static void Arc(Graphics2D gfx, float x, float y, float radH,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float radV, float startAngle, float angleExtent,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code int arcType, float lineWidth, Color colour,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float transparency)}<p>
	 * Draw an arc edge as though it were part of an ellipse for a range of degrees
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the centre of the arc as though it were an ellipse
	 * @param y The y coordinate of the centre of the arc as though it were an ellipse
	 * @param radH The horizontal radius of the arc as though it were an ellipse
	 * @param radV The vertical radius of the arc as though it were an ellipse
	 * @param startAngle The angle in degrees to start the arc where 0, 90, 180 and 270 represent East, North, West and South respectively
	 * @param angleExtent The sweep distance of the arc from the startAngle, 360 would be a full circle
	 * @param arcType The type of arc to be drawn: Arc2D.PIE, Arc2D.OPEN, Arc2D.CHORD
	 * @param lineWidth The width of the line that makes up the edge of the arc
	 * @param colour The colour the edge will be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Arc(Graphics2D gfx, 
						   float x, float y, float radH, float radV, float startAngle, float angleExtent, int arcType, 
						   float lineWidth, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Stroke curStroke = gfx.getStroke();
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setStroke(new BasicStroke(lineWidth));
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the arc
		gfx.draw(new Arc2D.Float(x - radH, y - radV, radH * 2, radV * 2, startAngle, angleExtent, arcType));
		
		//Reset elements
		gfx.setStroke(curStroke);
		gfx.setColor(curColour);
	}
	
	/**
	 *<b><i>FillArc</i></b><p>
	 * &nbsp&nbsp{@code public static void FillArc(Graphics2D gfx, float x, float y, float radH,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float radV, float startAngle, float angleExtent,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code int arcType, Color colour, float transparency)}<p>
	 * Draw a filled arc as though it were part of an ellipse for a range of degrees
	 * @param gfx The Graphics2D object to draw to
	 * @param x The x coordinate of the centre of the arc as though it were an ellipse
	 * @param y The y coordinate of the centre of the arc as though it were an ellipse
	 * @param radH The horizontal radius of the arc as though it were an ellipse
	 * @param radV The vertical radius of the arc as though it were an ellipse
	 * @param startAngle The angle in degrees to start the arc where 0, 90, 180 and 270 represent East, North, West and South respectively
	 * @param angleExtent The sweep distance of the arc from the startAngle, 360 would be a full circle
	 * @param arcType The type of arc to be drawn: Arc2D.PIE, Arc2D.OPEN, Arc2D.CHORD
	 * @param colour The fill colour of the arc
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void FillArc(Graphics2D gfx, 
			   float x, float y, float radH, float radV, float startAngle, float angleExtent, int arcType, 
			   Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the arc
		gfx.fill(new Arc2D.Float(x - radH, y - radV, radH * 2, radV * 2, startAngle, angleExtent, arcType));
		
		//Reset elements
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>Cone</i></b><p>
	 * &nbsp&nbsp{@code public static void Cone(Graphics2D gfx, Vector2F coneOrigin, Vector2F coneDir,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float angleSpan, float coneLength, float lineWidth,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code Color colour, float transparency)}<p>
	 * Draw a Cone aimed in a particular direction with a defined angle spread
	 * @param gfx The Graphics2D object to draw to
	 * @param coneOrigin The point the cone originates from, its point
	 * @param coneDir The direction the cone is spreading towards
	 * @param angleSpan The full span of angle, or field of view, in degrees
	 * @param coneLength The length of the cone
	 * @param lineWidth The width of the line that makes up the edge of the cone
	 * @param colour The colour the edge will be drawn with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void Cone(Graphics2D gfx, Vector2F coneOrigin, Vector2F coneDir, float angleSpan, float coneLength, float lineWidth, Color colour, float transparency)
	{
		float angle = (float)(Math.toDegrees(Math.atan2(-coneDir.y, coneDir.x)) - (angleSpan * 0.5f));
		Draw.Arc(gfx, coneOrigin.x, coneOrigin.y, coneLength, coneLength, angle, angleSpan, Arc2D.PIE, lineWidth, colour, transparency);		
	}
	
	/**
	 * <b><i>FillCone</i></b><p>
	 * &nbsp&nbsp{@code public static void FillCone(Graphics2D gfx, Vector2F coneOrigin, Vector2F coneDir,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code float angleSpan, float coneLength, Color colour, float transparency)}<p>
	 * Draw a filled Cone aimed in a particular direction with a defined angle spread
	 * @param gfx The Graphics2D object to draw to
	 * @param coneOrigin The point the cone originates from, its point
	 * @param coneDir The direction the cone is spreading towards
	 * @param angleSpan The full span of angle, or field of view, in degrees
	 * @param coneLength The length of the cone
	 * @param colour The colour the cone will be filled with
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 */
	public static void FillCone(Graphics2D gfx, Vector2F coneOrigin, Vector2F coneDir, float angleSpan, float coneLength, Color colour, float transparency)
	{
		float angle = (float)(Math.toDegrees(Math.atan2(-coneDir.y, coneDir.x)) - (angleSpan * 0.5f));
		Draw.FillArc(gfx, coneOrigin.x, coneOrigin.y, coneLength, coneLength, angle, angleSpan, Arc2D.PIE, colour, transparency);		
	}
	
	/**
	 * <b><i>Text</i></b><p>
	 * &nbsp&nbsp{@code public static void Text(Graphics2D gfx, String text, float x, float y,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code Font font, Color colour, int transparency)}<p>
	 * Draw the given text on the screen using the specified location and font
	 * @param gfx The Graphics2D object to draw to
	 * @param text The text to be displayed on the screen
	 * @param x The x coordinate of the <b>bottom left corner</b> of the text
	 * @param y The y coordinate of the <b>bottom left corner</b> of the text
	 * @param font The font to draw the text using
	 * @param colour The colour the text will show up in
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 * @see Font
	 */
	public static void Text(Graphics2D gfx, String text, float x, float y, Font font, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		//Store old elements
		Font curFont = gfx.getFont();
		Color curColour = gfx.getColor();
		
		//Set new elements
		gfx.setFont(font);
		gfx.setColor(Helper.GetColor(colour.getRed(), colour.getGreen(), colour.getBlue(), (int)transparency));
		
		//Draw the text
		gfx.drawString(text, x, y);
		
		//Reset elements
		gfx.setFont(curFont);
		gfx.setColor(curColour);
	}
	
	/**
	 * <b><i>Text</i></b><p>
	 * &nbsp&nbsp{@code public static void Text(Graphics2D gfx, String text, float x, float y,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code String fontName, int fontStyle, int fontSize,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code Color colour, float transparency)}<p>
	 * Draw the given text on the screen using the specified location and font properties
	 * @param gfx The Graphics2D object to draw to
	 * @param text The text to be displayed on the screen
	 * @param x The x coordinate of the <b>bottom left corner</b> of the text
	 * @param y The y coordinate of the <b>bottom left corner</b> of the text
	 * @param fontName The name of the font, e.g. "Times New Roman"
	 * @param fontStyle The styling of the font, e.g. Font.PLAIN, Font.BOLD, Font.ITALIC, etc.
	 * @param fontSize The height of the font on screen (<b>NOTE:</b> not pixels)
	 * @param colour The colour the text will show up in
	 * @param transparency The level of transparency where 0 is invisible and 1 is opaque<p>
	 * @see Graphics2D
	 * @see Color
	 * @see Font
	 */
	public static void Text(Graphics2D gfx, String text, float x, float y, String fontName, int fontStyle, int fontSize, Color colour, float transparency)
	{
		transparency = Math.min(1f, Math.max(0f, transparency)) * 255f;
		
		Font tempFont = new Font(fontName, fontStyle, fontSize);
		Text(gfx, text, x, y, tempFont, colour, (int)transparency);
	}
	
	/**
	 * <b><i>Sprite</i></b><p>
	 * &nbsp&nbsp{@code public static void Sprite(Graphics2D gfx, SpriteSheet sprite)}<p>
	 * Draw an image to the screen at a stored location
	 * @param gfx The Graphics2D object to draw to
	 * @param sprite The image to be drawn to the screen at its stored destination
	 */
	public static void Sprite(Graphics2D gfx, SpriteSheet sprite)
	{
		sprite.Draw(gfx);
	}
}