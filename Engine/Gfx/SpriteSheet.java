package Engine.Gfx;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * <h1>Single Sprite Images and Sprite Sheet Animations</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1 
 */
public class SpriteSheet
{
	//Constants used throughout
	private static final int ENDLESS_ANIMATION = -1;
	private static final int FLIPPED = -1;
	private static final int NOT_FLIPPED = 1;
	
	private BufferedImage image;
	
	private int frameNum = 0;
	
	private int numFrames = 1;
	private int frameRepeat = 1;
	private int frameWidth = 1;
	private int frameHeight = 1;
	
	/**
	 * Location and dimensions to draw the current image/animation frame at
	 */
	public Rectangle destRec = new Rectangle(0,0,1,1);
	
	private int curFrameCount = 0;
	private int rows = 1;
	private int columns = 1;
	private int startFrame = 0;
	
	private Rectangle srcRec = new Rectangle(0,0,1,1);

	private boolean isAnimating = false;
	private int numCycles = 1;
	private int curCycle = 0;
	
	private float curAngle = 0f;
	private int flipH = NOT_FLIPPED;	// -1 will perform the flip
	private int flipV = NOT_FLIPPED;	// -1 will perform the flip
	
	private Rectangle [] srcRecs = null;
	
	private AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	private AlphaComposite acBase = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
	
	private float transparency = 1.0f;
	private boolean visible = true;
	
	/**
	 * <b><i>SpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SpriteSheet()}<p>
	 * Create an empty sprite sheet that will not be drawn until properly setup
	 */
	public SpriteSheet()
	{
		visible = false;
	}
	
	/**
	 * <b><i>SpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SpriteSheet(BufferedImage image)}<p>
	 * Create a single image sprite sheet that will be drawn at the default location and size
	 * @param image The source image to be used for drawing
	 * @see BufferedImage
	 */
	public SpriteSheet(BufferedImage image)
	{
		this.image = image;
		frameWidth = image.getWidth();
		frameHeight = image.getHeight();
		srcRec.width = frameWidth;
		srcRec.height = frameHeight;
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		this.visible = true;
	}
	
	/**
	 * <b><i>SpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SpriteSheet(BufferedImage image, int rows, int columns, }<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code int startFrame, int numFrames, int frameRepeat)}<p>
	 * Create an animation sprite sheet made up of uniformly sized frames that will draw each frame at the default location
	 * @param image The source image to be used for drawing
	 * @param rows The number of rows of frames in animation, must be at least 1
	 * @param columns The number of columns of frames in the animation, must be at least 1
	 * @param startFrame The frame number (zero based, left to right, top to bottom) to begin animating from
	 * @param numFrames The total number of frames in the animation
	 * @param frameRepeat The number of updates to repeat a drawn frame to smooth out the animation
	 * @see BufferedImage
	 */
	public SpriteSheet(BufferedImage image, int rows, int columns, int startFrame, int numFrames, int frameRepeat)
	{
		//Ensure valid numbers
		this.image = image;
		this.rows = Math.max(1,rows);
		this.columns = Math.max(1,columns);
		this.frameNum = Math.max(0,startFrame);
		this.startFrame = this.frameNum;
		this.numFrames = Math.max(1,numFrames);
		this.frameRepeat = Math.max(1,frameRepeat);
		frameWidth = image.getWidth()/this.columns;
		frameHeight = image.getHeight()/this.rows;
		SetSrcRec();
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		this.visible = true;
	}
	
