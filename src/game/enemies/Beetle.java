package game.enemies;

/**
 * Created by johan on 2017-04-10.
 */
public class Beetle extends Enemy {

	public Beetle() {
		super("/enemies/Beetle.png", 4);
		this.speed = 75;
		this.maxHealth = 100;
		this.gold = 25;
	}
}
