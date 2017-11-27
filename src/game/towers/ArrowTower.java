package game.towers;

import game.enemies.Enemy;
import game.projectiles.ArrowProjectile;

/**
 * Created by johan on 2017-04-10.
 */
public class ArrowTower extends Tower {
	public ArrowTower() {
		super("/towers/arrow.png");
		this.cost = 1;
		this.range = 200;
		this.fireDelay = 0.25;
		this.upgradeCost = this.cost;
	}

	@Override
	public void shoot(Enemy e) {

		shoot(new ArrowProjectile(e, this.dmg));

	}
}
