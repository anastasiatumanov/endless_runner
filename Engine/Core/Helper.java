package Engine.Core;

import java.awt.Color;
import java.awt.Rectangle;
import java.util.Random;

import Engine.Gfx.GameCircle;
import Engine.Gfx.GameLine;
import Engine.Gfx.GamePolygon;
import Engine.Gfx.GameRectangle;
import Engine.Gfx.Vector2F;

/**
 * <h1>Common Data, Objects and Functionality</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.2 
 */
public class Helper 
{
	/**
	 * The one Random object to be used throughout the game for consistency
	 */
	public static Random rng = new Random();
	
	
	public static Color WHITE = GetColor(Color.WHITE);
	public static Color RED = GetColor(Color.RED);
	public static Color GREEN = GetColor(Color.GREEN);
	public static Color BLUE = GetColor(Color.BLUE);
	public static Color ORANGE = GetColor(Color.ORANGE);
	public static Color YELLOW = GetColor(Color.YELLOW);
	public static Color GRAY = GetColor(Color.GRAY);
	public static Color BLACK = GetColor(Color.BLACK);
	public static Color PINK = GetColor(Color.PINK);
	public static Color CYAN = GetColor(Color.CYAN);
	public static Color DARK_GRAY = GetColor(Color.DARK_GRAY);
	public static Color LIGHT_GRAY = GetColor(Color.LIGHT_GRAY);
	public static Color MAGENTA = GetColor(Color.MAGENTA);
	
  /**
	 * <b><i>Clamp</i></b><p>
	 * &nbsp&nbsp{@code public static int Clamp(int value, int min, int max)}<p>
	 * Force a number to be within the range set by min and max
	 * @param value The value to be forced within the min and max
	 * @param min The smallest allowable value
   * @param max The largest allowable value
	 * @return A number within the range of min and max, inclusive
	 */
  public static int Clamp(int value, int min, int max)
  {
    return (int)Math.max(min, (int)Math.min(value, max));
  }

	/**
	 * <b><i>Clamp</i></b><p>
	 * &nbsp&nbsp{@code public static float Clamp(float value, float min, float max)}<p>
	 * Force a number to be within the range set by min and max
	 * @param value The value to be forced within the min and max
	 * @param min The smallest allowable value
   * @param max The largest allowable value
	 * @return A number within the range of min and max, inclusive
	 */
  public static float Clamp(float value, float min, float max)
  {
    return Math.max(min, Math.min(value,max));
  }

	/**
	 * <b><i>RandomValue</i></b><p>
	 * &nbsp&nbsp{@code public static float RandomValue(float low, float high)}<p>
	 * Generate a random float value between low and high, inclusive of low and high
	 * @param low The smallest possible random float value
	 * @param high The largest possible random float value
	 * @return A random number value between low and high, inclusive
	 */
	public static float RandomValue(float low, float high)
	{
		return (rng.nextFloat() * (high - low)) + low;
	}
	
	/**
	 * <b><i>GetColor</i></b><p>
	 * &nbsp&nbsp{@code public static Color GetColor(int red, int green, int blue)}<p>
	 * Create a properly formatted Color object given the R,G,B components
	 * @param red The Red Color component desired between 0 - 255
	 * @param green The Green Color component desired between 0 - 255
	 * @param blue The Blue Color component desired between 0 - 255
	 * @return A properly formatted Color object
	 */
	public static Color GetColor(int red, int green, int blue)
	{
		return new Color(red, blue, green);
	}
	
	/**
	 * <b><i>GetColor</i></b><p>
	 * &nbsp&nbsp{@code public static Color GetColor(int red, int green, int blue,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code int transparency)}<p>
	 * Create a properly formatted Color object given the R,G,B and transparency components
	 * @param red The Red Color component desired between 0 - 255
	 * @param green The Green Color component desired between 0 - 255
	 * @param blue The Blue Color component desired between 0 - 255
	 * @param transparency The Transparency Color component desired between 0 - 255
	 * @return A properly formatted Color object
	 */
	public static Color GetColor(int red, int green, int blue, int transparency)
	{
		return new Color(red, blue, green, transparency);
	}
	
