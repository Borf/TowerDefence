package game.towers;

import game.enemies.Enemy;
import game.projectiles.ArrowProjectile;

/**
 * Created by johan on 2017-04-10.
 */
public class ArrowTower6 extends Tower {
	public ArrowTower6() {
		super("/towers/arrow.png");
		//this.cost = 100;
		this.range = 200;
		this.fireDelay = 0.25;
	}

	@Override
	public void shoot(Enemy e) {
		//e.damage(10);
		shoot(new ArrowProjectile(e));
		//shoot(new BombProjectile(e.x, e.y));
	}
}
