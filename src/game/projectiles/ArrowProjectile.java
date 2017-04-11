package game.projectiles;

import game.enemies.Enemy;

/**
 * Created by johan on 11-4-2017.
 */
public class ArrowProjectile extends DirectProjectile {
	public ArrowProjectile(Enemy target) {
		super("/projectiles/arrow.png", target);
		centerX = -25;
		speed = 25;
		freezeTime = 0.25;
	}

}
