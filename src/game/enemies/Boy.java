package game.enemies;

/**
 * Created by johan on 2017-04-10.
 */
public class Boy extends Enemy {

	public Boy() {
		super("/enemies/Boy1.png", 3);
		this.speed = 75;
		this.maxHealth = 100;
		this.gold = 25;
	}
}
