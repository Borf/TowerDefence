package map;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class TiledMap {
	public int height;
	public int width;
	
	public ArrayList<Tile> tiles = new ArrayList<>();
	public ArrayList<TiledTileLayer> layers = new ArrayList<TiledTileLayer>();


	public ArrayList<Point> path = new ArrayList<>();

	
	
	public TiledMap(String filename)
	{
		System.out.println("Loading map " + filename);
		JsonReader reader = null;
		try {
			reader = Json.createReader(getClass().getResourceAsStream(filename));
			JsonObject o = (JsonObject) reader.read();

			height = o.getInt("height");
			width = o.getInt("width");

			JsonArray tilesets = o.getJsonArray("tilesets");

			for (int i = 0; i < tilesets.size(); i++) {
				JsonObject tileset = tilesets.getJsonObject(i);
				String tileFile = tileset.getString("image");
				tileFile = tileFile.replaceAll("\\.\\./", "/"); //regex

				BufferedImage img = ImageIO.read(getClass().getResource(tileFile));

				int tilesetWidth = tileset.getInt("imagewidth");
				int tilesetHeight = tileset.getInt("imageheight");
				int tileWidth = tileset.getInt("tilewidth");
				int tileHeight = tileset.getInt("tileheight");


				int index = tileset.getInt("firstgid");
				while (tiles.size() < index + tileset.getInt("tilecount"))
					tiles.add(new Tile());


				for (int y = 0; y + tileHeight <= tilesetHeight; y += tileHeight) {
					for (int x = 0; x + tileWidth <= tilesetWidth; x += tileWidth) {
						tiles.get(index).tile = img.getSubimage(x, y, tileWidth, tileHeight);
						index++;
					}
				}

				index = tileset.getInt("firstgid");
				for(int ii = 0; ii < tileset.getInt("tilecount"); ii++)
				{
					tiles.get(index).walkable = tileset.getJsonObject("tileproperties").getJsonObject(ii+"").getBoolean("walkable");
					index++;
				}
			}



			JsonArray jsonLayers = o.getJsonArray("layers");
			for (int i = 0; i < jsonLayers.size(); i++) {
				if (jsonLayers.getJsonObject(i).getString("type").equals("tilelayer")) {
					layers.add(new TiledTileLayer(jsonLayers.getJsonObject(i), this));
				} else {

				}
			}


			Point current = new Point(o.getJsonObject("properties").getInt("startx"), o.getJsonObject("properties").getInt("starty"));
			path.add(current);

			Point[] offsets = { new Point(-1,0), new Point(1,0), new Point(0,1), new Point(0,-1) };
			int lastDirection = -1;
			boolean finished = false;
			while(!finished)
			{
				finished = true;
				for(int i = 0; i < 4; i++)
				{
					if(lastDirection != -1 && ((offsets[i].x != 0 && offsets[i].x == -offsets[lastDirection].x) || (offsets[i].y != 0 && offsets[i].y == -offsets[lastDirection].y)))
						continue;
					Point newPoint = new Point(current.x + offsets[i].x, current.y + offsets[i].y);
					if(newPoint.x < 0 || newPoint.x >= width || newPoint.y < 0 || newPoint.y >= height)
						continue;
					int index = layers.get(0).indices[newPoint.y][newPoint.x] & (~(7<<29));
					if(tiles.get(index).walkable)
					{
						path.add(newPoint);
						current = newPoint;
						lastDirection = i;
						finished = false;
						continue;
					}
				}
			}


			System.out.println("Path: " + path);







		} catch(IOException e) {
			e.printStackTrace();
		}



		
	}

	
	
	
	public void draw(Graphics2D g2) 
	{		
		for(TiledTileLayer l : layers)
			if(l.visible)
				g2.drawImage(l.image, new AffineTransform(), null);
	}
	
	
}