	/**
	 * <b><i>SpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SpriteSheet(BufferedImage image,Rectangle[] srcRecs, int startFrame,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * {@code  int numFrames, int frameRepeat)}<p>
	 * Create an animation sprite sheet that will draw each frame at the default location, but each source<br>
	 * frame is predetermined, allows for multiple animations on a single sprite sheet and frames of<br>
	 * non-uniform sized frames.
	 * @param image The source image to be used for drawing
	 * @param srcRecs The array of Rectangles specifying the specific location and size of each frame of animation<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspin the source image
	 * @param startFrame The frame number (zero based, left to right, top to bottom) to begin animating from
	 * @param numFrames The total number of frames in the animation
	 * @param frameRepeat The number of updates to repeat a drawn frame to smooth out the animation
	 * @see BufferedImage
	 */
	public SpriteSheet(BufferedImage image,Rectangle[] srcRecs, int startFrame, int numFrames, int frameRepeat)
	{
		//Ensure valid numbers
		this.image = image;
		this.frameNum = Math.max(0,startFrame);
		this.startFrame = this.frameNum;
		this.numFrames = Math.max(1,numFrames);
		this.frameRepeat = Math.max(1,frameRepeat);
		
		//Create a deep copy of the passed in rectangles
		this.srcRecs = new Rectangle[Math.min(srcRecs.length, numFrames)];
		for (int i = 0; i < this.srcRecs.length; ++i)
		{
			this.srcRecs[i] = new Rectangle(srcRecs[i].x, srcRecs[i].y, srcRecs[i].width, srcRecs[i].height);
		}
		
		SetSrcRec();
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		this.visible = true;
	}
	
	/**
	 * <b><i>SetSpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public void SetSpriteSheet(BufferedImage image)}<p>
	 * Modify the image used for a single image sprite sheet.<br>
	 * This resets the destination rectangle's size to that of the entire image and sets the image visible
	 * @param image The source image to be used for drawing
	 * @see BufferedImage
	 */
	public void SetSpriteSheet(BufferedImage image)
	{
		this.image = image;
		frameWidth = image.getWidth();
		frameHeight = image.getHeight();
		srcRec.width = frameWidth;
		srcRec.height = frameHeight;
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		SetSrcRec();
		this.visible = true;
	}
	
	/**
	 * <b><i>SetSpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SetSpriteSheet(BufferedImage image, int rows, int columns, }<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp
	 * {@code int startFrame, int numFrames, int frameRepeat)}<p>
	 * Modify the properties of a uniform frame sized animation sprite sheet
	 * @param image The source image to be used for drawing
	 * @param rows The number of rows of frames in animation, must be at least 1
	 * @param columns The number of columns of frames in the animation, must be at least 1
	 * @param startFrame The frame number (zero based, left to right, top to bottom) to begin animating from
	 * @param numFrames The total number of frames in the animation
	 * @param frameRepeat The number of updates to repeat a drawn frame to smooth out the animation
	 * @see BufferedImage
	 */
	public void SetSpriteSheet(BufferedImage image, int rows, int columns, int startFrame, int numFrames, int frameRepeat)
	{
		this.image = image;
		this.rows = Math.max(1,rows);
		this.columns = Math.max(1,columns);
		this.frameNum = Math.max(0,startFrame);
		this.startFrame = this.frameNum;
		this.numFrames = Math.max(1,numFrames);
		this.frameRepeat = Math.max(1,frameRepeat);
		frameWidth = image.getWidth()/this.columns;
		frameHeight = image.getHeight()/this.rows;
		SetSrcRec();
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		this.visible = true;
	}
	
