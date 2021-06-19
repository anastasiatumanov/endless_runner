package Engine.Gfx;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.*;

import javax.imageio.ImageIO;

/**
 * <h1>Load images</h1>
 * <b>Creation Date:</b> June 21, 2016<br>
 * <b>Modified Date:</b> April 06, 2021<p>
 * @author Trevor Lane
 * @version 1.1
 */
public class LoadImage
{

	/**
	 * <b><i>BufferedImage</i></b><p>
	 * &nbsp&nbsp{@code public static BufferedImage FromFile(String path)}<p>
	 * Load an image from a specified file path
	 * @param path A relative path of the image to be loaded from the project->res folder
	 * @return The BufferedImage object loaded from the given path
	 * @exception IOException Catches any file load errors
	 * @see BufferedImage
	 */
	public static BufferedImage FromFile(String path)
	{
		//public static BufferedImage FromFile(Class<?> classFile, String path)
		
		//URL url = classFile.getResource(path);
		BufferedImage image = null;
		
		try
		{
			//image = ImageIO.read(Image.class.getResourceAsStream(path));
      image = ImageIO.read(new File(path));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return image;
	}
}