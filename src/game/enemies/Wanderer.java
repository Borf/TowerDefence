package game.enemies;

/**
 * Created by johan on 2017-04-10.
 */
public class Wanderer extends Enemy {

	public Wanderer() {
		super("/enemies/wanderer.png");
		this.speed = 100;
		this.maxHealth = 100;
	}
}