	/**
	 * <b><i>SetSpriteSheet</b></i><p>
	 * &nbsp&nbsp{@code public SetSpriteSheet(BufferedImage image,Rectangle[] srcRecs, int startFrame,}<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
	 * &nbsp&nbsp&nbsp&nbsp
	 * {@code  int numFrames, int frameRepeat)}<p>
	 * Modify the properties of a non-uniform frame sized animation or multi animation frame sprite sheet
	 * @param image The source image to be used for drawing
	 * @param srcRecs The array of Rectangles specifying the specific location and size of each frame of animation<br>
	 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbspin the source image
	 * @param startFrame The frame number (zero based, left to right, top to bottom) to begin animating from
	 * @param numFrames The total number of frames in the animation
	 * @param frameRepeat The number of updates to repeat a drawn frame to smooth out the animation
	 * @see BufferedImage
	 */
	public void SetSpriteSheet(BufferedImage image, Rectangle[] srcRecs, int startFrame, int numFrames, int frameRepeat)
	{
		this.image = image;
		this.frameNum = Math.max(0,startFrame);
		this.startFrame = this.frameNum;
		this.numFrames = Math.max(1,numFrames);
		this.frameRepeat = Math.max(1,frameRepeat);
		
		//Create a deep copy of the passed in rectangles
		this.srcRecs = new Rectangle[Math.min(srcRecs.length, numFrames)];
		for (int i = 0; i < this.srcRecs.length; ++i)
		{
			this.srcRecs[i] = new Rectangle(srcRecs[i].x, srcRecs[i].y, srcRecs[i].width, srcRecs[i].height);
		}
		
		SetSrcRec();
		destRec.width = srcRec.width;
		destRec.height = srcRec.height;
		this.visible = true;
	}
	
	/**
	 * <b><i>SpriteSheet</i></b><p>
	 * {@code public static void GetCopy(SpriteSheet image)}<p>
	 * Creates a duplicate SpriteSheet of the given image
	 * @param image The SpriteSheet to be duplicated
	 * @return A copy of the given image
	 */
	public static SpriteSheet GetCopy(SpriteSheet image)
	{
		SpriteSheet result;
		
		if (image.srcRecs != null)
		{
			Rectangle[] temp = new Rectangle[image.srcRecs.length];
			for (int i = 0; i < temp.length; ++i)
			{
				temp[i] = new Rectangle(image.srcRecs[i].x, image.srcRecs[i].y, image.srcRecs[i].width, image.srcRecs[i].height);
			}
			
			result = new SpriteSheet(image.GetImage(), temp, image.GetStartFrame(), image.GetNumFrames(), image.GetFramesRepeat());
			result.destRec = new Rectangle(image.destRec.x, image.destRec.y, image.destRec.width, image.destRec.height);
			return result;
		}
		else
		{
			result = new SpriteSheet(image.GetImage(), image.GetRows(), image.GetColumns(), image.GetStartFrame(), image.GetNumFrames(), image.GetFramesRepeat());
			result.destRec = new Rectangle(image.destRec.x, image.destRec.y, image.destRec.width, image.destRec.height);
			return result;
		}
	}
	
	/**
	 * <b><i>SetImage</b></i><p>
	 * {@code public void SetImage(BufferedImage image)}<p>
	 * Set the sprite sheet image property
	 * @param image The source image to be used for drawing
	 * @see BufferedImage
	 */
	public void SetImage(BufferedImage image)
	{
		this.image = image;
	}
	
	private void SetSrcRec()
	{
		if (srcRecs != null && srcRecs.length > 0)
		{
			srcRec.x = this.srcRecs[frameNum].x;
			srcRec.y = this.srcRecs[frameNum].y;
			srcRec.width = this.srcRecs[frameNum].width;
			srcRec.height = this.srcRecs[frameNum].height;
			frameWidth = srcRec.width;
			frameHeight = srcRec.height;
		}
		else
		{
			int curRow = 0;
			int curCol = 0;
			
			curRow = frameNum / columns;
			curCol = frameNum % columns;
			
			srcRec.x = curCol * frameWidth;
			srcRec.y = curRow * frameHeight;
			
			srcRec.width = frameWidth;
			srcRec.height = frameHeight;
		}
	}
	
	private BufferedImage GetFrame(int xFrame, int yFrame, int width, int height)
	{
		return image.getSubimage(xFrame, yFrame, width, height);
	}
	
	private BufferedImage GetFrame(Rectangle srcRec)
	{
		return image.getSubimage(srcRec.x, srcRec.y, srcRec.width, srcRec.height);
	}
	