	/**
	 * <b><i>GetColor</i></b><p>
	 * &nbsp&nbsp{@code public static Color GetColor(Color colour)}<p>
	 * Create a properly formatted Color object given a generic Color object
	 * @param colour A Generic Color object to be properly formatted for output
	 * @return A properly formatted Color object
	 */
	public static Color GetColor(Color colour)
	{
		return new Color(colour.getRed(), colour.getBlue(), colour.getGreen());
	}
	
	/**
	 * <b><i>GetColor</i></b><p>
	 * &nbsp&nbsp{@code public static Color GetColor(Color colour, int transparency)}<p>
	 * Create a properly formatted Color object given a generic Color object and Transparency component
	 * @param colour A Generic Color object to be properly formatted for output
	 * @param transparency The Transparency Color component desired between 0 - 255
	 * @return A properly formatted Color object
	 */
	public static Color GetColor(Color colour, int transparency)
	{
		return new Color(colour.getRed(), colour.getBlue(), colour.getGreen(), transparency);
	}
	
	//////////////////////////////////////////////////////////////////////	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//					INTERSECTION TESTS								//
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////
	//					RECTANGLE INTERSECTION							//
	//////////////////////////////////////////////////////////////////////
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(Rectangle box, Vector2F pt)}<p>
	 * Test to see if pt is contained inside of box
	 * @param box The Rectangle to be tested against
	 * @param pt The point to be tested to be inside the box 
	 * @return true if pt lies within all four walls of box, false otherwise
	 */
	public static boolean Intersects(Rectangle box, Vector2F pt)
	{
		if (pt.x >= box.x && pt.x <= box.x + box.width &&	// between left and right walls
			pt.y >= box.y && pt.y <= box.y + box.height)	// between top and bottom walls
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameRectangle box, Vector2F pt)}<p>
	 * Test to see if pt is contained inside of box
	 * @param box The Rectangle to be tested against
	 * @param pt The point to be tested to be inside the box 
	 * @return true if pt lies within all four walls of box, false otherwise
	 */
	public static boolean Intersects(GameRectangle box, Vector2F pt)
	{
		return Intersects(box.GetRec(), pt);
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(Rectangle box1, Rectangle box2)}<p>
	 * Test for collision between two axis aligned (non-rotated) rectangles
	 * @param box1 The first test rectangle
	 * @param box2 The second test rectangle to be tested against
	 * @return true if any portion of box1 overlaps box2, false otherwise
	 */
	public static boolean Intersects(Rectangle box1, Rectangle box2)
	{
		if (box1.x > box2.x + box2.width || box1.x + box1.width < box2.x ||	// too far left or right
			box1.y > box2.y + box2.height || box1.y + box1.height < box2.y)	//too far up or down
		{
			//Impossible collision
			return false;
		}
		
		//ALL impossible collisions ruled out, must be a collision
		return true;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameRectangle box1, GameRectangle box2)}<p>
	 * Test for collision between two rectangles, rotation permitted.  This is an expensive test and
	 * should be avoided if possible.
	 * @param box1 The first test rectangle
	 * @param box2 The second test rectangle to be tested against
	 * @return true if any portion of box1 overlaps box2, false otherwise
	 */
	public static boolean Intersects(GameRectangle box1, GameRectangle box2)
	{
		//Step 1: Create two GamePolygons from the GameRectangles to allow for rotation
		GamePolygon poly1 = new GamePolygon(new Vector2F[]{new Vector2F(box1.GetLeft(), box1.GetTop()),
														   new Vector2F(box1.GetRight(), box1.GetTop()),
														   new Vector2F(box1.GetRight(), box1.GetBottom()),
														   new Vector2F(box1.GetLeft(), box1.GetBottom())});
		
		GamePolygon poly2 = new GamePolygon(new Vector2F[]{new Vector2F(box2.GetLeft(), box2.GetTop()),
				   										   new Vector2F(box2.GetRight(), box2.GetTop()),
				   										   new Vector2F(box2.GetRight(), box2.GetBottom()),
				   										   new Vector2F(box2.GetLeft(), box2.GetBottom())});
		
		//Step 2: Call the polygon-polygon collision test, more expensive, but functional
		return Intersects(poly1,poly2);
	}
	
	//////////////////////////////////////////////////////////////////////
	//					CIRCLE INTERSECTION								//
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameCircle circle, GameLine line)}<p>
	 * Test for collision between a circle and a line
	 * @param circle The circle to be tested for collision
	 * @param line The line to be tested for collision against the circle
	 * @return true if any portion of line overlaps circle, false otherwise
	 */
	public static boolean Intersects(GameCircle circle, GameLine line)
	{
		//Pretest end points before complex calcs
		if (Vector2F.Distance(line.GetPt1(), circle.GetCentre()) <= circle.GetRad() ||
			Vector2F.Distance(line.GetPt2(), circle.GetCentre()) <= circle.GetRad())
		{
			return true;
		}
		
		//Project Centre point onto the line
		Vector2F AC = Vector2F.Subtract(circle.GetCentre(), line.GetPt1());
		Vector2F AB = new Vector2F(line.GetPt2().x - line.GetPt1().x,line.GetPt2().y - line.GetPt1().y);
		double coef = Vector2F.DotProduct(AC, AB) / Vector2F.DotProduct(AB, AB);
		Vector2F projPtOnLine = new Vector2F((float)(line.GetPt1().x + (coef * AB.x)),(float)(line.GetPt1().y + (coef * AB.y)));
		
		boolean ptOnLine = false;
		
		//Check to see if projected point is actually on the line
        if ((line.GetPt1().x < projPtOnLine.x && projPtOnLine.x < line.GetPt2().x) ||
            (line.GetPt1().x > projPtOnLine.x && projPtOnLine.x > line.GetPt2().x) ||
            (line.GetPt1().y < projPtOnLine.y && projPtOnLine.y < line.GetPt2().y) ||
            (line.GetPt1().y > projPtOnLine.y && projPtOnLine.y > line.GetPt2().y)) 
        {
                ptOnLine = true;
        }
		
		if (ptOnLine == true)
		{
			if (Vector2F.Distance(projPtOnLine, circle.GetCentre()) <= circle.GetRad())
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameCircle circle, Vector2F pt)}<p>
	 * Determine if a point is contained inside a circle
	 * @param circle The circle to be tested for collision
	 * @param pt The point to be tested for collision against the circle
	 * @return true if the point exists inside of the circle, false otherwise
	 */
	public static boolean Intersects(GameCircle circle, Vector2F pt)
	{
		return circle.DistToCentre(pt) <= circle.GetRad();
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameCircle circle1, GameCircle circle2)}<p>
	 * Determine if two circles overlap
	 * @param circle The first circle to be tested for collision
	 * @param circle2 The second circle to be tested for collision
	 * @return true if any portion of circle1 overlaps circle2, false otherwise
	 */
	public static boolean Intersects(GameCircle circle, GameCircle circle2)
	{
		double dist = 0;
		
		dist = Math.pow(circle2.GetCentre().x - circle.GetCentre().x, 2) + Math.pow(circle2.GetCentre().y - circle.GetCentre().y, 2);
		if (dist <= Math.pow((circle.GetRad() + circle2.GetRad()),2))
		{
			return true;
		}
		
		//Circles are too far apart for a collision
		return false;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameCircle circle, Rectangle box)}<p>
	 * Determine if a circle overlaps an axis aligned (non-rotated) rectangle
	 * @param circle The circle to be tested for collision
	 * @param box The rectangle to be tested against for collision
	 * @return true if any portion of circle overlaps box, false otherwise
	 */
	public static boolean Intersects(GameCircle circle, Rectangle box)
	{
		//Create a local variable to represent the return value that may change
	    //multiple times through this subprogram
	    boolean result = false;

	    //First we need to find the bounding circle around box,
	    //Since this is a generic subprogram, we can't assume we have calculated it previously
	    //The centre located midway between the width and height, 
	    //the radius is the distance from the centre to any corner
	    Vector2F boxCentre = new Vector2F(box.x + (box.width * 0.5f), box.y + (box.height * 0.5f));
	    int boxRadius = (int)Math.sqrt(Math.pow(box.width*0.5f,2) + Math.pow(box.height*0.5f,2));

	    //Perform the pre circle-cicle collision to deside whether to continue or not
	    //OPTIONAL: This test is not necessary, but will make the program more efficient since
	    //it is much more expensive to test for collision with the box then a circle.  So if
	    //there is not circle collision the box collision is impossible so there would be
	    //no sense in testing for it. Remember this whole process occurs many times per second
	    if (Intersects(circle, new GameCircle(boxCentre.x, boxCentre.y, boxRadius)))
	    {
	        //Now we must check the three possible methods a circle can intersect
	        //with a rectangle
	        //1. The circle centre is inside the rectangle (similar to Rectangle Point Collision)
	        //2. The circle's centre is within the radius distance of the four walls, but outside the rectangle
	        //3. The circle's centre is within the radius distance of one of the four corners of the rectangle
	        //if (PtToBox(box, centre) == true)	// Step 1
	    	if (Helper.Intersects(box,circle.GetCentre()) == true) // Step 1
	        {
	            result = true;
	        }
	        else if ((box.x - circle.GetRad()) <= circle.GetCentre().x && (box.x + box.width + circle.GetRad()) >= circle.GetCentre().x &&     // Step 2, left and right boundaries
	                (box.y - circle.GetRad()) <= circle.GetCentre().y && (box.y + box.height + circle.GetRad()) >= circle.GetCentre().y)       // Step 2, Top and bottom boundaries
	        {
	            result = true;
	        }
	        else 	// Step 3, Corner Points comparisons
	        {
	            //This is a check to see if the distance from any box corner is smaller than the radius
	            //of the circle, ONLY do needed calculations.  To achieve this we could create a complex series of
	            //nested ifs and elses, but it is easier to take advantage of the fact that Java does what we
	            //call Short Circuit comparisons.
	            //
	            //The way this works is simple if you have an if statement with an OR, it will only check the next
	            //comparison in the OR if the previous one failed, which means in our case it won't bother with
	            //comparison and calculations after we find the first point within the radius distance away.
	            //Worst case scenario is it is not within the radius and each point is compared
	            if (Intersects(circle, new Vector2F(box.x, box.y)) ||							//Top Left
	            	Intersects(circle, new Vector2F(box.x + box.width, box.y)) ||				//Top Right
	            	Intersects(circle, new Vector2F(box.x + box.width, box.y + box.height)) ||	//Bottom Right
	            	Intersects(circle, new Vector2F(box.x, box.y + box.height)))				//Bottom Left
	            {
	                result = true;
	            }
	        }

	    }

	    return result;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameCircle circle, GameRectangle box)}<p>
	 * Determine if a circle overlaps a rectangle, rotations allowed.  This is an expensive test and
	 * should be avoided if possible.
	 * @param circle The circle to be tested for collision
	 * @param box The rectangle to be tested against for collision
	 * @return true if any portion of circle overlaps box, false otherwise
	 */
	public static boolean Intersects(GameCircle circle, GameRectangle box)
	{
		//Step1: Break box down into 4 lines
		GameLine []lines = new GameLine[]{new GameLine(box.GetLeft(), box.GetTop(), box.GetRight(), box.GetTop()),		//Top
										  new GameLine(box.GetRight(), box.GetTop(), box.GetRight(), box.GetBottom()),	//Right
										  new GameLine(box.GetLeft(), box.GetBottom(), box.GetRight(), box.GetBottom()),//Bottom
										  new GameLine(box.GetLeft(), box.GetTop(), box.GetRight(), box.GetBottom())};	//Left
		
		//Return true if any edge overlaps circle or the circle centre is inside the box or 
		//the box's centre is inside the circle
		return Intersects(box, circle.GetCentre()) || Intersects(circle, box.GetCentre()) ||
													  Intersects(circle,lines[0]) || 
													  Intersects(circle,lines[1]) || 
													  Intersects(circle,lines[2]) || 
													  Intersects(circle,lines[3]);
	}
	
	
	//////////////////////////////////////////////////////////////////////	
	//					LINE INTERSECTION								//
	//////////////////////////////////////////////////////////////////////
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameLine line, Vector2F pt)}<p>
	 * Determine if a point exists on top of a line segment
	 * @param line The line being tested against
	 * @param pt the point being tested
	 * @return true if the point exists on the line segement
	 */
	public static boolean Intersects(GameLine line, Vector2F pt)
	{
		float a = pt.x - line.GetPt1().x;
		float b = pt.y - line.GetPt1().y;
		float c = line.GetPt2().x - line.GetPt1().x;
		float d = line.GetPt2().y - line.GetPt1().y;
		
		float dot = a * c + b * d;
		float len = c * c + d * d;
		float para = dot / len;
		
		float x = 0;
		float y = 0;
		
		if (para < 0)
		{
			x = line.GetPt1().x;
			y = line.GetPt1().y;
		}
		else if (para > 1)
		{
			x = line.GetPt2().x;
			y = line.GetPt2().y;
		}
		else
		{
			x = line.GetPt1().x + para * c;
			y = line.GetPt1().y + para * d;
		}
		
		float xDiff = pt.x - x;
		float yDiff = pt.y - y;
		float hypo = (float)(Math.sqrt((xDiff * xDiff) + (yDiff * yDiff)));
		
		if (hypo < 2)
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameLine line, GameLine line2)}<p>
	 * Determine if two line segments intersect
	 * @param line The first line being tested against
	 * @param line2 The second line being tested against
	 * @return true if any point on teh line segment touches the line2 segment
	 */
	public static boolean Intersects(GameLine line, GameLine line2)
	{
		/*
		Vector2F CmP = new Vector2F(line2.GetPt1().x - line.GetPt1().x, line2.GetPt1().y - line.GetPt1().y);
		Vector2F r = new Vector2F(line.GetPt2().x - line.GetPt1().x, line.GetPt2().y - line.GetPt1().y);
		Vector2F s = new Vector2F(line2.GetPt2().x - line2.GetPt1().x, line2.GetPt2().y - line2.GetPt1().y);
 
		float CmPxr = CmP.x * r.y - CmP.y * r.x;
		float CmPxs = CmP.x * s.y - CmP.y * s.x;
		float rxs = r.x * s.y - r.y * s.x;
 
		if (CmPxr == 0f)
		{
			// Lines are collinear, and so intersect if they have any overlap
			return ((line2.GetPt1().x - line.GetPt1().x < 0f) != (line2.GetPt1().x - line.GetPt2().x < 0f))
				|| ((line2.GetPt1().y - line.GetPt1().y < 0f) != (line2.GetPt1().y - line.GetPt2().y < 0f));
		}
 
		if (rxs == 0f)
		{
			return false; // Lines are parallel.
		}
 
		float rxsr = 1f / rxs;
		float t = CmPxs * rxsr;
		float u = CmPxr * rxsr;
 
		return (t >= 0f) && (t <= 1f) && (u >= 0f) && (u <= 1f);
		*/
		
		//The top of the equation that calculates how far along the line the point of
		//intersection exists, a value between 0 (first point) and 1, the second point
		float tNumer;
		float uNumer;
		
		//The bottom of the same equation, however if this value is 0 there is no intersection
		float denom;
		
		
		//p and q are simplified names to use in place of the first point in each line since these
		//values will be used
		Vector2F p = line.GetPt1();
        Vector2F q = line2.GetPt1();
        
        //We subtract the endpoints from one another to get their magnitude, this cancels out any
        //offset from the (0,0) origin allowing us to focus on just the numbers
        Vector2F lineDelta = new Vector2F(line.GetPt2().x - line.GetPt1().x, line.GetPt2().y - line.GetPt1().y);
        Vector2F line2Delta = new Vector2F(line2.GetPt2().x - line2.GetPt1().x, line2.GetPt2().y - line2.GetPt1().y);

        //These formulas are to calculate to scaling factors between 0 and 1 of where the point of intersection
        //lies on the lines.  t is for line 1, u for line 2. the × represents a Cross Product of two vectors, not
        //a multiplication.  This is a mathematical operation that determines the magnitude of the perpendicular
        //vector to the two lines, imagine this vector coming at you in the 3D z axis, the 2D Cross Product is its
        //magnitude
        // t = (line2Point1 − linePoint1) × line2Delta / (lineDelta × line2Delta)
        // u = (line2Point1 − linePoint1) × lineDelta / (lineDelta × line2Delta)

        //lineDelta × line2Delta), the denominator above
        denom = Vector2F.CrossProduct(lineDelta, line2Delta);

        if (denom == 0)
        {
            // lines are collinear (on top of each other) or parallel (side by side)
        	return false;
        }

        //Calculate the two numerators here to keep the overall calculation below more readable
        tNumer = Vector2F.CrossProduct(Vector2F.Subtract(q,p), line2Delta);
        uNumer = Vector2F.CrossProduct(Vector2F.Subtract(q,p), lineDelta);

        //Calculate the scaling values that determine where on the lines the point of intersection exists
        float t = tNumer / denom;
        float u = uNumer / denom;

        //If either t or u has a value outside the range of 0 to 1 the intersection point lies outside the
        //range of the two line segment end points, so no collision occured
        if (t < 0 || t > 1 || u < 0 || u > 1)
        {
            // line segments do not intersect within their ranges
        	return false;
        }

        return true;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static Vector2F Intersects2(GameLine line, GameLine line2)}<p>
	 * Determine if and where two line segments intersect
	 * @param line The first line being tested against
	 * @param line2 The second line being tested against
	 * @return The point of intersection if it exists, null otherwise
	 */
	public static Vector2F Intersects2(GameLine line, GameLine line2)
	{
		//Where do the lines intersect
		Vector2F intersectionPoint = null;
		
		//The top of the equation that calculates how far along the line the point of
		//intersection exists, a value between 0 (first point) and 1, the second point
		float tNumer;
		float uNumer;
		
		//The bottom of the same equation, however if this value is 0 there is no intersection
		float denom;
		
		
		//p and q are simplified names to use in place of the first point in each line since these
		//values will be used
		Vector2F p = line.GetPt1();
        Vector2F q = line2.GetPt1();
        
        //We subtract the endpoints from one another to get their magnitude, this cancels out any
        //offset from the (0,0) origin allowing us to focus on just the numbers
        Vector2F lineDelta = new Vector2F(line.GetPt2().x - line.GetPt1().x, line.GetPt2().y - line.GetPt1().y);
        Vector2F line2Delta = new Vector2F(line2.GetPt2().x - line2.GetPt1().x, line2.GetPt2().y - line2.GetPt1().y);

        //These formulas are to calculate to scaling factors between 0 and 1 of where the point of intersection
        //lies on the lines.  t is for line 1, u for line 2. the × represents a Cross Product of two vectors, not
        //a multiplication.  This is a mathematical operation that determines the magnitude of the perpendicular
        //vector to the two lines, imagine this vector coming at you in the 3D z axis, the 2D Cross Product is its
        //magnitude
        // t = (line2Point1 − linePoint1) × line2Delta / (lineDelta × line2Delta)
        // u = (line2Point1 − linePoint1) × lineDelta / (lineDelta × line2Delta)

        //lineDelta × line2Delta), the denominator above
        denom = Vector2F.CrossProduct(lineDelta, line2Delta);

        if (denom == 0)
        {
            // lines are collinear (on top of each other) or parallel (side by side)
        	return null;
        }

        //Calculate the two numerators here to keep the overall calculation below more readable
        tNumer = Vector2F.CrossProduct(Vector2F.Subtract(q,p), line2Delta);
        uNumer = Vector2F.CrossProduct(Vector2F.Subtract(q,p), lineDelta);

        //Calculate the scaling values that determine where on the lines the point of intersection exists
        float t = tNumer / denom;
        float u = uNumer / denom;

        //If either t or u has a value outside the range of 0 to 1 the intersection point lies outside the
        //range of the two line segment end points, so no collision occured
        if (t < 0 || t > 1 || u < 0 || u > 1)
        {
            // line segments do not intersect within their ranges
        	return null;
        }

        //intersectionPoint = line1Point1 + (lineDelta * t);
        intersectionPoint = Vector2F.Add(p, Vector2F.Scaler(lineDelta, t));
        return intersectionPoint;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameLine line, Rectangle box)}<p>
	 * Determine if a line segment overlaps a rectangle
	 * @param line The first line being tested against
	 * @param box The rectangle being tested
	 * @return true if any point on the line segment overlaps the rectangle
	 */
	public static boolean Intersects(GameLine line, Rectangle box)
	{
		//Step 1: Pretest two line endpoints due to cost of future calculations
		if (Intersects(box,line.GetPt1()) || Intersects(box,line.GetPt2()))
		{
			return true;
		}
		
		//Step 2: neither line endpoint is inside the rectangle, further tests are required
		GameLine top = new GameLine(box.x, box.y, box.x + box.width, box.y);
		GameLine right = new GameLine(box.x + box.width, box.y, box.x + box.width, box.y + box.height);
		GameLine bottom = new GameLine(box.x, box.y + box.height, box.x + box.width, box.y + box.height);
		GameLine left = new GameLine(box.x, box.y, box.x, box.y + box.height);
		
		return Intersects(line, top) || Intersects(line, right) || Intersects(line, bottom) || Intersects(line, left);
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GameLine line, GameRectangle box)}<p>
	 * Determine if a line segment overlaps a rectangle, rotation allowed but produces an
	 * expensive test and should be avoided if possible
	 * @param line The first line being tested against
	 * @param box The rectangle being tested
	 * @return true if any point on the line segment overlaps the rectangle
	 */
	public static boolean Intersects(GameLine line, GameRectangle box)
	{
		//Step 1: Convert the two shapes to polygons before testing
		GamePolygon poly = new GamePolygon(new Vector2F[]{new Vector2F(box.GetLeft(), box.GetTop()),
				   										  new Vector2F(box.GetRight(), box.GetTop()),
				   										  new Vector2F(box.GetRight(), box.GetBottom()),
				   										  new Vector2F(box.GetLeft(), box.GetBottom())});
		GamePolygon poly2 = new GamePolygon(new Vector2F[]{line.GetPt1(), line.GetPt2()});

		//Step 2: perform a polygon-polygon test
		return Intersects(poly,poly2);
	}
	
	//////////////////////////////////////////////////////////////////////	
	//					POLYGON INTERSECTION							//
	//////////////////////////////////////////////////////////////////////
	

	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GamePolygon polygon, Vector2F pt)}<p>
	 * Determine if a point is contained within a polygon
	 * @param poly polygon being tested against
	 * @param pt The point being tested
	 * @return true if the point is contained within the polygon
	 */
	public static boolean Intersects(GamePolygon poly, Vector2F pt)
	{
		return poly.GetPoly().contains(pt.x,pt.y);
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public static boolean Intersects(GamePolygon poly1, GamePolygon poly2)}<p>
	 * Determine if two polygons overlap
	 * @param poly1 The first polygon being tested
	 * @param poly2 The second polygon geing tested
	 * @return true if part of polygon overlaps poly2
	 */
	public static boolean Intersects(GamePolygon poly1, GamePolygon poly2)
	{
		//Stage 1: Only do complex collision if bounding boxes collide
		if (Intersects(poly1.GetPoly().getBounds(), poly2.GetBoundingBox()))
		{
			if (PolyIntersect(poly1, poly2) || PolyIntersect(poly2, poly1))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * <b><i>PolyIntersect</i></b><p>
	 * &nbsp&nbsp{@code private static boolean PolyIntersect(GamePolygon poly1, GamePolygon poly2)}<p>
	 * Determine if two polygons overlap
	 * @param poly1 The first polygon being tested
	 * @param poly2 The second polygon geing tested
	 * @return true if any vertex from either polygon exists inside the other polygon, false otherwise
	 */
	private static boolean PolyIntersect(GamePolygon poly1, GamePolygon poly2)
	{
		Vector2F [] verts = poly1.GetVertices();
		
		for (int i = 0; i < verts.length; i++)
		{
			if (Intersects(poly2, verts[i]))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code private static boolean PolyIntersect(GamePolygon poly, Rectangle box)}<p>
	 * Determine if a polygon overlaps with an axis aligned (non-rotated) box
	 * @param poly The polygon being tested
	 * @param box The rectangle geing tested against
	 * @return true if the polygon overlaps with the rectangle, false otherwise
	 */
	public static boolean Intersects(GamePolygon poly, Rectangle box)
	{
		return poly.GetPoly().intersects(box);
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code private static boolean PolyIntersect(GamePolygon poly, GameRectangle box)}<p>
	 * Determine if a polygon overlaps with box, rotations allowed, but it is an expensive test
	 * and should be avoided if possible
	 * @param poly The polygon being tested
	 * @param box The rectangle geing tested against
	 * @return true if the polygon overlaps with the rectangle, false otherwise
	 */
	public static boolean Intersects(GamePolygon poly, GameRectangle box)
	{
		//Step 1: Convert box to a rectangle and do a polygon-polygon test
		GamePolygon poly2 = new GamePolygon(new Vector2F[]{new Vector2F(box.GetLeft(), box.GetTop()),
				   										   new Vector2F(box.GetRight(), box.GetTop()),
				   										   new Vector2F(box.GetRight(), box.GetBottom()),
				   										   new Vector2F(box.GetLeft(), box.GetBottom())});

		//Step 2: perform a polygon-polygon test
		return Intersects(poly,poly2);
	}
	
	/**
	 * <b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code private static boolean PolyIntersect(GamePolygon poly, GameLine line)}<p>
	 * Determine if a polygon overlaps with a line, this is an expensive test.
	 * @param poly The polygon being tested
	 * @param line The line being tested against
	 * @return true if the line overlaps with the poly in any way, false otherwise
	 */
	public static boolean Intersects(GamePolygon poly, GameLine line)
	{
		//Step 1: Test end points of the line first
		if (Intersects(poly,line.GetPt1()) || Intersects(poly,line.GetPt2()))
		{
			return true;
		}
		
		//Step 2: Line is too big to fit fully inside poly and must be tested for
		//		  individual line intersection
		for (int i = 0; i < poly.GetVertices().length - 1; i++)
		{
			if (Intersects(line, new GameLine(poly.GetVertices()[i].x,poly.GetVertices()[i].y,
											  poly.GetVertices()[i+1].x,poly.GetVertices()[i+1].y)))
			{
				return true;
			}
		}
		
		if (Intersects(line, new GameLine(poly.GetVertices()[poly.GetVertices().length - 1].x,
										  poly.GetVertices()[poly.GetVertices().length - 1].y,
										  poly.GetVertices()[0].x,poly.GetVertices()[0].y)))
		{
			return true;
		}
		
		return false;
	}
	
	//////////////////////////////////////////////////////////////////////	
	//					CONE INTERSECTION							    //
	//////////////////////////////////////////////////////////////////////
	/**
	 *<b><i>Intersects</i></b><p>
	 * &nbsp&nbsp{@code public boolean Intersects(Vector2F coneOrigin, Vector2F coneDir,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code Vector2F testPoint, float fov, float range)}<p>
	 * Determine if a cone intersects with a given point, commonly used in enemy vision A.I.
	 * @param coneOrigin The point the cone originates from, its point
	 * @param coneDir The direction the cone is spreading towards
	 * @param testPoint The point to be tested for collision
	 * @param fov The field of view, angle span, the cone spreads across
	 * @param range The length of the cone
	 * @return true if the point exists inside the defined cone, false otherwise
	 */
	public static boolean Intersects(Vector2F coneOrigin, Vector2F coneDir, Vector2F testPoint, float fov, float range)
	{
		//Source: http://nic-gamedev.blogspot.ca/2011/11/using-vector-mathematics-and-bit-of.html
		//Alternate strategy is to calculate the angle the testPoint is at relative to the coneOrigin.
		//If that angle is between the fov angles there is a collision.
		
		boolean result = false;
				
		Vector2F testPointDir = Vector2F.Subtract(testPoint, coneOrigin);
		Vector2F coneDirNorm = Vector2F.Normalize(Vector2F.Copy(coneDir));
		
		float length = testPointDir.Length();
		testPointDir.Normalize();
		
		if (range < 0 || length <= range)
		{
			result = Vector2F.DotProduct(coneDirNorm, testPointDir) >= Math.cos(Math.toRadians(fov) * 0.5f);
		}
		
		return result;
	}
}