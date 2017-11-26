package game;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PowerUp {

    private BufferedImage image;
    private int x,y;

    public PowerUp(String imageString, int x, int y)
    {
        try
        {
            this.image = ImageIO.read(this.getClass().getResource(imageString));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics2D G2D)
    {
        AffineTransform tx = new AffineTransform();
        tx.translate(x,y);
        //tx.rotate(angle);
        tx.translate(image.getWidth()/2, image.getHeight()/2);
        //tx.translate(centerX, centerY);
        G2D.drawImage(image, tx, null);
    }
}