	/**
	 * <b><i>Draw</b></i><p>
	 * {@code public void Draw(Graphics2D gfx)}<p>
	 * Draw the current frame of animation or full single image at the desired destination
	 * @param gfx The Graphics2D object to draw to
	 * @see Graphics2D
	 */
	public void Draw(Graphics2D gfx)
	{
		
		//System.out.println("DEST (" + destRec.x + "," + destRec.y + "," + destRec.width + "," + destRec.height + " || SOURCE (" + srcRec.x + "," + srcRec.y + "," + srcRec.width + "," + srcRec.height);
		//Draw to screen
		/*
		gfx.drawImage(GetFrame(srcRec), 															//Cut out a frame
					  destRec.x, destRec.y, destRec.x + destRec.width, destRec.y + destRec.height,	//Choose a location to draw to
					  0,0,srcRec.width, srcRec.height, 												//Only grab a square one frame in size
					  null);
		*/
		
		if (visible == true && image != null)
		{
			gfx.setComposite(ac);
			
			gfx.drawImage(GetFrame(srcRec), GetTransformations(), null);
			
			//reset transparency
			gfx.setComposite(acBase);
			
			//Update frame for next drawing
			if (isAnimating)
			{
				curFrameCount = (curFrameCount + 1) % frameRepeat;
				if (curFrameCount == 0)
				{
					frameNum = (frameNum + 1) % numFrames;
					if (frameNum == 0)
					{
						//Go to the next cycle
						curCycle++;
						
						//Stop animating if all out of cycles
						if (curCycle == numCycles)
						{
							StopAnimation();
						}
					}
					SetSrcRec();
				}
			}
		}
	}
	
	/**
	 * <b><i>StartAnimation</b></i><p>
	 * {@code public void StartAnimation()}<p>
	 * Begin an endless animation sequence that can only be stopped using StopAnimation()
	 */
	public void StartAnimation()
	{
		//Start an endless animation
		StartAnimation(ENDLESS_ANIMATION);
	}
	
	
	/**
	 * <b><i>StartAnimation</b></i><p>
	 * {@code public void StartAnimation(int numCycles)}<p>
	 * Begin an animation that will execute a specified number of full cycles
	 * @param numCycles The number of complete cycles of animation that will be drawn
	 */
	public void StartAnimation(int numCycles)
	{
		this.numCycles = numCycles;
		curCycle = 0;
		frameNum = startFrame;
		curFrameCount = 0;
		SetSrcRec();
		isAnimating = true;
	}
	
	/**
	 * <b><i>StopAnimation</b></i><p>
	 * {@code public void StopAnimation()}<p> 
	 * Stop the animation and reset the sprite sheet back to the start frame
	 */
	public void StopAnimation()
	{
		frameNum = startFrame;
		curFrameCount = 0;
		curCycle = 0;
		SetSrcRec();
		isAnimating = false;
	}
	
	/**
	 * <b><i>FlipHorizontally</b></i><p>
	 * {@code public void FlipHorizontally()}<p>
	 * Flip the image in the y-axis.  Note that this modifies its draw point<br>
	 * If it is flipped, the point is now on the top right of the image but will<br>
	 * still visibly show at the same location. 
	 */
	public void FlipHorizontally()
	{
		flipH *= -1;
		if (flipH == FLIPPED)
		{
			destRec.x += destRec.width;
		}
		else
		{
			destRec.x -= destRec.width;
		}
	}
	
	/**
	 * <b><i>FlipVertically</b></i><p>
	 * {@code public void FlipVertically()}<p>
	 * Flip the image in the x-axis.  Note that this modifies its draw point<br>
	 * If it is flipped, the point is now on the bottom left of the image but will<br>
	 * still visibly show at the same location. 
	 */
	public void FlipVertically()
	{
		flipV *= -1;
		if (flipV == FLIPPED)
		{
			destRec.y += destRec.height;
		}
		else
		{
			destRec.y -= destRec.height;
		}
	}
	
