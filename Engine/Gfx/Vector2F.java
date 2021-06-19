package Engine.Gfx;

/**
 * <h1>Coordinate Vectors</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class Vector2F
{
	/**
	 * The x coordinate of the vector
	 */
	public float x;
	/**
	 * The y coordinate of the vector
	 */
	public float y;
	
	/**
	 * A default zero vector
	 */
	public static Vector2F ZERO = new Vector2F(0f,0f);
	
	private static float worldX;
	private static float worldY;
	
	/**
	 * <b><i>Vector2F</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F()}<p>
	 * Create a default zero vector with no magnitude or direction
	 */
	public Vector2F()
	{
		this.x = 0f;
		this.y = 0f;
	}
	
	/**
	 * <b><i>Vector2F</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F(float x, float y)}<p>
	 * Create a vector with the given coordinates
	 * @param x The x component of the vector
	 * @param y The y component of the vector
	 */
	public Vector2F(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * <b><i>Zero</b></i><p>
	 * &nbsp&nbsp{@code public void Zero()}<p>
	 * Convert the vector into a zero vector, with no magnitude or direction<p>
	 */
	public void Zero()
	{
		x = 0f;
		y = 0f;
	}
	
	/**
	 * <b><i>Normalize</b></i><p>
	 * &nbsp&nbsp{@code public void Normalize()}<p>
	 * Convert the vector into a unit vector in the same direction<p>
	 */
	public void Normalize()
	{
		double length = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		if (length != 0.0)
		{
			float scaler = 1.0f / (float) length;
			x = x * scaler;
			y = y * scaler;
		}
	}
	
	/**
	 * <b><i>Normalize</b></i><p>
	 * &nbsp&nbsp{@code public static Vector2F Normalize(Vector2F vec)}<p>
	 * Convert the given vector into a unit vector with the same direction<p>
	 * @param vec The vector in screen coordinates to be normalized
	 * @return A new unit vector in the same direction as vec
	 */
	public static Vector2F Normalize(Vector2F vec)
	{
		double length = Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
		
		if (length != 0.0)
		{
			float scaler = 1.0f / (float) length;
			float x = vec.x * scaler;
			float y = vec.y * scaler;
			
			return new Vector2F(x,y);
		}
		
		return ZERO;
	}
	
	/**
	 * <b><i>Equals</b></i><p>
	 * &nbsp&nbsp{@code public boolean Equals(Vector2F vec)}<p>
	 * Determine if the given vector's coordinates match this one's<p>
	 * @param vec The comparison vector in screen coordinates
	 * @return true if vec's coordinates match this one's exactly, false otherwise
	 */
	public boolean Equals(Vector2F vec)
	{
		return this.x == vec.y && this.y == vec.y;
	}
	
	/**
	 * <b><i>Add</b></i><p>
	 * &nbsp&nbsp{@code public static Vector2F Add(Vector2F vec1, Vector2F vec2)}<p>
	 * Add the respective coordinates of the two given vectors<p>
	 * @param vec1 The first vector in screen coordinates
	 * @param vec2 The second vector in screen coordinates
	 * @return A new vector with the sum of each coordinates for its respective components
	 */
	public static Vector2F Add(Vector2F vec1, Vector2F vec2)
	{
		float x = vec1.x + vec2.x;
		float y = vec1.y + vec2.y;
		return new Vector2F(x, y);
	}
	
	/**
	 * <b><i>Subtract</b></i><p>
	 * &nbsp&nbsp{@code public static Vector2F Subtract(Vector2F vec1, Vector2F vec2)}<p>
	 * Subtract vec2 from vec1<p>
	 * @param vec1 The first vector in screen coordinates
	 * @param vec2 The second vector in screen coordinates
	 * @return A new vector with the subtraction result of each coordinates for its respective components
	 */
	public static Vector2F Subtract(Vector2F vec1, Vector2F vec2)
	{
		float x = vec1.x - vec2.x;
		float y = vec1.y - vec2.y;
		return new Vector2F(x, y);
	}
	
	/**
	 * <b><i>Scaler</b></i><p>
	 * &nbsp&nbsp{@code public static Vector2F Scaler(Vector2F vec, float scaler)}<p>
	 * Multiply the components of vec by the value, scaler<p>
	 * @param vec The vector to by modified
	 * @param scaler The value to multiply vec by
	 * @return A new vector with the scaler applied to the components of vec
	 */
	public static Vector2F Scaler(Vector2F vec, float scaler)
	{
		return new Vector2F(vec.x * scaler, vec.y * scaler);
	}
	
	/**
	 * <b><i>Copy</b></i><p>
	 * &nbsp&nbsp{@code public static Vector2F Copy(Vector2F vec)}<p>
	 * Create a new Vector with the same coordinates of the given vector<p>
	 * @param vec The vector to be copied
	 * @return A new vector with matching coordinates of the given vector
	 */
	public static Vector2F Copy(Vector2F vec)
	{
		return new Vector2F(vec.x, vec.y);
	}
	
	/*
	private static void SetWorldPosition(float x, float y)
	{
		worldX = x;
		worldY = y;
	}
	*/

	/**
	 * <b><i>GetWorldPosition</b></i><p>
	 * &nbsp&nbsp{@code public Vector2F GetWorldPosition()}<p>
	 * Retrieve the vectors position in World coordinates<p>
	 * @return A Vector2F representing the World location of the vector
	 */
	/*
	public Vector2F GetWorldPosition()
	{
		return new Vector2F(x - worldX, y - worldY);
	}
	*/
	
	/**
	 * <b><i>Length</b></i><p>
	 * &nbsp&nbsp{@code public float Length()}<p>
	 * Retrieve the length (magnitude) of the vector
	 * @return The length of the vector as a float
	 */
	public float Length()
	{
		return (float)(Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)));
	}
	
	/**
	 * <b><i>Length</b></i><p>
	 * @param vec The vector to have its length calculated
	 * @return The length (magnitude) of the given vector
	 */
	public static float Length(Vector2F vec)
	{
		return (float)(Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2)));
	}
	
	/**
	 * <b><i>Distance</b></i><p>
	 * &nbsp&nbsp{@code public static double Distance(Vector2F vec1, Vector2F vec2)}<p>
	 * Calculate the distance two arbitrary vectors<p>
	 * @param vec1 The first vector in screen coordinates
	 * @param vec2 The second vector in screen coordinates
	 * @return The unsigned distance between vec1 and vec2
	 */
	public static double Distance(Vector2F vec1, Vector2F vec2)
	{
		return Math.sqrt(Math.pow(vec2.x - vec1.x, 2) + Math.pow(vec2.y - vec1.y, 2));
	}
	
	/**
	 * <b><i>DotProduct</b></i><p>
	 * &nbsp&nbsp{@code public static double DotProduct(Vector2F vec1, Vector2F vec2)}<p>
	 * Calculate the dot product of the two vectors<p> 
	 * @param vec1 The first vector in screen coordinates
	 * @param vec2 The second vector in screen coordinates
	 * @return The result of the Dot Product of the two vectors
	 */
	public static double DotProduct(Vector2F vec1, Vector2F vec2)
	{
		return (vec1.x * vec2.x) + (vec1.y * vec2.y);
	}
	
	/**
	 * <b><i>CrossProduct</b></i><p>
	 * &nbsp&nbsp{@code public static double CrossProduct(Vector2F vec1, Vector2F vec2)}<p>
	 * Calculate the cross product of the two vectors.    This value represents the magnitude
	 * of the normal or perpendicular vector in the z-axis of the two vectors in 3D<p> 
	 * @param vec1 The first vector in screen coordinates
	 * @param vec2 The second vector in screen coordinates
	 * @return The result of the Cross Product of the two vectors.
	 */
	public static float CrossProduct(Vector2F vec1, Vector2F vec2)
	{
		return vec1.x * vec2.y - vec1.y * vec2.x;
	}
	
	/**
	 * <b><i>ToString</b></i><p>
	 * &nbsp&nbsp{@code public String ToString()}<p>
	 * Retrieve String representation of the Vector
	 * @return A formatted String representation of the Vector components
	 */
	public String ToString()
	{
		return "(" + this.x + ", " + this.y + ")";
	}
	
	/**
	 * <b><i>DistanceBetweenWorldVectors</b></i><p>
	 * &nbsp&nbsp{@code public static double DistanceBetweenWorldVectors(Vector2F vec1, Vector2F vec2)}<p>
	 * Calculate the distance between the world coordinates two arbitrary vectors<p>
	 * @param vec1 The first vector used to measure against
	 * @param vec2 The second vector used to measure against
	 * @return The unsigned distance between this and the given vectors 
	 */
	/*
	public static double DistanceBetweenWorldVectors(Vector2F vec1, Vector2F vec2)
	{
		float dx = Math.abs(vec1.GetWorldPosition().x - vec2.GetWorldPosition().x);
		float dy = Math.abs(vec1.GetWorldPosition().y - vec2.GetWorldPosition().y);
		
		return Math.sqrt(Math.pow(dx, 2) - Math.pow(dy, 2));
	}
	*/
}