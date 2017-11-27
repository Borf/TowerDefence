package game.powerups;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class PowerUp {

    private BufferedImage image;
    private int x,y,width,height;

    public PowerUp(String imageString, int x, int y, int width, int height)
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
        this.width = width;
        this.height = height;

    }

    public void draw(Graphics2D G2D)
    {
        //tx.rotate(angle);
        //tx.translate(centerX, centerY);
        G2D.drawImage(image, x, y, width, height, null);
    }
}