	/**
	 * <b><i>Rotate</b></i><p>
	 * {@code public float Rotate(float deltaAngle)}<p>
	 * Rotates the sprite sheet by a specified number of degrees in the counter-clockwise direction 
	 * @param deltaAngle The amount to adjust the current angle of rotation by
	 * @return The modified angle of rotation of the sprite sheet
	 */
	public float Rotate(float deltaAngle)
	{
		curAngle += Math.toRadians(deltaAngle);
		
		return (float)Math.toDegrees(curAngle);
	}
	
	/**
	 * <b><i>RotateTo</b></i><p>
	 * {@code public void RotateTo(float angle)}<p>
	 * Sets the current rotation angle of the sprite sheet to the specified angle
	 * @param angle The destination angle of the sprite sheet
	 */
	public void RotateTo(float angle)
	{
		curAngle = (float)Math.toRadians(angle);
	}
	
	private AffineTransform GetTransformations()
	{
		Vector2F rotationAnchor = new Vector2F((float)(destRec.width * 0.5), (float)(destRec.height * 0.5));
		if (flipH == FLIPPED)
		{
			rotationAnchor.x *=-1;
		}
		if (flipV == FLIPPED)
		{
			rotationAnchor.y *=-1;
		}
		
		AffineTransform at = new AffineTransform();
		at.translate(destRec.x, destRec.y);
		at.rotate(curAngle, rotationAnchor.x, rotationAnchor.y);
		at.scale((float)flipH * (float)destRec.width/(float)frameWidth, (float)flipV * (float)destRec.height/(float)frameHeight);
		
		return at;
	}
	
	/**
	 * <b><i>GetImage</b></i><p>
	 * {@code public BufferedImage GetImage()}<p>
	 * Retrieve the currently set image for the sprite sheet
	 * @return The complete sprite sheet BufferedImage
	 */
	public BufferedImage GetImage()
	{
		return image;
	}
	
