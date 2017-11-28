package game.enemies;

import game.GameObject;
import map.TiledMap;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 * A class representing an enemy on the screen
 */
public class Enemy extends GameObject {
	protected int maxHealth = 100;
	protected int health = maxHealth;
	protected double speed = 100;// + Math.random() * 10;
	public int gold = 1;


	//for pathfinding
	public TiledMap map;
	public int pathNode = 0;

	//constructor for a 4x4 spritesheet
	public Enemy(String image)
	{
		super(image, 4, 4);
	}
	public Enemy(String image, int frames)
	{
		super(image, frames, 4);
	}


	/**
	 * Updates the enemy, making it follow the path at a certain speed
	 * @param elapsedTime Time elapsed since last update
	 */
	public void update(double elapsedTime)
	{
		Point target = map.path.get(pathNode);
		target = new Point(target.x * 128 + 64,target.y * 128 + 64);

		if(target.distance(x,y) < speed * elapsedTime)
		{
			pathNode++;
		}
		else
		{
			int direction = 0;
			if(Math.abs(x - target.x) > Math.abs(y - target.y))
				if(x < target.x) {
					x += speed * elapsedTime;
					direction = 2;
				}
				else {
					x -= speed * elapsedTime;
					direction = 1;
				}
			else
				if( y < target.y) {
					y += speed * elapsedTime;
					direction = 3;
				}
				else {
					y -= speed * elapsedTime;
					direction = 0;
				}

			frame = direction * getFrameCount() + (int)((System.currentTimeMillis() / 100)%getFrameCount());
		}








	}

	/**
	 * Indicates if this enemy is alive
	 * @return is this enemy alive
	 */
	public boolean isAlive()
	{
		return health > 0;
	}


	/**
	 * Draws the enemy with a healthbar
	 * @param g2d
	 */
	public void draw(Graphics2D g2d)
	{
		super.draw(g2d);

		g2d.setColor(Color.red);
		g2d.fill(new Rectangle2D.Double(x-50, y - 50, 100, 10));
		g2d.setColor(Color.green);
		g2d.fill(new Rectangle2D.Double(x-50, y - 50, 100 * ((float)health / maxHealth), 10));
		g2d.setColor(Color.black);
		g2d.draw(new Rectangle2D.Double(x-50, y - 50, 100, 10));
	}


	/**
	 * Damages the enemy
	 * @param dmg amount of damage
	 */
	public void damage(int dmg) {
		health -= dmg;
		if(health < 0)
			health = 0;
	}
}
