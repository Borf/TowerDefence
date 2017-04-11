package game.towers;

import game.Game;
import game.GameObject;
import game.projectiles.Projectile;
import game.enemies.Enemy;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by johan on 2017-04-10.
 */
public abstract class Tower extends GameObject {
	private Game game;

	public int cost = 75;
	protected double fireDelay = 0.25;
	protected double range = 200;
	private double fireTimer = 0;



	public Tower(String image) {
		super(image, 1,1);
	}

	@Override
	public void update(double elapsedTime) {
		fireTimer -= elapsedTime;
		if(fireTimer < 0)
		{
			ArrayList<Enemy> inRange = game.getEnemyNear(new Point((int)x,(int)y), range);
			if(!inRange.isEmpty())
				shoot(inRange.get(0)); //TODO: pick random ones?

			fireTimer = fireDelay;
		}
	}
	public abstract void shoot(Enemy e);

	protected void shoot(Projectile projectile)
	{
		projectile.x = x;
		projectile.y = y;
		game.addProjectile(projectile);
	}



	public void setGame(Game game) { this.game = game; }
}
