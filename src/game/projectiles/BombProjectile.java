package game.projectiles;

import game.enemies.Enemy;

/**
 * Created by johan on 11-4-2017.
 */
public class BombProjectile extends AreaProjectile {
	public BombProjectile(double x, double y, int damage) {
		super("/projectiles/bomb.png", x, y, 200);
		speed = 25;
		freezeTime = 0.0;
		dmg = damage;
	}


	public void onHit(Enemy enemy)
	{
		enemy.damage(dmg);
	}


}
