package game.projectiles;

import game.enemies.Enemy;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by johan on 11-4-2017.
 */
public class AreaProjectile extends Projectile {
	double range;
	public AreaProjectile(String image, int x, int y, int range) {
		super(image);
		this.range = range;
	}

	@Override
	public void onHit() {
		ArrayList<Enemy> enemiesNear = game.getEnemyNear(new Point(targetX, targetY), range);
		for(Enemy e : enemiesNear)
			onHit(e);
	}
}
