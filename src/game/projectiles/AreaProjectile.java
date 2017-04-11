package game.projectiles;

import game.enemies.Enemy;

import java.awt.*;
import java.util.ArrayList;

/**
 * A projectile that damages an area of monsters
 */
public class AreaProjectile extends Projectile {
	double range;
	public AreaProjectile(String image, double x, double y, int range) {
		super(image);
		this.range = range;
		targetX = (int)x;
		targetY = (int)y;

	}

	@Override
	public void onHit() {
		ArrayList<Enemy> enemiesNear = game.getEnemiesNear(new Point(targetX, targetY), range);
		for(Enemy e : enemiesNear)
			onHit(e);
	}
}