	/**
	 * <b><i>SetTransparency</b></i><p>
	 * {@code public void SetTransparency(float transparency)}<p>
	 * Set the current transparency level of the sprite sheet
	 * @param transparency The level of transparency of the image, 0 is invisible and 1 is opaque
	 */
	public void SetTransparency(float transparency)
	{
		this.transparency = Math.max(0f, Math.min(1f,transparency));
		ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.transparency);
	}
	
	/**
	 * <b><i>SetStartFrame</b></i><p>
	 * {@code public void SetStartFrame(int startFrame)}<p>
	 * Set the starting frame number, restricted between 0 and numFrames - 1
	 * @param startFrame The frame number to begin animating at
	 */
	public void SetStartFrame(int startFrame) 
	{
		this.startFrame = Math.min(numFrames-1, Math.max(0, startFrame));
	}
	
	/**
	 * <b><i>SetNumCycles</b></i><p>
	 * {@code public void SetNumCycles(int numCycles)}<p>
	 * Set the number of full animation cycles to be drawn before animation stop
	 * @param numCycles The number of full animation cycles to be drawn
	 */
	public void SetNumCycles(int numCycles) 
	{
		this.numCycles = numCycles;
	}
	
	/**
	 * <b><i>SetVisible</b></i><p>
	 * {@code public void SetVisible(boolean visible)}<p>
	 * Set the visibility of the sprite sheet
	 * @param visible The new state of visibility
	 */
	public void SetVisible(boolean visible)
	{
		this.visible = visible;
	}
	
	/**
	 * <b><i>GetTransparency</b></i><p>
	 * {@code public float GetTransparency()}<p>
	 * Retrieve the current level of transparency between 0(invisible) and 1(opaque)
	 * @return The transparency level of the sprite sheet
	 */
	public float GetTransparency()
	{
		return transparency;
	}
	
	/**
	 * <b><i>GetAngle</b></i><p>
	 * {@code public float GetAngle()}<p>
	 * Retrieve the current rotation angle of the sprite sheet
	 * @return The angle of rotation
	 */
	public float GetAngle()
	{
		return (float)Math.toDegrees(curAngle);
	}
	
	/**
	 * <b><i>IsAnimating</b></i><p>
	 * {@code public boolean IsAnimating()}<p>
	 * Retrieve the current animation state of the sprite sheet
	 * @return true if the sprite sheet is animating, false otherwise
	 */
	public boolean IsAnimating()
	{
		return isAnimating;
	}
	
	/**
	 * <b><i>GetStartFrame</b></i><p>
	 * {@code public int GetStartFrame()}<p>
	 * Retrieve the current starting frame number of the animation between 0 and numFrames - 1
	 * @return The animation frame number the sprite sheet will begin at
	 */
	public int GetStartFrame() 
	{
		return startFrame;
	}

	/**
	 * <b><i>GetNumCycles</b></i><p>
	 * {@code public int GetNumCycles()}<p>
	 * Retrieve the number of full animation cycles to be drawn before animation completion
	 * @return The number of full animation cycles
	 */
	public int GetNumCycles() 
	{
		return numCycles;
	}

	/**
	 * <b><i>GetFrameNum</b></i><p>
	 * {@code public int GetFrameNum()}<p>
	 * Retrieve the current frame number the animation sequence is on
	 * @return The current frame number of animation
	 */
	public int GetFrameNum() 
	{
		return frameNum;
	}

	/**
	 * <b><i>GetNumFrames</b></i><p>
	 * {@code public int GetNumFrames()}<p>
	 * Retrieve the total number of frames in the animation sequence
	 * @return The number of frames in the animation 
	 */
	public int GetNumFrames() 
	{
		return numFrames;
	}

	/**
	 * <b><i>GetFramesRepeat</b></i><p>
	 * {@code public int GetFrameRepeat()}<p>
	 * Retrieve the number of times a single frame will be shown before the next<br>
	 * frame is drawn
	 * @return The number of times a frame is repeated
	 */
	public int GetFramesRepeat() 
	{
		return frameRepeat;
	}

	/**
	 * <b><i>GetFrameWidth</b></i><p>
	 * {@code public int GetFrameWidth()}<p>
	 * Retrieve the width of the current frame on the source image
	 * @return width of the current frame
	 */
	public int GetFrameWidth() 
	{
		return frameWidth;
	}

	/**
	 * <b><i>GetFrameHeight</b></i><p>
	 * {@code public int GetFrameHeight()}<p>
	 * Retrieve the height of the current frame on the source image
	 * @return height of the current frame
	 */
	public int GetFrameHeight() 
	{
		return frameHeight;
	}

	/**
	 * <b><i>GetRows</b></i><p>
	 * {@code public int GetRows()}<p>
	 * Retrieve the number of rows in the sprite sheet
	 * @return The number of rows in sprite sheet, 1 for single image and non-uniform sprite sheets
	 */
	public int GetRows() 
	{
		return rows;
	}
	
	/**
	 * <b><i>GetColumns</b></i><p>
	 * {@code public int GetColumns()}<p>
	 * Retrieve the number of columns in the sprite sheet
	 * @return The number of columns in sprite sheet, 1 for single image and non-uniform sprite sheets
	 */
	public int GetColumns() 
	{
		return columns;
	}

	/**
	 * <b><i>GetSrcRec</b></i><p>
	 * {@code public Rectangle GetSrcRec()}<p>
	 * Retrieve the Rectangle specifying the current frame on animation on the sprite sheet
	 * @return A rectangle used to cutout the current frame of animation on the sprite sheet
	 */
	public Rectangle GetSrcRec() 
	{
		return srcRec;
	}
	
	/**
	 * <b><i>GetVisible</b></i><p>
	 * {@code public boolean GetVisible()}<p>
	 * Retrieve the current visibility state of the image
	 * @return true if the image is visible, false otherwise
	 */
	public boolean GetVisible()
	{
		return visible;
	}
}