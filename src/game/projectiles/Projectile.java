package game.projectiles;

import game.Game;
import game.GameObject;
import game.enemies.Enemy;

import java.awt.*;

/**
 * Created by johan on 2017-04-10.
 */
public abstract  class Projectile extends GameObject {
	int targetX, targetY;
	double speed;
	double freezeTime = 0;
	boolean atTarget = false;
	Game game;

	public Projectile(String image) {
		super(image, 1, 1);
	}


	@Override
	public void update(double elapsedTime) {
		double diffX = targetX - x;
		double diffY = targetY - y;
		double len = Math.sqrt(diffX*diffX+diffY*diffY);
		if(len < speed || atTarget)
		{
			if(!atTarget)
				onHit();
			freezeTime-=elapsedTime;
			x = targetX;
			y = targetY;
			atTarget = true;
		}
		else
		{
			angle = Math.atan2(diffY, diffX);
			diffX /= len;
			diffY /= len;
			x += speed * diffX;
			y += speed * diffY;
			angle+=0.1f;
		}
	}

	public abstract void onHit();

	public void onHit(Enemy enemy)
	{

	}

	public boolean isAlive()
	{
		return !atTarget || freezeTime > 0;
	}

	public void setGame(Game game) {
		this.game = game;
	}
}
