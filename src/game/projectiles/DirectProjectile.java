package game.projectiles;

import game.enemies.Enemy;

/**
 * Created by johan on 11-4-2017.
 */
public class DirectProjectile extends Projectile {
	Enemy target;
	int dmg;
	public DirectProjectile(String image, Enemy target) {
		super(image);
		this.target = target;
	}

	public void update(double elapsedTime)
	{
		targetX = (int) target.x;
		targetY = (int) target.y;
		super.update(elapsedTime);
	}

	@Override
	public void onHit() {
		onHit(target);
	}


}
