package map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class TiledTileLayer {
	TiledMap map;
	public int[][] indices;
	int width;
	int height;
	BufferedImage image;
	boolean visible = true;
	public TiledTileLayer(JsonObject layer, TiledMap tiledMap) {
		this.map = tiledMap;
		JsonArray data = layer.getJsonArray("data");

		height = layer.getInt("height");
		width = layer.getInt("width");
		visible = layer.getBoolean("visible");

		indices = new int[height][width];

		int i = 0;
		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				indices[y][x] = data.getInt(i);
				i++;
			}
		}
		image = createImage();

	}
	public BufferedImage createImage()
	{
		BufferedImage img = new BufferedImage(128*width, 128*height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();

		for(int y = 0; y < height; y++)
		{
			for(int x = 0; x < width; x++)
			{
				int tileIndex = indices[y][x];
				int rotation = tileIndex>>29;
				tileIndex &= ~(7<<29);

				AffineTransform tx = new AffineTransform();
				tx.translate(x*128, y*128);
				if((rotation&4) != 0) {
					tx.translate(128,0);
					tx.scale(-1, 1);
				}
				if((rotation&2) != 0) {
					tx.translate(0,128);
					tx.scale(1, -1);
				}
				if((rotation&1) != 0) {
					tx.rotate(Math.toRadians(180), 64,64);
				}

				g2.drawImage(map.tiles.get(tileIndex).tile, tx, null);
			}
		}


		return img;
	}

	public void updateImage() {
		image = createImage();
	}
}
