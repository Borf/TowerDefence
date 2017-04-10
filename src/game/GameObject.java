package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by johan on 2017-04-10.
 */
public class GameObject
{
	public double x;
	public double y;
	protected BufferedImage[] images;
	private int sheetWidth;
	private int sheetHeight;
	protected int frame;



	private static HashMap<String, BufferedImage[]> imageCache = new HashMap<>();


	public GameObject(String image, int sheetWidth, int sheetHeight)
	{
		this.x = x;
		this.y = y;
		this.frame = 0;
		this.sheetWidth = sheetWidth;
		this.sheetHeight = sheetHeight;

		try {
			if(!imageCache.containsKey(image)) {
				BufferedImage img = ImageIO.read(this.getClass().getResource(image));
				BufferedImage[] slices = new BufferedImage[sheetWidth * sheetHeight];
				for(int x = 0; x < sheetWidth; x++)
				{
					for(int y = 0; y < sheetHeight; y++)
					{
						slices[x+sheetWidth*y] = img.getSubimage(x*(img.getWidth()/sheetWidth),y*(img.getHeight()/sheetHeight), img.getWidth()/sheetWidth, img.getHeight()/sheetHeight);
					}
				}
				imageCache.put(image, slices);
			}
			this.images = imageCache.get(image);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}




	public void draw(Graphics2D g2d)
	{
		AffineTransform tx = new AffineTransform();
		tx.translate(x,y);
		tx.translate(-images[frame].getWidth()/2, -images[frame].getHeight()/2);

		g2d.drawImage(images[frame], tx, null);
	}
	public void update(double elapsedTime)
	{

	}
}
